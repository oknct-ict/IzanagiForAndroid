package tk.oknctict.izanagi.variable;

public class IzaString implements IzaBasic
{
	public String mValue;
	public int mType;

	public IzaString()
	{
		this("");
	}
	public IzaString(String value)
	{
		mValue = value;
		mType = TYPE_STRING;
	}
	public IzaString(IzaString izaString)
	{
		mValue = izaString.mValue;
		mType = TYPE_STRING;
	}

	public void set(IzaBasic value)
	{
		mValue = ((IzaString)value).mValue;
	}

	public IzaBasic add(IzaBasic right)
	{
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaString result = new IzaString(mValue + value);
			mValue += value;
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaString result = new IzaString(mValue + value);
			mValue += value;
			return (result);
		}
		else if (right instanceof IzaString){
			String value = ((IzaString)right).mValue;
			IzaString result = new IzaString(mValue + value);
			mValue += value;
			return (result);
		}
		else if (right instanceof IzaBoolean){
			boolean value = ((IzaBoolean)right).mValue;
			IzaString result = new IzaString(mValue + value);
			mValue += value;
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
		String value;

		if (right instanceof IzaInteger){
			value = "" + ((IzaInteger)right).mValue;
		}
		else if (right instanceof IzaFloat){
			value = "" + ((IzaFloat)right).mValue;
		}
		else if (right instanceof IzaString){
			value = ((IzaString)right).mValue;
		}
		else if (right instanceof IzaBoolean){
			value = "" + ((IzaBoolean)right).mValue;
		}
		else {
			return (False);
		}

		if (mValue.equals(value)){
			return (True);
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
		else {
			return (False);
		}
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
		int count = 0;
		char comma = ',';

		for (int i = 0; i < index.length(); i++){
			if (mValue.charAt(i) == comma){
				count++;
			}
		}

		if (count == 0){
			int n = Integer.valueOf(index);
			IzaString result = new IzaString(mValue.substring(n, n));
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
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
				result = this;
				break;

			default:
				System.out.println("許されないキャストです");
				return (null);
		}
		
		return (result);
	}

	public IzaBasic toIzaInteger()
	{
		IzaInteger value = new IzaInteger(Integer.valueOf(mValue));
		return (value);
	}
	public IzaBasic toIzaFloat()
	{
		IzaFloat value = new IzaFloat(Integer.valueOf(mValue));
		return (value);
	}

	public int getType()
	{
		return (mType);
	}

	public IzaBasic clone()
	{
		return (new IzaString(this));
	}

	public String toString()
	{
		return ("" + mValue);
	}
}
