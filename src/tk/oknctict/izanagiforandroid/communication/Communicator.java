package tk.oknctict.izanagiforandroid.communication;

import java.net.URISyntaxException;

import tk.oknctict.izanagiforandroid.communication.WebSocketHandlerSingleton;

/**
 * Izanagi for Androidに必要な通信を行うクラス
 * @author marusa
 */
public class Communicator {
	private WebSocketHandlerSingleton wsHandler;
	
	public Communicator(String uri) throws URISyntaxException{
		wsHandler = WebSocketHandlerSingleton.getInstance();
		
		wsHandler.setUri(uri);
	}
}
