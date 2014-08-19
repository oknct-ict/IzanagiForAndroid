package tk.oknctict.izanagi.shell;

import tk.oknctict.izanagi.variable.*;

public class ShellVar
{
	private String mName;
	private IzaBasic mValue;

	public ShellVar(String name, IzaBasic value)
	{
		set(name, value);
	}

	public void set(String name, IzaBasic value)
	{
		setName(name);
		setValue(value);
	}

	public int getType()
	{
		return (mValue.getType());
	}

	public String getName()
	{
		return (mName);
	}
	public void setName(String name)
	{
		mName = name;
	}

	public IzaBasic getValue()
	{
		return (mValue);
	}
	public void setValue(IzaBasic value)
	{
		mValue = value;
	}

	public void dump()
	{
		System.out.println(mName + " = (" + mValue.toString() + ")");
	}
}
