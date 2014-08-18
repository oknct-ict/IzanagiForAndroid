package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

/**
 * GUI部品そのものと部品の操作のインターフェイスを持つクラスです
 * @author marusa
 */
public class GuiPartsHandler {
	private int mPartsType;
	
	/**
	 * コンストラクタ
	 * <pre>
	 * 指定したタイプのパーツを保持します。  
	 * </pre>
	 * @param partsType
	 * @throws UndefinedPartsTypeException 
	 */
	public GuiPartsHandler(int partsType) throws UndefinedPartsTypeException{
		/* 存在しないパーツタイプのチェック */
		if (partsType <= 0 || PARTS_TYPE_COUNT_PLUSONE <= partsType){
			throw new UndefinedPartsTypeException();
		}
		
		mPartsType = partsType;
	}
	public static final int PARTS_TYPE_BUTTON = 1;
	public static final int PARTS_TYPE_TEXTVIEW = 2;
	public static final int PARTS_TYPE_TEXTBOX = 3;
	public static final int PARTS_TYPE_IMAGEVIEW = 4;
	public static final int PARTS_TYPE_SHAPE = 5;
	public static final int PARTS_TYPE_COUNT_PLUSONE = 6;
	
	public int getPartsType(){
		return (mPartsType);
	}
	
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
	
	/* exceptions */
	public class UndefinedPartsTypeException extends Exception {
		public UndefinedPartsTypeException(){
			super("This partsType is not defined.");
		}
		
		private static final long serialVersionUID = 5536758626328426886L;
	}
}
