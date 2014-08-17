package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import tk.oknctict.izanagiforandroid.Constants;
import tk.oknctict.izanagiforandroid.communication.WebSocketHandlerSingleton;

/**
 * Izanagi for Androidに必要な通信を行うクラス
 * @author marusa
 */
public class Communicator {
	private WebSocketHandlerSingleton wsHandler;
	
	/**
	 * コンストラクタ
	 * <pre>
	 * Izanagiサーバへの接続の準備をします。URIはConstants.serverUriで指定しています
	 * </pre>
	 * @throws URISyntaxException
	 */
	public Communicator() throws URISyntaxException{
		wsHandler = WebSocketHandlerSingleton.getInstance();
		
		wsHandler.setUri(Constants.serverWSUri);
	}

	/**
	 * コネクションの確立
	 * <pre>
	 * 設定されているuriへコネクションの確立を試みます。
	 * </pre>
	 * @return 試行結果。成功なら0,失敗ならエラーコード。
	 */
	public int establishConnection(){
		int err = wsHandler.establishConnection();
		return (err);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
	
	/**
	 * サーバへログインする
	 * @param userId ログインしたいユーザのID
	 * @param passwd ログインしたいユーザのパスワード
	 * @return 成功なら0,失敗ならエラーコード
	 */
	public int login(String userId, String passwd){
		/* JSONObjectの生成 */
		JSONObject rootObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
		try {
			dataObject.put("user_id", userId);
			dataObject.put("password", passwd);
			
			rootObject.put("type", "android");
			rootObject.put("session_id", "");
			rootObject.put("command", "login_REQ");
			rootObject.put("data", dataObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			return (ERROR_CANNOT_GENERATE_JSON);
		}
		
		/* データの送信 */
		try {
			wsHandler.sendMessage(rootObject.toString());
		} catch (NotYetConnectedException e) {
			e.printStackTrace();
			return (ERROR_NOT_YET_CONNECTED);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return (ERROR_INTERUPTED);
		}
		
		return (0);
	}
	public static final int ERROR_CANNOT_USE_SHA1 = 1;
	public static final int ERROR_CANNOT_GENERATE_JSON = 2;
	public static final int ERROR_NOT_YET_CONNECTED = 3;
	public static final int ERROR_INTERUPTED = 4;
}
