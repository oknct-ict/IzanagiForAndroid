package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

import android.content.Context;
import tk.oknctict.izanagiforandroid.IzanagiExecuteActivityHolder;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.GuiPartsEventListener;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.Pos;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.UndefinedPartsTypeException;

/**
 * GUI�S�̂��}�l�[�W����V���O���g���ȃN���X
 * @author marusa
 */
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
	//private Context mContext;
	
	/**
	 * �p�[�c��ǉ�����
	 * <pre>
	 * �C�ӂ̃p�[�cID��type�Ŏw�肵��GUI�p�[�c�𐶐����܂��B
	 * add�������_��initialPos�̈ʒu�Ƀp�[�c���`�悳��܂��B�p�[�cID��GuiPartsHandler�ɒ萔��`����Ă��܂��B
	 * <br />
	 * <b>�p�[�cID�͏d�����Ă͂����܂���B</b>�p�[�cID���d�����Ă���ꍇ��ERROR_CONFLICT_ID��Ԃ��܂��B
	 * </pre>
	 * @param partsId �C�ӂ̃p�[�cID
	 * @param partsType �p�[�c�̃^�C�v
	 * @param layoutParams �������C�A�E�g�p�����[�^
	 * @throws PartsIdConflictException 
	 * @throws UndefinedPartsTypeException 
	 * @throws NotStartedExecuteActivity
	 */
	public void addGuiParts(String partsId, int partsType, GuiPartsHandler.LayoutParams layoutParams) throws PartsIdConflictException, UndefinedPartsTypeException, NotStartedExecuteActivity{
		/* ID���d�����Ă��Ȃ����m�F���� */
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
	 * �p�[�c���폜����
	 * @param partsId �폜�������p�[�c��ID
	 * @throws PartsIdNotfoundException 
	 */
	public void deleteGuiParts(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̍폜����
	}
	
	
	/* �p�[�c�̑��상�\�b�h�Q */
	/**
	 * ���C�A�E�g�̃p�����[�^��ݒ肵�܂�
	 * @param partsId �ݒ肷��p�[�c��ID
	 * @param layoutParams �ݒ肷��p�����[�^
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
	 * ���C�A�E�g�̃p�����[�^���擾���܂�
	 * @param partsId �擾����p�[�c��ID
	 * @return ���C�A�E�g�̃p�����[�^
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
	 * �p�[�c�Ƀe�L�X�g��ݒ肵�܂�
	 * @param partsId �e�L�X�g���Z�b�g����p�[�c
	 * @param text �Z�b�g����e�L�X�g
	 * @throws PartsIdNotfoundException 
	 */
	public void setText(String partsId, String text) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̐ݒ菈��
	}
	
	/**
	 * �p�[�c����e�L�X�g���擾���܂�
	 * <pre>
	 * �����e�L�X�g���擾�ł��Ȃ��悤�ȃp�[�c�������ꍇ�A�󕶎��񂪕Ԃ�܂��B
	 * </pre>
	 * @param partsId �e�L�X�g���擾����p�[�c��ID
	 * @return �擾�����e�L�X�g
	 * @throws PartsIdNotfoundException
	 */
	public String getText(String partsId) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̎擾����
		
		return ("");
	}
	
	/**
	 * �C�x���g���X�i��ݒ肵�܂�
	 * @param partsId �Z�b�g��̃p�[�cID
	 * @param listener �C�x���g���X�i
	 * @throws PartsIdNotfoundException 
	 */
	public void setEventListener(String partsId, GuiPartsEventListener listener) throws PartsIdNotfoundException{
		if (guiPartsHashMap.containsKey(partsId) == false){
			throw new PartsIdNotfoundException();
		}
		
		//TODO: ���ۂ̐ݒ菈��
	}
	
	
	/* Inner Classes */
	//Nothing
	
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
	
	public class NotStartedExecuteActivity extends Exception {
		public NotStartedExecuteActivity(){
			super("Not started izanagi execute activity.");
		}
		
		private static final long serialVersionUID = 7027429261284500743L;
	}
}
