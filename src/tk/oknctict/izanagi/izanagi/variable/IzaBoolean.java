package tk.oknctict.izanagi.variable;

public class IzaBoolean implements IzaBasic
{
	public boolean mValue;
	public int mType;

	public IzaBoolean()
	{
		this(true);
	}
	public IzaBoolean(boolean value)
	{
		mValue = value;
		mType = TYPE_BOOLEAN;
	}
	public IzaBoolean(IzaBoolean izaBoolean)
	{
		mValue = izaBoolean.mValue;
		mType = TYPE_BOOLEAN;
	}

	public void set(IzaBasic value)
	{
		mValue = ((IzaBoolean)value).mValue;
	}

	public IzaBasic add(IzaBasic right)
	{
		if (right instanceof IzaString){
			String value = ((IzaString)right).mValue;
			IzaString result = new IzaString("" + mValue + value);
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}
	public IzaBasic sub(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mul(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic div(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic mod(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic pow(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic and(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic or(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic not(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBoolean EQ(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);

		if (right instanceof IzaBoolean){
			boolean value = ((IzaBoolean)right).mValue;
			if (mValue == value){
				return (True);
			}
		}

		return (False);
	}
	public IzaBoolean NEQ(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);
		IzaBoolean eq = EQ(right);

		if (eq.mValue == false){
			return (True);
		}

		return (False);
	}
	public IzaBoolean GT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean GE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LT(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean LE(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BAnd(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);

		if (right instanceof IzaBoolean){
			boolean value = ((IzaBoolean)right).mValue;
			if (mValue && value){
				return (True);
			}
			else {
				return (False);
			}
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}
	public IzaBoolean BOr(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);

		if (right instanceof IzaBoolean){
			boolean value = ((IzaBoolean)right).mValue;
			if (mValue || value){
				return (True);
			}
			else {
				return (False);
			}
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}

	public IzaBasic minus()
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBasic subscript(String index)
	{
		System.out.println("許されない演算です");
		return (null);
	}

	public IzaBasic cast(int type)
	{
		IzaBasic result;
		switch (type){
			case TYPE_STRING:
				result = toIzaString();
				break;

			case TYPE_BOOLEAN:
				result = this;
				break;

			default:
				System.out.println("許されないキャストです");
				return (null);
		}
		
		return (result);
	}

	public IzaBasic toIzaString()
	{
		IzaString value = new IzaString("" + mValue);
		return (value);
	}

	public int getType()
	{
		return (mType);
	}

	public IzaBasic clone()
	{
		return (new IzaBoolean(this));
	}

	public String toString()
	{
		return ("" + mValue);
	}
}
