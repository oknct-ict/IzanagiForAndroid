package tk.oknctict.izanagiforandroid;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.json.JSONException;

import tk.oknctict.izanagiforandroid.R.id;
import tk.oknctict.izanagiforandroid.communication.Communicator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button login_button = (Button)findViewById(id.login_login_button);
		login_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, "login", Toast.LENGTH_SHORT).show();
				
				EditText usernameBox = (EditText)findViewById(R.id.login_username);
				EditText passwdBox = (EditText)findViewById(R.id.login_passwd);
				
				String username = usernameBox.getText().toString();
				String passwd = passwdBox.getText().toString();
				
				try {
					Communicator communicator = new Communicator();
					communicator.establishConnection();
					try {
						communicator.login(username, passwd);
					} catch (NotYetConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
