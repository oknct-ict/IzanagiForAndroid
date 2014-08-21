package tk.oknctict.izanagi.variable;

import tk.oknctict.izanagi.parser.ASTFuncBlock;

public class IzaEvent
{
	public static final int EVENT_NON   = 0;
	public static final int EVENT_CLICK = 1;

	public static final String EVENT_CLICK_NAME = "Click";

	private String mFuncName;
	private ASTFuncBlock mBlock;
	private int mEventType;

	public IzaEvent(String funcName, ASTFuncBlock block, int eventType)
	{
		setFuncName(funcName);
		setBlock(block);
		setEventType(eventType);
	}
	public IzaEvent(IzaEvent event)
	{
		setFuncName(event.getFuncName());
		setBlock(event.getBlock());
		setEventType(event.getEventType());
	}

	public String getFuncName()
	{
		return (mFuncName);
	}
	public void setFuncName(String funcName)
	{
		mFuncName = funcName;
	}

	public ASTFuncBlock getBlock()
	{
		return (mBlock);
	}
	public void setBlock(ASTFuncBlock block)
	{
		mBlock = block;
	}

	public int getEventType()
	{
		return (mEventType);
	}
	public void setEventType(int eventType)
	{
		mEventType = eventType;
	}
}
