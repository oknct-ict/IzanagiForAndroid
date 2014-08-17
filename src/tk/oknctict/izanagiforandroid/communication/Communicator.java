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
 * Izanagi for Android�ɕK�v�ȒʐM���s���N���X
 * @author marusa
 */
public class Communicator {
	private WebSocketHandlerSingleton wsHandler;
	
	/**
	 * �R���X�g���N�^
	 * <pre>
	 * Izanagi�T�[�o�ւ̐ڑ��̏��������܂��BURI��Constants.serverUri�Ŏw�肵�Ă��܂�
	 * </pre>
	 * @throws URISyntaxException
	 */
	public Communicator() throws URISyntaxException{
		wsHandler = WebSocketHandlerSingleton.getInstance();
		
		wsHandler.setUri(Constants.serverWSUri);
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
	 * @return �����Ȃ�0,���s�Ȃ�G���[�R�[�h
	 */
	public int login(String userId, String passwd){
		/* JSONObject�̐��� */
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
		
		/* �f�[�^�̑��M */
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
