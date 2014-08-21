package tk.oknctict.izanagiforandroid;

import java.io.StringReader;
import java.util.HashMap;

import tk.oknctict.izanagi.parser.ASTStart;
import tk.oknctict.izanagi.parser.ExprParser;
import tk.oknctict.izanagi.parser.ParseException;
import tk.oknctict.izanagi.shell.ShellVisitor;
import tk.oknctict.izanagi.variable.IzaView;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.LayoutParams;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IzanagiExecuteActivity extends Activity {

	private static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private static final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	private static boolean mDebugMode = false;
	private static Context mContext;
	
//	private static LinearLayout mLinearLayout;
	private static RelativeLayout mRelativeLayout;
	private static TextView mDebugView;
	
	private static HashMap<String, View> mViewMap;
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_izanagi_execute);
		
		mContext = this;
		mViewMap = new HashMap<String, View>();
		
//		mLinearLayout = new LinearLayout(this);
//		mLinearLayout.setBackgroundColor(Color.WHITE);
		mRelativeLayout = new RelativeLayout(this);
		mRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(FP, FP));
		mRelativeLayout.setBackgroundColor(Color.WHITE);
		setContentView(mRelativeLayout);
		
		mDebugView = new TextView(this);
		mDebugView.setTextColor(Color.BLACK);
//		mLinearLayout.addView(mDebugView, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
//		setDebugMode();
		
		String code = "Dim button as Button";
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

//		mLinearLayout.removeAllViews();
//		mLinearLayout.addView(mDebugView, new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
	}
	public static void debubPrint(String str)
	{
		if (mDebugMode == true){
			mDebugView.append(str);
		}
	}
	
	public static void addView(String viewId, int viewType, LayoutParams params)
	{
		View view = pickupView(viewType);
		view.setId(Integer.valueOf(viewId));
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(params.width, params.height);
		layoutParams.setMargins(params.x, params.y, 0, 0);
		
		mRelativeLayout.addView(view, layoutParams);
	}
	public static void changeView(String viewId, LayoutParams params)
	{
		View view;
		view = mRelativeLayout.findViewById(Integer.valueOf(viewId));
		
	}
	
	private static View pickupView(int viewType)
	{
		View view = new Button(mContext);
		
		switch (viewType){
		case IzaView.TYPE_BUTTON:
			view = new Button(mContext);
			break;
		
		case IzaView.TYPE_TEXTVIEW:
			view = new TextView(mContext);
			break;
			
		case IzaView.TYPE_EDITTEXT:
			view = new EditText(mContext);
			break;
		}
		
		return (view);
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
