package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.UUID;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import tk.oknctict.izanagiforandroid.Constants;
import tk.oknctict.izanagiforandroid.LoginActivity;
import tk.oknctict.izanagiforandroid.SessionIdHolder;
import tk.oknctict.izanagiforandroid.communication.WebSocketHandlerSingleton;
import tk.oknctict.izanagiforandroid.communication.WebSocketHandlerSingleton.IWebSocketHandlerListener;

/**
 * Izanagi for Androidに必要な通信を行うクラス
 * @author marusa
 */
public class Communicator {
	private WebSocketHandlerSingleton wsHandler;
	
	ProgressDialog progressDialog;
	
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
	public void login(String userId, String passwd, final IWebSocketHandlerListener eventListener) throws JSONException, NotYetConnectedException, InterruptedException {
		/* JSONObjectの生成 */
		JSONObject dataObject = new JSONObject();
		dataObject.put("user_id", userId);
		dataObject.put("password", passwd);
		
		JSONObject deviceData = new JSONObject();
		deviceData.put("device_id", android.os.Build.ID);
		dataObject.put("device_data", deviceData);
		
		JSONObject rootObject = generatePacket("", "login", dataObject);
		
		progressDialog = new ProgressDialog(LoginActivity.instance); // TODO: 不穏なにおい
		
		/* プログレスバーの設定 */
        progressDialog.setTitle("ログイン");
        // プログレスダイアログのメッセージを設定します
        progressDialog.setMessage("ログインしています...");
        // プログレスダイアログの確定（false）／不確定（true）を設定します
        progressDialog.setIndeterminate(false);
        // プログレスダイアログのスタイルを円スタイルに設定します
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // プログレスダイアログの最大値を設定します
        progressDialog.setMax(100);
        // プログレスダイアログの値を設定します
        progressDialog.incrementProgressBy(0);
        // プログレスダイアログのキャンセルが可能かどうかを設定します
        progressDialog.setCancelable(false);
        // プログレスダイアログを表示します
        progressDialog.show();
		
		/* データの送信 */
		wsHandler.sendMessage(rootObject.toString());
		
		/*
		 * loginはハンドシェイクも兼ねているので、
		 * ログインリクエストのレスポンスに含まれるセッションIDが通信のための認証キーになってます
		 */
		wsHandler.addOnMessageListener(rootObject.getInt("request_id"), new IWebSocketHandlerListener() {
			@Override
			public void onMessage(String message) {
				Log.d("WebSocketHandlerSingleton", message);
				
				//受け取ったJSONのパース
				JSONObject obj = null;
				try {
					obj = new JSONObject(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//セッションIDの保存
				try {
					SessionIdHolder.setSessionId(obj.getString("session_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/* プログレスバーの停止 */
				progressDialog.dismiss();
				
				eventListener.onMessage(message);
			}
			
			@Override
			public void onError(Exception ex) {}
			@Override
			public void onClose(int code, String reason, boolean remote) {}
			@Override
			public void onOpen(ServerHandshake handshakedata) {}
		});
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
		
		JSONObject rootObject = generatePacket("", "register", dataObject);
		
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
		packet.put("request_id", UUID.randomUUID().hashCode());
		
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
