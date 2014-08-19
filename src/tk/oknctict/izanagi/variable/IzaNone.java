package tk.oknctict.izanagi.variable;

public class IzaNone implements IzaBasic
{
	public int mType;

	public IzaNone()
	{
		mType = TYPE_NONE;
	}

	public void set(IzaBasic value)
	{
	}

	public IzaBasic add(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
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
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean NEQ(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
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
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBoolean BOr(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
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
			case TYPE_INTEGER:
				result = toIzaInteger();
				break;
			
			case TYPE_FLOAT:
				result = toIzaFloat();
				break;

			case TYPE_STRING:
				result = toIzaString();
				break;

			case TYPE_ARRAY:
				result = toIzaArray();
				break;

			default:
				System.out.println("許されないキャストです");
				return (null);
		}
		
		return (result);
	}

	public IzaBasic toIzaInteger()
	{
		IzaInteger value = new IzaInteger();
		return (value);
	}
	public IzaBasic toIzaFloat()
	{
		IzaFloat value = new IzaFloat();
		return (value);
	}
	public IzaBasic toIzaString()
	{
		IzaString value = new IzaString();
		return (value);
	}
	public IzaBasic toIzaBoolean()
	{
		IzaBoolean value = new IzaBoolean();
		return (value);
	}
	public IzaBasic toIzaArray()
	{
		IzaArray value = new IzaArray();
		return (value);
	}

	public int getType()
	{
		return (mType);
	}

	public IzaBasic clone()
	{
		return (new IzaNone());
	}

	public String toString()
	{
		return ("None");
	}
}
