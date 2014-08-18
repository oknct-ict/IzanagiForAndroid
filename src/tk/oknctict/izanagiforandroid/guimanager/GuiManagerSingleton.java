package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

public class GuiManagerSingleton {
	/* for singleton */
	private static GuiManagerSingleton instance = new GuiManagerSingleton();
	private GuiManagerSingleton(){}
	
	/**
	 * �C���X�^���X�̎擾
	 * @return �C���X�^���X
	 */
	public static GuiManagerSingleton getInstance(){
		return (instance);
	}
	/* end for singleton */
	
	private HashMap<String, GuiPartsHandler> guiPartsHashMap = new HashMap<String, GuiPartsHandler>();
	
	/**
	 * �p�[�c��ǉ�����
	 * <pre>
	 * �C�ӂ̃p�[�cID��type�Ŏw�肵��GUI�p�[�c�𐶐����܂��B
	 * add�������_��initialPos�̈ʒu�Ƀp�[�c���`�悳��܂��B
	 * <br />
	 * <b>�p�[�cID�͏d�����Ă͂����܂���B</b>�p�[�cID���d�����Ă���ꍇ��ERROR_CONFLICT_ID��Ԃ��܂��B
	 * </pre>
	 * @param partsId �C�ӂ̃p�[�cID
	 * @param type �p�[�c�̃^�C�v
	 * @param initialPos �����ʒu
	 * @return �����Ȃ�0�A���s�Ȃ�G���[�R�[�h
	 */
	public int addGuiParts(String partsId, String type, Pos initialPos){
		/* ID���d�����Ă��Ȃ����m�F���� */
		if (guiPartsHashMap.containsKey(partsId) == true){
			return (ERROR_CONFLICT_ID);
		}
		
		//TODO: ���ۂ̒ǉ��������s��
		
		return (0);
	}
	public static final int ERROR_CONFLICT_ID = 1;
	
	
	/* Inner Classes */
	/**
	 * �p�[�c�̈ʒu��ۑ�����\����
	 * @author media
	 */
	public class Pos {
		public int x;
		public int y;
		
		/**
		 * �R���X�g���N�^
		 * <pre>
		 * �����Ŏw�肵�����W�ŏ��������܂�
		 * </pre>
		 * @param ix x���W�̏������l
		 * @param iy y���W�̏������l
		 */
		public Pos(int ix, int iy){
			x = ix;
			y = iy;
		}
	}
}
