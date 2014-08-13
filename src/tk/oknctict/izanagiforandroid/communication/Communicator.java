package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;

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
}
