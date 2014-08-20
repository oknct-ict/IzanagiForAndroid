package tk.oknctict.izanagiforandroid;

import android.content.Context;

public class IzanagiExecuteActivityHolder {
	private static Context mContext;
	
	public static Context getContext(){
		return mContext;
	}
	
	public static void setContext(Context context){
		mContext = context;
	}
}
