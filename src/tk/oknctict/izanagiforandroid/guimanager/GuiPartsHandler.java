package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

import tk.oknctict.izanagiforandroid.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * GUI部品そのものと部品の操作のインターフェイスを持つクラスです
 * @author marusa
 */
public class GuiPartsHandler {
	private View mView;
	private int mPartsType;
	
	/**
	 * コンストラクタ
	 * <pre>
	 * 指定したタイプのパーツを保持します。  
	 * </pre>
	 * @param partsType
	 * @throws UndefinedPartsTypeException 
	 */
	public GuiPartsHandler(Context context, int partsType) throws UndefinedPartsTypeException{
		/* 存在しないパーツタイプのチェック */
		if (partsType <= 0 || PARTS_TYPE_COUNT_PLUSONE <= partsType){
			throw new UndefinedPartsTypeException();
		}
		
		mPartsType = partsType;
		
		RelativeLayout relativeLayout = (RelativeLayout)((Activity)context).findViewById(R.id.izanagi_execute_layout);
		switch (mPartsType){
		case PARTS_TYPE_BUTTON:
			Button buttonTemp = new Button(context);
			break;
			
		case PARTS_TYPE_TEXTVIEW:
			TextView textViewTemp = new TextView(context);
			break;
			
		case PARTS_TYPE_EDITTEXT:
			EditText editTextTemp = new EditText(context);
			break;
			
		case PARTS_TYPE_IMAGEVIEW:
			ImageView imageViewTemp = new ImageView(context);
			break;
			
		case PARTS_TYPE_SHAPE:
			//TODO: Shapeの定義しましょう
			break;
		}
	}
	public static final int PARTS_TYPE_BUTTON = 1;
	public static final int PARTS_TYPE_TEXTVIEW = 2;
	public static final int PARTS_TYPE_EDITTEXT = 3;
	public static final int PARTS_TYPE_IMAGEVIEW = 4;
	public static final int PARTS_TYPE_SHAPE = 5;
	public static final int PARTS_TYPE_COUNT_PLUSONE = 6;
	
	/**
	 * パーツタイプの取得
	 * @return パーツタイプ
	 */
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
