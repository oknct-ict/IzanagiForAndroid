package tk.oknctict.izanagiforandroid;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import tk.oknctict.izanagiforandroid.R.id;
import tk.oknctict.izanagiforandroid.communication.Communicator;
import tk.oknctict.izanagiforandroid.communication.WebSocketHandlerSingleton.IWebSocketHandlerListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {
	public static LoginActivity instance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NowActivityHolder.setContext(this);
		instance = this; // TODO: インスタンスが存在しない場合不安ですね・・・
		
		/* タイトル */
		requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルの非表示
		setContentView(R.layout.activity_login);
		
		Button login_button = (Button)findViewById(id.login_login_button);
		login_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(LoginActivity.this, "login", Toast.LENGTH_SHORT).show();
				
				EditText usernameBox = (EditText)findViewById(R.id.login_username);
				EditText passwdBox = (EditText)findViewById(R.id.login_password);
				
				String username = usernameBox.getText().toString();
				String passwd = passwdBox.getText().toString();
				
				try {
					Communicator communicator = new Communicator();
					communicator.establishConnection();
					try {
						/* ログインの実行と、メッセージ受信時の挙動の設定 */
						communicator.login(username, passwd, new IWebSocketHandlerListener() {
							@Override
							public void onMessage(String message) {
								int result = -1;
								try {
									JSONObject obj = new JSONObject(message);
									JSONObject data = new JSONObject(obj.getString("data"));
									result = data.getInt("result");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								if (result == 0){
									Intent intent = new Intent(getApplication(), MyAppsActivity.class);
									startActivity(intent);
									
									LoginActivity.this.finish();
								}
							}
							
							@Override
							public void onOpen(ServerHandshake handshakedata) {}
							@Override
							public void onError(Exception ex) {}
							@Override
							public void onClose(int code, String reason, boolean remote) {}
						});
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
		
		Button registarButton = (Button)findViewById(R.id.login_newuser_button);
		registarButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), NewUserActivity.class);
				startActivity(intent);
			}
		});
	}
}
