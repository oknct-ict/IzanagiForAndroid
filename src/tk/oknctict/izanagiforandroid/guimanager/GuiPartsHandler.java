package tk.oknctict.izanagiforandroid.guimanager;

import java.util.HashMap;

/**
 * GUI���i���̂��̂ƕ��i�̑���̃C���^�[�t�F�C�X�����N���X�ł�
 * @author marusa
 */
public class GuiPartsHandler {
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
}
