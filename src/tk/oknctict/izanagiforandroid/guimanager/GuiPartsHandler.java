package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

/**
 * GUI���i���̂��̂ƕ��i�̑���̃C���^�[�t�F�C�X�����N���X�ł�
 * @author marusa
 */
public class GuiPartsHandler {
	private int mPartsType;
	
	/**
	 * �R���X�g���N�^
	 * <pre>
	 * �w�肵���^�C�v�̃p�[�c��ێ����܂��B  
	 * </pre>
	 * @param partsType
	 * @throws UndefinedPartsTypeException 
	 */
	public GuiPartsHandler(int partsType) throws UndefinedPartsTypeException{
		/* ���݂��Ȃ��p�[�c�^�C�v�̃`�F�b�N */
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
	 * GUI�p�[�c�̃C�x���g���X�i��ێ�����N���X�ł��B
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
