package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

import android.content.Context;
import tk.oknctict.izanagiforandroid.IzanagiExecuteActivityHolder;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.GuiPartsEventListener;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.Pos;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.UndefinedPartsTypeException;

/**
 * GUI全体をマネージするシングルトンなクラス
 * @author marusa
 */
public class GuiManagerSingleton {
	/* for singleton */
	private static GuiManagerSingleton instance = new GuiManagerSingleton();
	private GuiManagerSingleton(){}
	
	/**
	 * インスタンスの取得
	 * @return インスタンス
	 */
	public static GuiManagerSingleton getInstance(){
		return (instance);
	}
	/* end for singleton */
	
	
	private HashMap<String, GuiPartsHandler> guiPartsHashMap = new HashMap<String, GuiPartsHandler>();
	//private Context mContext;
	
	/**
	 * パーツを追加する
	 * <pre>
	 * 任意のパーツIDでtypeで指定したGUIパーツを生成します。
	 * addした時点でinitialPosの位置にパーツが描画されます。パーツIDはGuiPartsHandlerに定数定義されています。
	 * <br />
	 * <b>パーツIDは重複してはいけません。</b>パーツIDが重複している場合はERROR_CONFLICT_IDを返します。
	 * </pre>
	 * @param partsId 任意のパーツID
	 * @param partsType パーツのタイプ
	 * @param layoutParams 初期レイアウトパラメータ
	 * @throws PartsIdConflictException 
	 * @throws UndefinedPartsTypeException 
	 * @throws NotStartedExecuteActivity
	 */
	public void addGuiParts(String partsId, int partsType, GuiPartsHandler.LayoutParams layoutParams) throws PartsIdConflictException, UndefinedPartsTypeException, NotStartedExecuteActivity{
		/* IDが重複していないか確認する */
		if (guiPartsHashMap.containsKey(partsId) == true){
			throw new PartsIdConflictException();
		}
		
		Context context = IzanagiExecuteActivityHolder.getContext();
		if (context == null){
			throw new NotStartedExecuteActivity();
		}
		guiPartsHashMap.put(partsId, new GuiPartsHandler(context, partsType, layoutParams));
	}
	
	/**
	 * パーツを削除する
	 * @param partsId 削除したいパーツのID
	 * @throws PartsIdNotfoundException 
	 */
	public void deleteGuiParts(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の削除処理
	}
	
	
	/* パーツの操作メソッド群 */
	/**
	 * レイアウトのパラメータを設定します
	 * @param partsId 設定するパーツのID
	 * @param layoutParams 設定するパラメータ
	 * @throws PartsIdNotfoundException
	 */
	public void setLayoutParams(String partsId, GuiPartsHandler.LayoutParams layoutParams) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		GuiPartsHandler parts = guiPartsHashMap.get(partsId);
		parts.setLayoutParams(layoutParams);
	}
	
	/**
	 * レイアウトのパラメータを取得します
	 * @param partsId 取得するパーツのID
	 * @return レイアウトのパラメータ
	 * @throws PartsIdNotfoundException
	 */
	public GuiPartsHandler.LayoutParams getLayoutParams(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		GuiPartsHandler parts = guiPartsHashMap.get(partsId);
		return (parts.getLayoutParams());
	}
	
	/**
	 * パーツにテキストを設定します
	 * @param partsId テキストをセットするパーツ
	 * @param text セットするテキスト
	 * @throws PartsIdNotfoundException 
	 */
	public void setText(String partsId, String text) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の設定処理
	}
	
	/**
	 * パーツからテキストを取得します
	 * <pre>
	 * もしテキストを取得できないようなパーツだった場合、空文字列が返ります。
	 * </pre>
	 * @param partsId テキストを取得するパーツのID
	 * @return 取得したテキスト
	 * @throws PartsIdNotfoundException
	 */
	public String getText(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の取得処理
		
		return ("");
	}
	
	/**
	 * イベントリスナを設定します
	 * @param partsId セット先のパーツID
	 * @param listener イベントリスナ
	 * @throws PartsIdNotfoundException 
	 */
	public void setEventListener(String partsId, GuiPartsEventListener listener) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の設定処理
	}
	
	
	/* Inner Classes */
	//Nothing
	
	/* 例外群 */
	public class PartsIdConflictException extends Exception {
		public PartsIdConflictException(){
			super("partId is already exists.");
		}
		private static final long serialVersionUID = -8940594564735678038L;
	}
	
	public class PartsIdNotfoundException extends Exception {
		public PartsIdNotfoundException(){
			super("partsId is not found.");
		}
		
		private static final long serialVersionUID = 1421375205147951206L;
	}
	
	public class NotStartedExecuteActivity extends Exception {
		public NotStartedExecuteActivity(){
			super("Not started izanagi execute activity.");
		}
		
		private static final long serialVersionUID = 7027429261284500743L;
	}
}
