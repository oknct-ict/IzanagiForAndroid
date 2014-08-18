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
	 * @throws PartsIdConflictException 
	 */
	public void addGuiParts(String partsId, String type, Pos initialPos) throws PartsIdConflictException{
		/* ID���d�����Ă��Ȃ����m�F���� */
		if (guiPartsHashMap.containsKey(partsId) == true){
			throw new PartsIdConflictException();
		}
		
		//TODO: ���ۂ̒ǉ�����
	}
	
	
	/* �p�[�c�̑��상�\�b�h�Q */
	/**
	 * �p�[�c�̈ʒu��ύX���郁�\�b�h
	 * @param partsId�@�ύX����p�[�c��ID
	 * @param pos ���W�w��
	 * @throws PartsIdNotfoundException
	 */
	public void setPosition(String partsId, Pos pos) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̐ݒ菈��
	}
	
	/**
	 * �p�[�c�̈ʒu���擾���郁�\�b�h
	 * @param partsId �ʒu���擾�������p�[�c��ID
	 * @return�@�p�[�c�̍��W
	 * @throws PartsIdNotfoundException
	 */
	public Pos getPosition(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̎擾����
		Pos partsPos = new Pos();
		partsPos.x = 0;
		partsPos.y = 0;
		
		return (partsPos);
	}
	
	
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
		 */
		public Pos(){};
		
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

	
	/* ��O�Q */
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
