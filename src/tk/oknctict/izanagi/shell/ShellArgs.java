package tk.oknctict.izanagi.shell;

import java.util.ArrayList;

public class ShellArgs
{
	private ArrayList<ShellArg> mArgList = new ArrayList<ShellArg>();

	public ShellArgs(ArrayList<ShellArg> argList)
	{
		mArgList = argList;
	}
	public ShellArgs()
	{
	}

	public ArrayList<ShellArg> getArgList()
	{
		return (mArgList);
	}

	public void add(ShellArg arg)
	{
		mArgList.add(arg);
	}
}
