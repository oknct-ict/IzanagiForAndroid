package tk.oknctict.izanagi.shell;

public class ShellArg
{
	String mName;
	int mType;

	public ShellArg(String name, int type)
	{
		setName(name);
		setType(type);
	}
	public ShellArg(String name, String type)
	{
		setName(name);
		setType(type);
	}

	public String getName()
	{
		return (mName);
	}
	public void setName(String name)
	{
		mName = name;
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
}
