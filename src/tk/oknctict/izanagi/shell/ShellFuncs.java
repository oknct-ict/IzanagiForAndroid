package tk.oknctict.izanagi.shell;

import java.util.HashMap;
import java.util.Map;

public class ShellFuncs
{
	private static ShellFuncs instance = new ShellFuncs();
	private final HashMap<String, ShellFunc> mFunc;

	private ShellFuncs()
	{
		mFunc = new HashMap<String, ShellFunc>();
	}
	public static ShellFuncs getInstance()
	{
		return (instance);
	}

	public ShellFunc get(String name)
	{
		return (mFunc.get(name));
	}

	public void set(String name, ShellFunc shellFunc)
	{
		if (mFunc.containsKey(name)){
			System.out.println("すでに同じ名前の関数が宣言されています");
		}
		else {
			mFunc.put(name, shellFunc);
		}
	}

	public boolean usedName(String name)
	{
		return (mFunc.containsKey(name));
	}

	public void dump()
	{
		System.out.println("[");
		for (Map.Entry<String, ShellFunc> e : mFunc.entrySet()){
			e.getValue().dump();
		}
		System.out.println("]");
	}
}
