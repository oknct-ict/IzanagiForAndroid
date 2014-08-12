package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;

import android.os.Build;

public class WebSocketHandler {
	private static URI mUri = null;
	
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
		
		return (0);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
}
