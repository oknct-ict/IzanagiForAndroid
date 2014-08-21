package tk.oknctict.izanagiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAppActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myapps);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listlayout);
		adapter.add("hoge1");
		adapter.add("hoge2");
		
		ListView listView = (ListView)findViewById(R.id.myapps_listView);
		listView.setAdapter(adapter);
	}
}
