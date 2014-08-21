package tk.oknctict.izanagi.variable;

import tk.oknctict.izanagi.parser.ASTFuncBlock;
import tk.oknctict.izanagi.shell.*;

public class IzaTextView extends IzaView
{
	public IzaTextView()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	public IzaTextView(float x, float y, float width, float height)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_TEXTVIEW;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	public IzaTextView(IzaTextView textView)
	{
		mShellInterface = ShellInterface.getInstance();
		mShellInterface.createView(this);

		mType = TYPE_TEXTVIEW;
		setX(textView.getX());
		setY(textView.getY());
		setWidth(textView.getWidth());
		setHeight(textView.getHeight());
	}

	@Override
	public IzaBasic cast(int type)
	{
		IzaBasic value = new IzaNone();

		switch (type){
			case TYPE_TEXTVIEW:
				value = new IzaTextView(this);
				break;
		}

		return (value);
	}

	@Override
	public IzaBasic clone()
	{
		IzaTextView value = new IzaTextView(this);
		return (value);
	}

}

