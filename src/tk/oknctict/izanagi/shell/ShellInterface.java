package tk.oknctict.izanagi.shell;

import java.util.ArrayList;
import tk.oknctict.izanagi.variable.*;
import tk.oknctict.izanagi.shell.*;
import tk.oknctict.izanagi.parser.*;
import tk.oknctict.izanagiforandroid.guimanager.GuiManagerSingleton;
import tk.oknctict.izanagiforandroid.guimanager.GuiManagerSingleton.PartsIdNotfoundException;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.GuiPartsEventListener;
import tk.oknctict.izanagiforandroid.guimanager.GuiPartsHandler.LayoutParams;

public class ShellInterface
{
	private static final ShellInterface instance = new ShellInterface();

	private final String FUNC_GET_X 	 = "stdGetX";
	private final String FUNC_SET_X 	 = "stdSetX";
	private final String FUNC_GET_Y 	 = "stdGetY";
	private final String FUNC_SET_Y 	 = "stdSetY";
	private final String FUNC_GET_WIDTH  = "stdGetWidth";
	private final String FUNC_SET_WIDTH  = "stdSetWidth";
	private final String FUNC_GET_HEIGHT = "stdGetHeight";
	private final String FUNC_SET_HEIGHT = "stdSetHeight";
	private final String FUNC_GET_TEXT	 = "stdGetText";
	private final String FUNC_SET_TEXT	 = "stdSetText";

	private ShellVarsManager mVarsMng;
	private ShellFuncs mFuncs;
	private GuiManagerSingleton mGuiManager;

	private ShellVisitor mVisitor;

	private ShellInterface()
	{
		mVarsMng = ShellVarsManager.getInstance();
		mFuncs = ShellFuncs.getInstance();
		mGuiManager = GuiManagerSingleton.getInstance();
		mVisitor = new ShellVisitor();
	}
	public static ShellInterface getInstance()
	{
		return (instance);
	}

	public void setEvent(String name, IzaEvent event)
	{
		setEvent(name, event.getFuncName(), event.getBlock(), event.getEventType());
	}
	public void setEvent(String name, String funcName, ASTFuncBlock block, String eventType)
	{
		int event = toIEventType(eventType);

		setEvent(name, funcName, block, event);
	}
	public void setEvent(String name, String funcName, ASTFuncBlock block, int eventType)
	{
		IzaBasic view = new IzaNone();
		String viewId;

		if (mVarsMng.usedName(name) == false){
			System.out.println("not find view");
			return;
		}

		view = mVarsMng.get(name).getValue();
		if ((view instanceof IzaView) == false){
			System.out.println("type is not view");
			return;
		}

		IzaEvent event = new IzaEvent(funcName, block, eventType);
		ShellArgs args = new ShellArgs();
		ShellFunc func = new ShellFunc(funcName, args, view.getType(), block);
		((IzaView)view).setEvent(event);

		mFuncs.set(funcName, func);
		
//		viewId = String.valueOf(view.hashCode());
//		GuiPartsEventListener listener = new GuiPartsEventListener();
//		listener.addMap(toSEventType(eventType), funcName);
	}

	public void createView(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = makeLayoutParams(view);
		
//		mGuiManager.addGuiParts(viewId, view.getType(), layout);
	}
	public void updateView(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = makeLayoutParams(view);

//		mGuiManager.setLayoutParams(viewId, layout);
	}
	
	public int loadX(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = null;

//		layout = mGuiManager.getLayoutParams(viewId);
//		return (layout.x);
		
		return (0);
	}
	public int loadY(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = null;

//		layout = mGuiManager.getLayoutParams(viewId);
//		return (layout.y);
		
		return (0);
	}
	public int loadWidth(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = null;

//		layout = mGuiManager.getLayoutParams(viewId);
//		return (layout.width);
		
		return (0);
	}
	public int loadHeight(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		LayoutParams layout = null;

//		layout = mGuiManager.getLayoutParams(viewId);
//		return (layout.height);
		
		return (0);
	}
	public String loadText(IzaView view)
	{
		String viewId = String.valueOf(view.hashCode());
		
		try {
			return (mGuiManager.getText(viewId));
		} catch (PartsIdNotfoundException e) {
			e.printStackTrace();
		}

		return ("");
	}
	
