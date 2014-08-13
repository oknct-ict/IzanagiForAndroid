package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;

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
	 * uri�ւ̐ڑ��̏��������܂�
	 * </pre>
	 * @param uri�@�ڑ�������URI
	 * @throws URISyntaxException
	 */
	public Communicator(String uri) throws URISyntaxException{
		wsHandler = WebSocketHandlerSingleton.getInstance();
		
		wsHandler.setUri(uri);
	}
}
