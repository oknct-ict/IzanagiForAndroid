package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

/**
 * GUI部品そのものと部品の操作のインターフェイスを持つクラスです
 * @author marusa
 */
public class GuiPartsHandler {
	/* Inner Classes */
	/**
	 * GUIパーツのイベントリスナを保持するクラスです。
	 * @author marusa
	 */
	public class GuiPartsEventListener{
		private HashMap<String, String> listenerMap = new HashMap<String, String>();
		
		public void addMap(String type, String functionId){
			listenerMap.put(type, functionId);
		}
	}
}
