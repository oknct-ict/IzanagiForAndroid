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
	private Pos mPos;
	
	/**
	 * コンストラクタ
	 * <pre>
	 * 指定したタイプのパーツを保持します。  
	 * </pre>
	 * @param partsType
	 * @throws UndefinedPartsTypeException 
	 */
	public GuiPartsHandler(Context context, int partsType, LayoutParams layoutParams) throws UndefinedPartsTypeException{
		/* 存在しないパーツタイプのチェック */
		if (partsType <= 0 || PARTS_TYPE_COUNT_PLUSONE <= partsType){
			throw new UndefinedPartsTypeException();
		}
		
		mPartsType = partsType;
		
		RelativeLayout relativeLayout = (RelativeLayout)((Activity)context).findViewById(R.id.izanagi_execute_layout);
		switch (mPartsType){
		case PARTS_TYPE_BUTTON:
			Button buttonTemp = new Button(context);
			relativeLayout.addView(buttonTemp, layoutParams.width, layoutParams.height);
			break;
			
		case PARTS_TYPE_TEXTVIEW:
			TextView textViewTemp = new TextView(context);
			relativeLayout.addView(textViewTemp, layoutParams.width, layoutParams.height);
			break;
			
		case PARTS_TYPE_EDITTEXT:
			EditText editTextTemp = new EditText(context);
			relativeLayout.addView(editTextTemp, layoutParams.width, layoutParams.height);
			break;
			
		case PARTS_TYPE_IMAGEVIEW:
			ImageView imageViewTemp = new ImageView(context);
			relativeLayout.addView(imageViewTemp, layoutParams.width, layoutParams.height);
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
	public static class GuiPartsEventListener{
		private HashMap<String, String> listenerMap = new HashMap<String, String>();
		
		public void addMap(String type, String functionId){
			listenerMap.put(type, functionId);
		}
	}
	
	/**
	 * パーツの位置を保存する構造体
	 * @author marusa
	 */
	public static class Pos {
		public int x;
		public int y;
		
		/**
		 * コンストラクタ
		 */
		public Pos(){};
		
		/**
		 * コンストラクタ
		 * <pre>
		 * 引数で指定した座標で初期化します
		 * </pre>
		 * @param ax x座標の初期化値
		 * @param ay y座標の初期化値
		 */
		public Pos(int ax, int ay){
			x = ax;
			y = ay;
		}
	}
	
	/**
	 * パーツのレイアウト情報を保存する構造体
	 * @author marusa
	 */
	public static class LayoutParams{
		public int x = 0;
		public int y = 0;

		public int width = 45;
		public int height = 96;
		
		public LayoutParams(int ax, int ay){
			x = ax;
			y = ay;
		}
		
		public LayoutParams(int ax, int ay, int aWidth, int aHeight){
			x = ax;
			y = ay;
			width = aWidth;
			height = aHeight;
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
