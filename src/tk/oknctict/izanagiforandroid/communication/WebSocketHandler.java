package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import tk.oknctict.izanagiforandroid.MainActivity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class WebSocketHandler {
	private static final String TAG = "WebSocketHandler";
	
	private static URI mUri = null;
	private static WebSocketClient mClient = null;
	private static Handler mHandler = null;
	
	/**
	 * 接続先のURIのセッタ
	 * <pre>
	 * ws://exam.com/websocket/
	 * のようにプロトコル名から始めた接続先のURIを指定してください。
	 * </pre>
	 * @param uri プロトコルから始まる接続先のURIを指定します。
	 * @throws URISyntaxException 不正なURIの場合のException
	 */
	public static void setUri(String uri) throws URISyntaxException{
		mUri = new URI(uri);
	}
	
	/**
	 * コネクションの確立
	 * <pre>
	 * 設定されているuriへコネクションの確立を試みます。
	 * </pre>
	 * @return 試行結果。成功なら0,失敗ならエラーコード。
	 */
	public static int establishConnection(){
		if (mUri == null){
			return (ERROR_NO_SETTING_URI);
		}
		
		if ("sdk".equals(Build.PRODUCT)){ //if using emulator, disable IPv6.
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		mHandler = new Handler();
		mClient = new WebSocketClient(mUri){
			@Override
			public void onOpen(ServerHandshake handshake){
				Log.d(TAG, "onOpen");
			}
			
			@Override
			public void onMessage(final String message){
				Log.d(TAG, "onMessage");
				Log.d(TAG, "Message:" + message);
				mHandler.post(new Runnable(){
					@Override
					public void run(){
						//TODO: implements show toast
					}
				});
			}
			
			@Override
			public void onError(Exception ex){
				Log.d(TAG, "onError");
				ex.printStackTrace();
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote){
				Log.d(TAG, "onClose");
			}
		};
		
		mClient.connect();
		
		return (0);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
}
