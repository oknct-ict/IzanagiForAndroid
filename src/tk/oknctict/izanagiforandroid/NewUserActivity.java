package tk.oknctict.izanagiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NewUserActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* ���C�A�E�g */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //�^�C�g���̔�\��
		setContentView(R.layout.activity_newuser);
	}
}
