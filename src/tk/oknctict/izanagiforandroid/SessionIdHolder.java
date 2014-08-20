package tk.oknctict.izanagiforandroid;

/**
 * セッションIDの管理をするためのクラスです
 * @author marusa
 */
public class SessionIdHolder {
	private static String mSessionId = null;
	
	/**
	 * セッションIDを設定するためのメソッド
	 * @param sessionId
	 */
	public static void setSessionId(String sessionId){
		mSessionId = sessionId;
	}
	
	/**
	 * セッションIDを削除するためのメソッド
	 */
	public static void delSessionId(){
		mSessionId = null;
	}
	
	/**
	 * セッションIDを取得するためのメソッド
	 * @return
	 */
	public static String getSessionId() {
		return (mSessionId);
	}
}
