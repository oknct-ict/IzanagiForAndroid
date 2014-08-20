package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

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
		
		if (wsHandler.hasConnection() == false){
			wsHandler.setUri(Constants.serverWSUri);
		}
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
	 * @throws JSONException 
	 * @throws InterruptedException 
	 * @throws NotYetConnectedException 
	 */
	public void login(String userId, String passwd) throws JSONException, NotYetConnectedException, InterruptedException {
		/* JSONObjectの生成 */
		JSONObject dataObject = new JSONObject();
		dataObject.put("user_id", userId);
		dataObject.put("password", passwd);
		
		JSONObject rootObject = generatePacket("", "login_REQ", dataObject);
		
		/* データの送信 */
		wsHandler.sendMessage(rootObject.toString());
	}
	public static final int ERROR_CANNOT_USE_SHA1 = 1;
	public static final int ERROR_CANNOT_GENERATE_JSON = 2;
	public static final int ERROR_NOT_YET_CONNECTED = 3;
	public static final int ERROR_INTERUPTED = 4;
	
	/**
	 * ユーザの新規登録リクエスト
	 * @param newUserId
	 * @param newUserPasswd
	 * @param email
	 * @param schoolId
	 * @param grade
	 * @throws IllegalSchoolIdException
	 * @throws IllegalGradeException
	 * @throws JSONException
	 * @throws NotYetConnectedException
	 * @throws InterruptedException
	 */
	public void newUserRequest(String newUserId, String newUserPasswd, String email, int schoolId, int grade) throws IllegalSchoolIdException, IllegalGradeException, JSONException, NotYetConnectedException, InterruptedException{
		if (schoolId < 1 || SCHOOL_ID_OTHER < schoolId){
			throw new IllegalSchoolIdException();
		}
		if (grade < 1 || 9 < schoolId){
			throw new IllegalGradeException();
		}
		
		/* JSONObjectの生成 */
		JSONObject dataObject = new JSONObject();
		dataObject.put("user_id", newUserId);
		dataObject.put("password", newUserPasswd);
		dataObject.put("address", email);
		dataObject.put("grade", (schoolId * 10 + grade));
		
		JSONObject rootObject = generatePacket("", "register_REQ", dataObject);
		
		/* データの送信 */
		wsHandler.sendMessage(rootObject.toString());
	}
	public static final int SCHOOL_ID_JUNEAR = 1;
	public static final int SCHOOL_ID_JUNEAR_HIGE = 2;
	public static final int SCHOOL_ID_HIGE = 3;
	public static final int SCHOOL_ID_UNIV = 4;
	public static final int SCHOOL_ID_GRAD = 5;
	public static final int SCHOOL_ID_NCT = 6;
	public static final int SCHOOL_ID_COLLAGE = 7;
	public static final int SCHOOL_ID_OTHER = 8;
	
	
	/* private */
	private JSONObject generatePacket(String sessionId, String command, JSONObject data) throws JSONException{
		/* JSONObjectの生成 */
		JSONObject packet = new JSONObject();
		
		packet.put("type", "android");
		packet.put("session_id", sessionId);
		packet.put("command", command);
		packet.put("data", data);
		
		return (packet);
	}
	
	/* exceptions */
	public class IllegalSchoolIdException extends Exception {
		public IllegalSchoolIdException() {
			super("Undefined school id.");
		}
		
		private static final long serialVersionUID = 6567553179418687742L;
	}

	public class IllegalGradeException extends Exception {
		public IllegalGradeException() {
			super("Illegal num of grade.");
		}
		
		private static final long serialVersionUID = -5634065383444357157L;		
	}
}
