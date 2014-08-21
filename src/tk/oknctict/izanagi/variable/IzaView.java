package tk.oknctict.izanagi.variable;

import java.util.ArrayList;
import tk.oknctict.izanagi.variable.*;
import tk.oknctict.izanagi.shell.*;

public abstract class  IzaView implements IzaBasic
{
	protected ShellInterface mShellInterface;
	protected int mType;
	private float mX, mY;
	private float mWidth, mHeight;
	private String mText;

	private IzaEvent onClick;

	public int getType()
	{
		return (mType);
	}

	public void setEvent(IzaEvent event)
	{
		int eventType = event.getEventType();

		switch (eventType){
			case IzaEvent.EVENT_CLICK:
				setClickEvent(event);
				break;
		}
	}
	public IzaEvent getClickEvent()
	{
		return (onClick);
	}
	public void setClickEvent(IzaEvent event)
	{
		onClick = new IzaEvent(event);
	}

	public float getX()
	{
		return (mX);
	}
	public void setX(float x)
	{
		mX = x;
		mShellInterface.updateView(this);
	}

	public float getY()
	{
		return (mY);
	}
	public void setY(float y)
	{
		mY = y;
		mShellInterface.updateView(this);
	}

	public float getWidth()
	{
		return (mWidth);
	}
	public void setWidth(float width)
	{
		mWidth = width;
		mShellInterface.updateView(this);
	}

	public float getHeight()
	{
		return (mHeight);
	}
	public void setHeight(float height)
	{
		mHeight = height;
		mShellInterface.updateView(this);
	}

	public String getText()
	{
		return (mText);
	}
	public void setText(String text)
	{
		mText = text;
		mShellInterface.updateView(this);
	}

	public String toString()
	{
		return (String.format("type = %d, x = %f, y = %f, width = %f, height = %f", mType, mX, mY, mWidth, mHeight));
	}


	public abstract IzaBasic cast(int type);
	public abstract IzaBasic clone();






























	public void set(IzaBasic value)
	{
	}
	public IzaBasic add(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic sub(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mul(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic div(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mod(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic pow(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic and(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic or(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic not(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean EQ(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean NEQ(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean GT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean GE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BAnd(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BOr(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic minus()
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic subscript(String index)
	{
		System.out.println("許されない演算です");
		return (null);
	}
}
