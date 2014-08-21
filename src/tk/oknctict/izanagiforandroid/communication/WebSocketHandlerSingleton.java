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
 * Websocket���n���h������V���O���g���N���X
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
	 * �C���X�^���X�̎擾
	 * @return �C���X�^���X
	 */
	public static WebSocketHandlerSingleton getInstance(){
		return (mInstance);
	}
	
	/**
	 * �R�l�N�V�����������Ă��邩�m�F����
	 * @return �R�l�N�V�����̗L��
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
	 * ���N�G�X�gID�ɑΉ��������b�Z�[�W���X�i��ݒ肵�܂�
	 * <pre>
	 * IWebSocketHandlerListener���w�肵�܂���onMessage�����Ă΂�܂���
	 * </pre>
	 * @param requestId ���X�����������N�G�X�gID��ݒ肷��
	 * @param listener requestId�̃��b�Z�[�W�̍ۂɌĂ΂�郊�X�i
	 */
	public void addOnMessageListener(int requestId, IWebSocketHandlerListener listener){
		mOnClieckListener.put(requestId, listener);
	}
	
	/**
	 * ���N�G�X�gID�̃��X�i���폜���܂�
	 * @param requestId �폜���郊�X�i�ɑΉ����郊�N�G�X�gID
	 */
	public void delOnMessageListener(int requestId){
		mOnClieckListener.delete(requestId);
	}
	
	/**
	 * �ڑ����URI�̃Z�b�^
	 * <pre>
	 * ws://exam.com/websocket/
	 * �̂悤�Ƀv���g�R��������n�߂��ڑ����URI���w�肵�Ă��������B
	 * </pre>
	 * @param uri �v���g�R������n�܂�ڑ����URI���w�肵�܂��B
	 * @return �����Ȃ�0�A���s�Ȃ�G���[�R�[�h
	 * @throws URISyntaxException �s����URI�̏ꍇ��Exception
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
	 * �R�l�N�V�����̊m��
	 * <pre>
	 * �ݒ肳��Ă���uri�փR�l�N�V�����̊m�������݂܂��B
	 * </pre>
	 * @return ���s���ʁB�����Ȃ�0,���s�Ȃ�G���[�R�[�h�B
	 */
	public int establishConnection(){
		if (mUri == null){
			return (ERROR_NO_SETTING_URI);
		}
		if (mListener == null){ //mListener����̏ꍇ�A�������Ȃ�Listener��ǉ����Ă���
			mListener = new EmptyListener();
		}
		if (hasConnection() == true){ //���ɃN���C�A���g���m�ۂ���Ă���ꍇ�̓G���[��Ԃ�
			return (ERROR_ALREADY_CONNECTED);
		}
		
		if ("sdk".equals(Build.PRODUCT)){ //if using emulator, disable IPv6.
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		/*
		 * �ڑ��̊m���Ƌ��ɁA�C�x���g���X�i�̐ݒ�������Ȃ��܂� 
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
					 * PUSH�R�}���h�Ȃ珈��������B
					 * �����łȂ����requestId��Listener���Ă�
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
	 * �R�l�N�V������j������
	 * @return �����Ȃ�0�A���s�Ȃ�G���[�R�[�h
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
	 * ���b�Z�[�W���M���\�b�h
	 * @param message ���M���镶����
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
	 * WebSocket�ɃC�x���g�����������Ƃ��̃��X�i
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
		/* �S�Ẵ^�X�N��Kill���čŏ������蒼�� */
		ServiceHelper helper = new ServiceHelper();
		Intent intent = new Intent(helper.getApplication(), NoConnectionActivity.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    helper.startActivity(intent);
	}
	
	/**
	 * mListener��null�������Ȃ��Ă悢�悤�ɂ��邽�߂̋�̃N���X
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
