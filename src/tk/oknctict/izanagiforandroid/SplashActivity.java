package tk.oknctict.izanagiforandroid;

import java.net.URISyntaxException;

import tk.oknctict.izanagiforandroid.communication.Communicator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* ���C�A�E�g */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //�^�C�g���̔�\��
		setContentView(R.layout.activity_splash);
		
		try {
			Communicator communicator = new Communicator();
			communicator.establishConnection();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		/* 3600ms�ҋ@ */
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 3600);
	}
	
	class splashHandler implements Runnable {
		@Override
		public void run() {
			/* TODO:
			 * ���O�C���ς݂����ʂ��đJ�ڐ�̕ύX���s��
			 */
			//Intent intent = new Intent(getApplication(), LoginActivity.class);
			Intent intent = new Intent(getApplication(), IzanagiExecuteActivity.class);
			startActivity(intent);
			
			SplashActivity.this.finish();
		}
	}
}
