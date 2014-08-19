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
		
		/* レイアウト */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルの非表示
		setContentView(R.layout.activity_splash);
		
		try {
			Communicator communicator = new Communicator();
			communicator.establishConnection();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		/* 3600ms待機 */
		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 3600);
	}
	
	class splashHandler implements Runnable {
		@Override
		public void run() {
			/* TODO:
			 * ログイン済みか識別して遷移先の変更を行う
			 */
			Intent intent = new Intent(getApplication(), LoginActivity.class);
			startActivity(intent);
			
			SplashActivity.this.finish();
		}
	}
}
