package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

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
	 * uriへの接続の準備をします
	 * </pre>
	 * @param uri　接続する先のURI
	 * @throws URISyntaxException
	 */
	public Communicator(String uri) throws URISyntaxException{
		wsHandler = WebSocketHandlerSingleton.getInstance();
		
		wsHandler.setUri(uri);
	}
	
	/**
	 * サーバへログインする
	 * @param userId ログインしたいユーザのID
	 * @param passwd ログインしたいユーザのパスワード
	 * @return 成功なら0,失敗ならエラーコード
	 */
	public int login(String userId, String passwd){
		/* パスワードのダイジェストの生成 */
		MessageDigest md = null;
		try { //ハッシュ関数の用意
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return (ERROR_CANNOT_USE_SHA1);
		}
		
		byte[] digest = md.digest(passwd.getBytes());
		String passwdDigest = "";
		for (int i = 0; i < digest.length; i++){
			passwdDigest += Integer.toHexString(digest[i]);
		}
		
		/* JSONObjectの生成 */
		JSONObject rootObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
		try {
			dataObject.put("user_id", userId);
			dataObject.put("password", passwdDigest);
			
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
