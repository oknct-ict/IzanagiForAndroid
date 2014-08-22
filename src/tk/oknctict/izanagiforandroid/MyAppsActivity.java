package tk.oknctict.izanagiforandroid;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;

import tk.oknctict.izanagiforandroid.communication.Communicator;
import tk.oknctict.izanagiforandroid.communication.Communicator.ExecutionResult;
import tk.oknctict.izanagiforandroid.communication.Communicator.LogMessage;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyAppsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myapps);
		NowActivityHolder.setContext(this);
		
		MyAppsAdapter adapter = new MyAppsAdapter();
		
		ProjectData proj1 = new ProjectData("hoge1", new Date());
		ProjectData proj2 = new ProjectData("hoge2", new Date());
		adapter.add(proj1);
		adapter.add(proj2);
		
		ListView listView = (ListView)findViewById(R.id.myapps_listView);
		listView.setAdapter(adapter);
		
		Button testButton = (Button)findViewById(R.id.myapps_test_button);
		testButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Communicator communicator;
				try {
					communicator = new Communicator();
					communicator.postLog(new LogMessage("test", "This is a test message."));
					communicator.postFinishedExecution(new ExecutionResult(0));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
			}
		});
	}
	
	private class MyAppsAdapter extends BaseAdapter {
		private ArrayList<ProjectData> dataList = new ArrayList<MyAppsActivity.ProjectData>();
		
		public void add(ProjectData data){
			dataList.add(data);
		}
		
		@Override
		public int getCount() {
			return (dataList.size());
		}

		@Override
		public Object getItem(int position) {
			return (dataList.get(position));
		}

		@Override
		public long getItemId(int position) {
			return (position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int listItemLayoutId = R.layout.listlayout;
			
			View v = convertView;
			
			/* レイアウトの設定 */
			if (v == null){
				LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(listItemLayoutId, null);
			}
			
			/* レイアウトへのデータの設定 */
			ProjectData projectData = dataList.get(position);
			if (projectData != null){
				//ImageView listImage = (ImageView)findViewById(listItemLayoutId);
				TextView projectNameText = (TextView)v.findViewById(R.id.listlayout_textview1);
				TextView lastUpdate = (TextView)v.findViewById(R.id.listlayout_textview2);
				
				projectNameText.setText(projectData.getProjectName());
				lastUpdate.setText(projectData.getLastUpdate().toString());
			}
			
			return v;
		}
	}
	
	@SuppressWarnings("unused")
	private class ProjectData {
		private String mProjectName = "";
		private Date mLastUpdate = null;
		
		public ProjectData(){}
		
		public ProjectData(String projectName, Date lastUpadate){
			mProjectName = projectName;
			mLastUpdate = lastUpadate;
		}

		public String getProjectName() {
			return mProjectName;
		}

		public void setProjectName(String projectName) {
			this.mProjectName = projectName;
		}

		public Date getLastUpdate() {
			return mLastUpdate;
		}

		public void setLastUpdate(Date lastUpdate) {
			this.mLastUpdate = lastUpdate;
		}
	}
}
