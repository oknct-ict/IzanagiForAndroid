package tk.oknctict.izanagiforandroid;

/**
 * �Z�b�V����ID�̊Ǘ������邽�߂̃N���X�ł�
 * @author marusa
 */
public class SessionIdHolder {
	private static String mSessionId = null;
	
	/**
	 * �Z�b�V����ID��ݒ肷�邽�߂̃��\�b�h
	 * @param sessionId
	 */
	public static void setSessionId(String sessionId){
		mSessionId = sessionId;
	}
	
	/**
	 * �Z�b�V����ID���폜���邽�߂̃��\�b�h
	 */
	public static void delSessionId(){
		mSessionId = null;
	}
	
	/**
	 * �Z�b�V����ID���擾���邽�߂̃��\�b�h
	 * @return
	 */
	public static String getSessionId() {
		return (mSessionId);
	}
}
