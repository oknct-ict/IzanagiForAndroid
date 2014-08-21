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
 * Izanagi for Android�ɕK�v�ȒʐM���s���N���X
 * @author marusa
 */
public class Communicator {
	private WebSocketHandlerSingleton wsHandler;
	
	ProgressDialog progressDialog;
	
	/**
	 * �R���X�g���N�^
	 * <pre>
	 * Izanagi�T�[�o�ւ̐ڑ��̏��������܂��BURI��Constants.serverUri�Ŏw�肵�Ă��܂�
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
	 * �R�l�N�V�����̊m��
	 * <pre>
	 * �ݒ肳��Ă���uri�փR�l�N�V�����̊m�������݂܂��B
	 * </pre>
	 * @return ���s���ʁB�����Ȃ�0,���s�Ȃ�G���[�R�[�h�B
	 */
	public int establishConnection(){
		int err = wsHandler.establishConnection();
		return (err);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
	
	/**
	 * �T�[�o�փ��O�C������
	 * @param userId ���O�C�����������[�U��ID
	 * @param passwd ���O�C�����������[�U�̃p�X���[�h
	 * @throws JSONException 
	 * @throws InterruptedException 
	 * @throws NotYetConnectedException 
	 */
	public void login(String userId, String passwd, final IWebSocketHandlerListener eventListener) throws JSONException, NotYetConnectedException, InterruptedException {
		/* JSONObject�̐��� */
		JSONObject dataObject = new JSONObject();
		dataObject.put("user_id", userId);
		dataObject.put("password", passwd);
		
		JSONObject deviceData = new JSONObject();
		deviceData.put("device_id", android.os.Build.ID);
		dataObject.put("device_data", deviceData);
		
		JSONObject rootObject = generatePacket("", "login", dataObject);
		
		progressDialog = new ProgressDialog(LoginActivity.instance); // TODO: �s���Ȃɂ���
		
		/* �v���O���X�o�[�̐ݒ� */
        progressDialog.setTitle("���O�C��");
        // �v���O���X�_�C�A���O�̃��b�Z�[�W��ݒ肵�܂�
        progressDialog.setMessage("���O�C�����Ă��܂�...");
        // �v���O���X�_�C�A���O�̊m��ifalse�j�^�s�m��itrue�j��ݒ肵�܂�
        progressDialog.setIndeterminate(false);
        // �v���O���X�_�C�A���O�̃X�^�C�����~�X�^�C���ɐݒ肵�܂�
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // �v���O���X�_�C�A���O�̍ő�l��ݒ肵�܂�
        progressDialog.setMax(100);
        // �v���O���X�_�C�A���O�̒l��ݒ肵�܂�
        progressDialog.incrementProgressBy(0);
        // �v���O���X�_�C�A���O�̃L�����Z�����\���ǂ�����ݒ肵�܂�
        progressDialog.setCancelable(false);
        // �v���O���X�_�C�A���O��\�����܂�
        progressDialog.show();
		
		/* �f�[�^�̑��M */
		wsHandler.sendMessage(rootObject.toString());
		
		/*
		 * login�̓n���h�V�F�C�N�����˂Ă���̂ŁA
		 * ���O�C�����N�G�X�g�̃��X�|���X�Ɋ܂܂��Z�b�V����ID���ʐM�̂��߂̔F�؃L�[�ɂȂ��Ă܂�
		 */
		wsHandler.addOnMessageListener(rootObject.getInt("request_id"), new IWebSocketHandlerListener() {
			@Override
			public void onMessage(String message) {
				Log.d("WebSocketHandlerSingleton", message);
				
				//�󂯎����JSON�̃p�[�X
				JSONObject obj = null;
				try {
					obj = new JSONObject(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//�Z�b�V����ID�̕ۑ�
				try {
					SessionIdHolder.setSessionId(obj.getString("session_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/* �v���O���X�o�[�̒�~ */
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
	 * ���[�U�̐V�K�o�^���N�G�X�g
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
		
		/* JSONObject�̐��� */
		JSONObject dataObject = new JSONObject();
		dataObject.put("user_id", newUserId);
		dataObject.put("password", newUserPasswd);
		dataObject.put("address", email);
		dataObject.put("grade", (schoolId * 10 + grade));
		
		JSONObject rootObject = generatePacket("", "register", dataObject);
		
		/* �f�[�^�̑��M */
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
		/* JSONObject�̐��� */
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
