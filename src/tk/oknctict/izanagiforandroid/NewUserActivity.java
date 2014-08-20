package tk.oknctict.izanagiforandroid;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.json.JSONException;

import tk.oknctict.izanagiforandroid.communication.Communicator;
import tk.oknctict.izanagiforandroid.communication.Communicator.IllegalGradeException;
import tk.oknctict.izanagiforandroid.communication.Communicator.IllegalSchoolIdException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* レイアウト */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルの非表示
		setContentView(R.layout.activity_newuser);
		
		Button signupButton = (Button)findViewById(R.id.newuser_signup_button);
		signupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText userId = (EditText)findViewById(R.id.newuser_username);
				EditText passwd = (EditText)findViewById(R.id.newuser_password);
				EditText passwdAgain = (EditText)findViewById(R.id.newuser_password_again);
				EditText email = (EditText)findViewById(R.id.newuser_email);
				
				if (passwd.getText().equals(passwdAgain.getText())){
					// TODO: パスワードが再入力と一致しなかった場合、リジェクト
					return;
				}
				
				/* リクエスト処理 */
				try {
					Communicator communicator = new Communicator();
					try {
						communicator.newUserRequest(userId.getText().toString(), passwd.getText().toString(), email.getText().toString(), 1, 1);
					} catch (NotYetConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalSchoolIdException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalGradeException e) {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
