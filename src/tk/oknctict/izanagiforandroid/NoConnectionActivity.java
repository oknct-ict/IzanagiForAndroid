package tk.oknctict.izanagiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NoConnectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noconnection);
		
		Button reConnectButton = (Button)findViewById(R.id.noconnection_reconnect_button);
		reConnectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), SplashActivity.class);
				startActivity(intent);
				
				NoConnectionActivity.this.finish();
			}
		});
	}
}
