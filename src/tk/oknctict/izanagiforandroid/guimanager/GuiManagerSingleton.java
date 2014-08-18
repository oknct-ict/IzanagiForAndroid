package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

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
	
	/**
	 * パーツを追加する
	 * <pre>
	 * 任意のパーツIDでtypeで指定したGUIパーツを生成します。
	 * addした時点でinitialPosの位置にパーツが描画されます。
	 * <br />
	 * <b>パーツIDは重複してはいけません。</b>パーツIDが重複している場合はERROR_CONFLICT_IDを返します。
	 * </pre>
	 * @param partsId 任意のパーツID
	 * @param type パーツのタイプ
	 * @param initialPos 初期位置
	 * @throws PartsIdConflictException 
	 */
	public void addGuiParts(String partsId, String type, Pos initialPos) throws PartsIdConflictException{
		/* IDが重複していないか確認する */
		if (guiPartsHashMap.containsKey(partsId) == true){
			throw new PartsIdConflictException();
		}
		
		//TODO: 実際の追加処理
	}
	
	
	/* パーツの操作メソッド群 */
	/**
	 * パーツの位置を変更するメソッド
	 * @param partsId　変更するパーツのID
	 * @param pos 座標指定
	 * @throws PartsIdNotfoundException
	 */
	public void setPosition(String partsId, Pos pos) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の設定処理
	}
	
	/**
	 * パーツの位置を取得するメソッド
	 * @param partsId 位置を取得したいパーツのID
	 * @return　パーツの座標
	 * @throws PartsIdNotfoundException
	 */
	public Pos getPosition(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: 実際の取得処理
		Pos partsPos = new Pos();
		partsPos.x = 0;
		partsPos.y = 0;
		
		return (partsPos);
	}
	
	
	/* Inner Classes */
	/**
	 * パーツの位置を保存する構造体
	 * @author media
	 */
	public class Pos {
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
		 * @param ix x座標の初期化値
		 * @param iy y座標の初期化値
		 */
		public Pos(int ix, int iy){
			x = ix;
			y = iy;
		}
	}

	
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
}
