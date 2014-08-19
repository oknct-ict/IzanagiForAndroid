package tk.oknctict.izanagi.variable;

public interface IzaBasic
{
	int TYPE_NONE = 0;
	int TYPE_INTEGER = 1;
	int TYPE_FLOAT = 2;
	int TYPE_STRING = 3;
	int TYPE_BOOLEAN = 4;
	int TYPE_ARRAY = 5;

	public void set(IzaBasic value);
	public IzaBasic add(IzaBasic right);
	public IzaBasic sub(IzaBasic right);
	public IzaBasic mul(IzaBasic right);
	public IzaBasic div(IzaBasic right);
	public IzaBasic mod(IzaBasic right);
	public IzaBasic pow(IzaBasic right);
	public IzaBasic and(IzaBasic right);
	public IzaBasic or(IzaBasic right);
	public IzaBasic not(IzaBasic right);
	public IzaBoolean EQ(IzaBasic right);
	public IzaBoolean NEQ(IzaBasic right);
	public IzaBoolean GT(IzaBasic right);
	public IzaBoolean GE(IzaBasic right);
	public IzaBoolean LT(IzaBasic right);
	public IzaBoolean LE(IzaBasic right);
	public IzaBoolean BAnd(IzaBasic right);
	public IzaBoolean BOr(IzaBasic right);
	public IzaBasic minus();
	public IzaBasic subscript(String index);

	public IzaBasic cast(int type);
	public int getType();
	public IzaBasic clone();

	public String toString();
}
