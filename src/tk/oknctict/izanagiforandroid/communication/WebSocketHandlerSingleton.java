package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import android.os.Build;

/**
 * Websocketをハンドルするシングルトンクラス
 * @author marusa
 */
public class WebSocketHandlerSingleton {
	/* for singleton */
	private static WebSocketHandlerSingleton mInstance = new WebSocketHandlerSingleton();
	private WebSocketHandlerSingleton() {}
	
	private static URI mUri = null;
	private static WebSocketClient mClient = null;
	
	private IWebSocketHandlerListener mListener = new EmptyListener();
	
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
		if (mClient != null){ //既にクライアントが確保されている場合はエラーを返す
			return (ERROR_ALREADY_CONNECTED);
		}
		
		if ("sdk".equals(Build.PRODUCT)){ //if using emulator, disable IPv6.
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		mClient = new WebSocketClient(mUri){
			@Override
			public void onOpen(ServerHandshake handshake){
				mListener.onOpen(handshake);
			}
			
			@Override
			public void onMessage(final String message){
				mListener.onMessage(message);
			}
			
			@Override
			public void onError(Exception ex){
				mListener.onError(ex);
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote){
				mListener.onClose(code, reason, remote);
				WebSocketHandlerSingleton.delConnection();
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
		mClient.send(message);
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
