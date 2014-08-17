package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import android.os.Build;

/**
 * Websocket���n���h������V���O���g���N���X
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
		if (mClient != null){ //���ɃN���C�A���g���m�ۂ���Ă���ꍇ�̓G���[��Ԃ�
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
		mClient.send(message);
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
