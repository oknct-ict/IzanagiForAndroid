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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewUserActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* ���C�A�E�g */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //�^�C�g���̔�\��
		setContentView(R.layout.activity_newuser);
		
		/* �h���b�v�_�E�����X�g�̃R���e���c�̐ݒ� */
		Spinner institutionSpinner = (Spinner)findViewById(R.id.newuser_institution);
		ArrayAdapter<CharSequence> institutionsAdapter = ArrayAdapter.createFromResource(this, R.array.newuser_institution_array, android.R.layout.simple_spinner_item);
		institutionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		institutionSpinner.setAdapter(institutionsAdapter);
		
		Button signupButton = (Button)findViewById(R.id.newuser_signup_button);
		signupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText userId = (EditText)findViewById(R.id.newuser_username);
				EditText passwd = (EditText)findViewById(R.id.newuser_password);
				EditText passwdAgain = (EditText)findViewById(R.id.newuser_password_again);
				EditText email = (EditText)findViewById(R.id.newuser_email);
				
				if (passwd.getText().equals(passwdAgain.getText())){
					// TODO: �p�X���[�h���ē��͂ƈ�v���Ȃ������ꍇ�A���W�F�N�g
					return;
				}
				
				/* ���N�G�X�g���� */
				try {
					Communicator communicator = new Communicator();
					communicator.establishConnection();
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
		
		/* �L�����Z���{�^�� */
		Button canceleButton = (Button)findViewById(R.id.newuser_cancel_button);
		canceleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewUserActivity.this.finish();
			}
		});
	}
}
