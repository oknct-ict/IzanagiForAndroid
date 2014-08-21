package tk.oknctict.izanagi.variable;

import tk.oknctict.izanagi.parser.ASTFuncBlock;
import tk.oknctict.izanagi.shell.*;

public class IzaButton extends IzaView
{
	public IzaButton()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	public IzaButton(float x, float y, float width, float height)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_BUTTON;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	public IzaButton(IzaButton button)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_BUTTON;
		setX(button.getX());
		setY(button.getY());
		setWidth(button.getWidth());
		setHeight(button.getHeight());
	}

	@Override
	public IzaBasic cast(int type)
	{
		IzaBasic value = new IzaNone();

		switch (type){
			case TYPE_BUTTON:
				value = new IzaButton(this);
				break;
		}

		return (value);
	}

	@Override
	public IzaBasic clone()
	{
		IzaBasic value = new IzaButton(this);
		return (value);
	}

}