	private LayoutParams makeLayoutParams(IzaView view)
	{
		int x, y;
		int width, height;
		
		x = (int)view.getX();
		y = (int)view.getY();
		width  = (int)view.getWidth();
		height = (int)view.getHeight();
		LayoutParams layout = new LayoutParams(x, y, width, height);
		
		return (layout);
	}

	public void callFunc(String funcName)
	{
		ShellFunc func = mFuncs.get(funcName);

		ShellVisitor.cFuncsNameStack.push(funcName);
		mVarsMng.intoFunc();
		mVisitor.visit(func.getBlock(), null);
	}

	public IzaBasic callStdFunc(String funcName, ArrayList<IzaBasic> valueList)
	{
		IzaBasic returnValue= new IzaNone();
		IzaBasic view = new IzaNone();
		String name;
		int size;

		size = valueList.size();

		if (size < 1){
			System.out.println("引数がおかしいです");
			return (returnValue);
		}

		view = valueList.get(0);
		if ((view instanceof IzaView) == false){
			return (returnValue);
		}

		if (funcName.equals(FUNC_GET_X)){
			float x = ((IzaView)view).getX();
			returnValue = new IzaFloat(x);
		}
		else if (funcName.equals(FUNC_SET_X)){
			if (size < 2){
				System.out.println("引数がおかしいです");
			}
			else {
				float x = ((IzaFloat)valueList.get(1)).mValue;
				((IzaView)view).setX(x);
				returnValue = new IzaFloat();
			}
		}
		else if (funcName.equals(FUNC_GET_Y)){
			float y = ((IzaView)view).getY();
			returnValue = new IzaFloat(y);
		}
		else if (funcName.equals(FUNC_SET_Y)){
			if (size < 2){
				System.out.println("引数がおかしいです");
			}
			else {
				float y = ((IzaFloat)valueList.get(1)).mValue;
				((IzaView)view).setY(y);
				returnValue = new IzaFloat();
			}
		}
		else if (funcName.equals(FUNC_GET_WIDTH)){
			float width = ((IzaView)view).getWidth();
			returnValue = new IzaFloat(width);
		}
		else if (funcName.equals(FUNC_SET_WIDTH)){
			if (size < 2){
				System.out.println("引数がおかしいです");
			}
			else {
				float width = ((IzaFloat)valueList.get(1)).mValue;
				((IzaView)view).setWidth(width);
				returnValue = new IzaFloat();
			}
		}
		else if (funcName.equals(FUNC_GET_HEIGHT)){
			float height = ((IzaView)view).getHeight();
			returnValue = new IzaFloat(height);
		}
		else if (funcName.equals(FUNC_SET_HEIGHT)){
			if (size < 2){
				System.out.println("引数がおかしいです");
			}
			else {
				float height = ((IzaFloat)valueList.get(1)).mValue;
				((IzaView)view).setHeight(height);
				returnValue = new IzaFloat();
			}
		}
		else if (funcName.equals(FUNC_GET_TEXT)){
			String text = ((IzaView)view).getText();
			returnValue = new IzaString(text);
		}
		else if (funcName.equals(FUNC_SET_TEXT)){
			if (size < 2){
				System.out.println("引数がおかしいです");
			}
			else {
				String text = ((IzaString)valueList.get(1)).mValue;
				((IzaView)view).setText(text);
				returnValue = new IzaString();
			}
		}

		return (returnValue);
	}

	private int toIEventType(String str)
	{
		if (str.equals(IzaEvent.EVENT_CLICK_NAME)){
			return (IzaEvent.EVENT_CLICK);
		}

		return (IzaEvent.EVENT_NON);
	}
	private String toSEventType(int num)
	{
		if (num == IzaEvent.EVENT_CLICK){
			return (IzaEvent.EVENT_CLICK_NAME);
		}
		
		return ("");
	}
}
