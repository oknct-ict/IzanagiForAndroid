package tk.oknctict.izanagiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NewUserActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* レイアウト */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルの非表示
		setContentView(R.layout.activity_newuser);
	}
}
