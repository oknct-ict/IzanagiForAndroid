package tk.oknctict.izanagi.shell;

public class ShellGlobalVars extends ShellVars
{
	private static ShellGlobalVars instance = new ShellGlobalVars();

	private ShellGlobalVars()
	{
		super();
	}
	public static ShellGlobalVars getInstance()
	{
		return (instance);
	}
}
