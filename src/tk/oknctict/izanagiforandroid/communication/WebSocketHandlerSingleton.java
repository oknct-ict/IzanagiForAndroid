package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import tk.oknctict.izanagiforandroid.NoConnectionActivity;
import tk.oknctict.izanagiforandroid.ServiceHelper;
import tk.oknctict.izanagiforandroid.SessionIdHolder;
import android.content.Intent;
import android.os.Build;
import android.util.SparseArray;

/**
 * Websocketをハンドルするシングルトンクラス
 * @author marusa
 */
public class WebSocketHandlerSingleton{
	/* for singleton */
	private static WebSocketHandlerSingleton mInstance = new WebSocketHandlerSingleton();
	private WebSocketHandlerSingleton() {}
	
	private static URI mUri = null;
	private static WebSocketClient mClient = null;
	
	private IWebSocketHandlerListener mListener = new EmptyListener();
	
	private static SparseArray<IWebSocketHandlerListener> mOnClieckListener = new SparseArray<WebSocketHandlerSingleton.IWebSocketHandlerListener>();
	
	/**
	 * インスタンスの取得
	 * @return インスタンス
	 */
	public static WebSocketHandlerSingleton getInstance(){
		return (mInstance);
	}
	
	/**
	 * コネクションを持っているか確認する
	 * @return コネクションの有無
	 */
	public boolean hasConnection(){
		if (mClient == null){
			return (false);
		}
		else {
			return (true);
		}
	}
	
	/**
	 * リクエストIDに対応したメッセージリスナを設定します
	 * <pre>
	 * IWebSocketHandlerListenerを指定しますがonMessageしか呼ばれません
	 * </pre>
	 * @param requestId リスンしたいリクエストIDを設定する
	 * @param listener requestIdのメッセージの際に呼ばれるリスナ
	 */
	public void addOnMessageListener(int requestId, IWebSocketHandlerListener listener){
		mOnClieckListener.put(requestId, listener);
	}
	
	/**
	 * リクエストIDのリスナを削除します
	 * @param requestId 削除するリスナに対応するリクエストID
	 */
	public void delOnMessageListener(int requestId){
		mOnClieckListener.delete(requestId);
	}
	
	/**
	 * 接続先のURIのセッタ
	 * <pre>
	 * ws://exam.com/websocket/
	 * のようにプロトコル名から始めた接続先のURIを指定してください。
	 * </pre>
	 * @param uri プロトコルから始まる接続先のURIを指定します。
	 * @return 成功なら0、失敗ならエラーコード
	 * @throws URISyntaxException 不正なURIの場合のException
	 */
	public int setUri(String uri) throws URISyntaxException{
		if (mClient != null){
			return (ERROR_CONNECTING);
		}
		
		mUri = new URI(uri);
		
		return (0);
	}
	public static final int ERROR_CONNECTING = 1;
	
	/**
	 * コネクションの確立
	 * <pre>
	 * 設定されているuriへコネクションの確立を試みます。
	 * </pre>
	 * @return 試行結果。成功なら0,失敗ならエラーコード。
	 */
	public int establishConnection(){
		if (mUri == null){
			return (ERROR_NO_SETTING_URI);
		}
		if (mListener == null){ //mListenerが空の場合、何もしないListenerを追加しておく
			mListener = new EmptyListener();
		}
		if (hasConnection() == true){ //既にクライアントが確保されている場合はエラーを返す
			return (ERROR_ALREADY_CONNECTED);
		}
		
		if ("sdk".equals(Build.PRODUCT)){ //if using emulator, disable IPv6.
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		/*
		 * 接続の確立と共に、イベントリスナの設定もおこないます 
		 */
		mClient = new WebSocketClient(mUri){
			@Override
			public void onOpen(ServerHandshake handshake){
				mListener.onOpen(handshake);
			}
			
			@Override
			public void onMessage(final String message){
				mListener.onMessage(message);
				
				try {
					JSONObject obj = new JSONObject(message);
					
					/*
					 * PUSHコマンドなら処理をする。
					 * そうでなければrequestIdでListenerを呼ぶ
					 */
					String command = obj.getString("command");
					if (command == "run_start"){
						
					}
					else {
						int requestId = obj.getInt("request_id");
						IWebSocketHandlerListener listener = mOnClieckListener.get(requestId);
						listener.onMessage(message);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError(Exception ex){
				mListener.onError(ex);
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote){
				mListener.onClose(code, reason, remote);
				WebSocketHandlerSingleton.delConnection();
				SessionIdHolder.delSessionId();
				
				connectionError();
			}
		};
		
		mClient.connect();
		
		return (0);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
	public static final int ERROR_ALREADY_CONNECTED = 2;
	
	/**
	 * コネクションを破棄する
	 * @return 成功なら0、失敗ならエラーコード
	 */
	public static int delConnection(){
		if (mClient == null){
			return (ERROR_NO_CONNECTION);
		}
		
		mClient = null;
		
		return (0);
	}
	public static final int ERROR_NO_CONNECTION = 1;
	
	/**
	 * メッセージ送信メソッド
	 * @param message 送信する文字列
	 * @throws NotYetConnectedException
	 * @throws InterruptedException
	 */
	public void sendMessage(String message) throws NotYetConnectedException, InterruptedException {
		try {
			mClient.send(message);
		}
		catch (Exception e){
			e.printStackTrace();
			connectionError();
		}
	}
	
	/**
	 * WebSocketにイベントが発生したときのリスナ
	 * @author marusa
	 */
	public interface IWebSocketHandlerListener {
		public void onOpen(ServerHandshake handshakedata);
		public void onMessage(String message);
		public void onClose(int code, String reason, boolean remote);
		public void onError(Exception ex);
	}
	
	/* private */
	private void connectionError(){
		/* 全てのタスクをKillして最初からやり直す */
		ServiceHelper helper = new ServiceHelper();
		Intent intent = new Intent(helper.getApplication(), NoConnectionActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    helper.startActivity(intent);
	}
	
	/**
	 * mListenerがnullを持たなくてよいようにするための空のクラス
	 * @author media
	 */
	private class EmptyListener implements IWebSocketHandlerListener {
		@Override
		public void onOpen(ServerHandshake handshakedata) {}

		@Override
		public void onMessage(String message) {}

		@Override
		public void onClose(int code, String reason, boolean remote) {}

		@Override
		public void onError(Exception ex) {}
	}
}
