package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;

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
	
	private URI mUri = null;
	private WebSocketClient mClient = null;
	
	private IWebSocketHandlerListener mListener = new MyWebSocketHandlerListener();
	
	/**
	 * �C���X�^���X�̎擾
	 * @return �C���X�^���X
	 */
	public WebSocketHandlerSingleton getInstance(){
		return (mInstance);
	}
	
	/**
	 * �ڑ����URI�̃Z�b�^
	 * <pre>
	 * ws://exam.com/websocket/
	 * �̂悤�Ƀv���g�R��������n�߂��ڑ����URI���w�肵�Ă��������B
	 * </pre>
	 * @param uri �v���g�R������n�܂�ڑ����URI���w�肵�܂��B
	 * @throws URISyntaxException �s����URI�̏ꍇ��Exception
	 */
	public void setUri(String uri) throws URISyntaxException{
		mUri = new URI(uri);
	}
	
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
			mListener = new MyWebSocketHandlerListener();
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
			}
		};
		
		mClient.connect();
		
		return (0);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
	
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
	private class MyWebSocketHandlerListener implements IWebSocketHandlerListener {
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
