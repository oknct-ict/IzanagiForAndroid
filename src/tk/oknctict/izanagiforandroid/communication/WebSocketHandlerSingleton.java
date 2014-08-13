package tk.oknctict.izanagiforandroid.communication;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

/**
 * Websocket���n���h������V���O���g���N���X
 * @author marusa
 *
 */
public class WebSocketHandlerSingleton {
	private static WebSocketHandlerSingleton mInstance = new WebSocketHandlerSingleton();
	private WebSocketHandlerSingleton() {}
	
	private static final String TAG = "WebSocketHandler";
	
	private URI mUri = null;
	private WebSocketClient mClient = null;
	private Handler mHandler = null;
	
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
		
		if ("sdk".equals(Build.PRODUCT)){ //if using emulator, disable IPv6.
			java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
			java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		mHandler = new Handler();
		mClient = new WebSocketClient(mUri){
			@Override
			public void onOpen(ServerHandshake handshake){
				Log.d(TAG, "onOpen");
			}
			
			@Override
			public void onMessage(final String message){
				Log.d(TAG, "onMessage");
				Log.d(TAG, "Message:" + message);
				mHandler.post(new Runnable(){
					@Override
					public void run(){
						//TODO: implements show toast
					}
				});
			}
			
			@Override
			public void onError(Exception ex){
				Log.d(TAG, "onError");
				ex.printStackTrace();
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote){
				Log.d(TAG, "onClose");
			}
		};
		
		mClient.connect();
		
		return (0);
	}
	public static final int ERROR_NO_SETTING_URI = 1;
}
