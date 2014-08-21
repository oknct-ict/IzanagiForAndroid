package tk.oknctict.izanagiforandroid;

import java.io.StringReader;

import tk.oknctict.izanagi.parser.ASTStart;
import tk.oknctict.izanagi.parser.ExprParser;
import tk.oknctict.izanagi.parser.ParseException;
import tk.oknctict.izanagi.shell.ShellVisitor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IzanagiExecuteActivity extends Activity {

	private static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
	private static boolean mDebugMode = false;
	
	private static LinearLayout mLinearLayout;
	private static TextView mDebugView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_izanagi_execute);
		
		mLinearLayout = new LinearLayout(this);
		mLinearLayout.setBackgroundColor(Color.WHITE);
		setContentView(mLinearLayout);
		
		mDebugView = new TextView(this);
		mDebugView.setTextColor(Color.BLACK);
		mLinearLayout.addView(mDebugView, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
		setDebugMode();
		
		String code = "Print \"Hello, World!\"";
		ExprParser parser = new ExprParser(new StringReader(code));
		ShellVisitor visitor = new ShellVisitor();
		try {
			ASTStart start = parser.Start();
			start.jjtAccept(visitor, null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setDebugMode()
	{
		mDebugMode = true;

		mLinearLayout.removeAllViews();
		mLinearLayout.addView(mDebugView, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
	}
	public static void debubPrint(String str)
	{
		if (mDebugMode == true){
			mDebugView.append(str);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.izanagi_execute, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
