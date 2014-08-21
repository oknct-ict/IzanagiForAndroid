package tk.oknctict.izanagi.variable;

import tk.oknctict.izanagi.parser.ASTFuncBlock;
import tk.oknctict.izanagi.shell.*;

public class IzaEditText extends IzaView
{
	public IzaEditText()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	public IzaEditText(float x, float y, float width, float height)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_EDITTEXT;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	public IzaEditText(IzaEditText editText)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_EDITTEXT;
		setX(editText.getX());
		setY(editText.getY());
		setWidth(editText.getWidth());
		setHeight(editText.getHeight());
	}

	@Override
	public IzaBasic cast(int type)
	{
		IzaBasic value = new IzaNone();

		switch (type){
			case TYPE_EDITTEXT:
				value = new IzaEditText(this);
				break;
		}

		return (value);
	}

	@Override
	public IzaBasic clone()
	{
		IzaEditText value = new IzaEditText(this);
		return (value);
	}

}

