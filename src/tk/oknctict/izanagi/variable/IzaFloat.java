package tk.oknctict.izanagi.variable;

public class IzaFloat implements IzaBasic
{
	public float mValue;
	public int mType;

	public IzaFloat()
	{
		this(0.0f);
	}
	public IzaFloat(float value)
	{
		mValue = value;
		mType = TYPE_FLOAT;
	}
	public IzaFloat(IzaFloat izaFloat)
	{
		mValue = izaFloat.mValue;
		mType = TYPE_FLOAT;
	}

	public void set(IzaBasic value)
	{
		mValue = ((IzaFloat)value).mValue;
	}

	public IzaBasic add(IzaBasic right)
	{
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaInteger result = new IzaInteger((int)mValue + value);
			mValue += value;
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaFloat result = new IzaFloat(mValue + value);
			mValue += value;
			return (result);
		}
		else if (right instanceof IzaString){
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
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaInteger result = new IzaInteger((int)mValue - value);
			mValue -= value;
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaFloat result = new IzaFloat(mValue - value);
			mValue -= value;
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}
	public IzaBasic mul(IzaBasic right)
	{
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaInteger result = new IzaInteger((int)mValue * value);
			mValue *= value;
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaFloat result = new IzaFloat(mValue * value);
			mValue *= value;
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}
	public IzaBasic div(IzaBasic right)
	{
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaInteger result = new IzaInteger((int)mValue / value);
			mValue /= value;
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaFloat result = new IzaFloat(mValue / value);
			mValue /= value;
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
	}
	public IzaBasic mod(IzaBasic right)
	{
		System.out.println("許されない演算です");
		return (null);
	}
	public IzaBasic pow(IzaBasic right)
	{
		if (right instanceof IzaInteger){
			int value = ((IzaInteger)right).mValue;
			IzaInteger result = new IzaInteger((int)Math.pow(mValue, value));
			mValue = (float)Math.pow(mValue, value);
			return (result);
		}
		else if (right instanceof IzaFloat){
			float value = ((IzaFloat)right).mValue;
			IzaFloat result = new IzaFloat((float)Math.pow(mValue, value));
			mValue = (float)Math.pow(mValue, value);
			return (result);
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}
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

		if (right instanceof IzaFloat){
			int value = (int)((IzaFloat)right).mValue;
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
		else {
			return (False);
		}
	}
	public IzaBoolean GT(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);
		float value;

		if (right instanceof IzaInteger){
			value = ((IzaInteger)right).mValue;
		}
		else if (right instanceof IzaFloat){
			value = ((IzaFloat)right).mValue;
		}
		else {
			System.out.println("許されない演算です");
			return (null);
		}

		if (mValue > value){
			return (True);
		}
		else {
			return (False);
		}
	}
	public IzaBoolean GE(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);
		IzaBoolean gt = GT(right);
		IzaBoolean eq = EQ(right);

		if (gt.mValue == true || eq.mValue == true){
			return (True);
		}

		return (False);
	}
	public IzaBoolean LT(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);
		IzaBoolean ge = GE(right);

		if (ge.mValue == false){
			return (True);
		}

		return (False);
	}
	public IzaBoolean LE(IzaBasic right)
	{
		IzaBoolean True = new IzaBoolean(true);
		IzaBoolean False = new IzaBoolean(false);
		IzaBoolean lt = LT(right);
		IzaBoolean eq = EQ(right);

		if (lt.mValue == true || eq.mValue == true){
			return (True);
		}

		return (False);
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
		mValue *= -1;
		IzaFloat result = new IzaFloat(mValue);

		return (result);
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
				result = this;
				break;

			case TYPE_STRING:
				result = toIzaString();
				break;

			default:
				System.out.println("許されないキャストです");
				return (null);
		}
		
		return (result);
	}

	public IzaBasic toIzaInteger()
	{
		IzaInteger value = new IzaInteger((int)mValue);
		return (value);
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
		return (new IzaFloat(this));
	}

	public String toString()
	{
		return ("" + mValue);
	}
}
