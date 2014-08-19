package tk.oknctict.izanagi.shell;

import tk.oknctict.izanagi.parser.ASTFuncBlock;
import java.util.ArrayList;
import tk.oknctict.izanagi.variable.*;

public class ShellFunc
{
	String mName;
	ShellArgs mArgs;
	int mType;
	ASTFuncBlock mBlock;

	public ShellFunc(String name, ShellArgs args, int type, ASTFuncBlock block)
	{
		setName(name);
		setArgs(args);
		setType(type);
		setBlock(block);
	}
	public ShellFunc(String name, ShellArgs args, String type, ASTFuncBlock block)
	{
		setName(name);
		setArgs(args);
		setType(type);
		setBlock(block);
	}

	public boolean checkArgs(ArrayList<IzaBasic> valueList)
	{
		ArrayList<ShellArg> argList = mArgs.getArgList();
		int size = argList.size();
		
		if (valueList.size() != size){
			System.out.println("引数の数が違います");
			return (false);
		}

		for (int i = 0; i < size; i++){
			int typeA = valueList.get(i).getType();
			int typeB = argList.get(i).getType();

			if (typeA != typeB){
				System.out.println("引数の型が違います");
				return (false);
			}
		}

		return (true);
	}

	public String getName()
	{
		return (mName);
	}
	public void setName(String name)
	{
		mName = name;
	}

	public ShellArgs getArgs()
	{
		return (mArgs);
	}
	public void setArgs(ShellArgs args)
	{
		mArgs = args;
	}

	public int getType()
	{
		return (mType);
	}
	public void setType(int type)
	{
		mType = type;
	}
	public void setType(String type)
	{
		if (type.equals(ShellValue.TYPE_STRING_NONE)){
			setType(ShellValue.TYPE_NONE);
		}
		else if (type.equals(ShellValue.TYPE_STRING_INTEGER)){
			setType(ShellValue.TYPE_INTEGER);
		}
		else if (type.equals(ShellValue.TYPE_STRING_FLOAT)){
			setType(ShellValue.TYPE_FLOAT);
		}
		else if (type.equals(ShellValue.TYPE_STRING_STRING)){
			setType(ShellValue.TYPE_STRING);
		}
		else if (type.equals(ShellValue.TYPE_STRING_BOOLEAN)){
			setType(ShellValue.TYPE_BOOLEAN);
		}
	}

	public ASTFuncBlock getBlock()
	{
		return (mBlock);
	}
	public void setBlock(ASTFuncBlock block)
	{
		mBlock = block;
	}

	public void dump()
	{
		System.out.println(mName + ":" + mType);
	}
}
