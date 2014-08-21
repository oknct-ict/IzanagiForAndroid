package tk.oknctict.izanagiforandroid;

import android.content.Context;

public class NowActivityHolder{
	private static Context context;

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		NowActivityHolder.context = context;
	}
}
