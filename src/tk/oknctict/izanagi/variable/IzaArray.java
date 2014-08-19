package tk.oknctict.izanagi.variable;

import java.util.List;
import java.util.ArrayList;

public class IzaArray implements IzaBasic
{
	public ArrayList<IzaBasic> mValue;
	public int mType;
	public int mDataType;
	public ArrayList<Integer> mArraySizes;

	public IzaArray()
	{
		mType = TYPE_ARRAY;
		mDataType = TYPE_NONE;
	}
	public IzaArray(IzaArray izaArray, int dataType)
	{
		mValue = new ArrayList<IzaBasic>(izaArray.mValue);
		mType = TYPE_ARRAY;
		mDataType = dataType;
		mArraySizes = new ArrayList<Integer>(izaArray.mArraySizes);
	}
	public IzaArray(ArrayList<Integer> arraySizes, int dataType)
	{
		mType = TYPE_ARRAY;
		mDataType = dataType;
		mArraySizes = arraySizes;
		int totalSize = 1;

		for (int i = 0; i < mArraySizes.size(); i++){
			totalSize *= mArraySizes.get(i);
		}

		mValue = new ArrayList<IzaBasic>(totalSize);
		for (int i = 0; i < totalSize; i++){
			mValue.add(i, new IzaNone().cast(mDataType));
		}
	}
	public void setArray(String index)
	{
		List<Integer> arraySizes = toArraySizes(index);
		int totalSize = 1;

		for (int i = 0; i < mArraySizes.size(); i++){
			totalSize *= mArraySizes.get(i);
		}

		mValue = new ArrayList<IzaBasic>(totalSize);
	}
	public void set(IzaBasic value)
	{
	}

	private ArrayList<Integer> toArraySizes(String index)
	{
		String[] sizes = index.split(",");
		ArrayList<Integer> arraySizes = new ArrayList<Integer>(sizes.length);

		for (int i = 0; i < sizes.length; i++){
			arraySizes.add(i, Integer.valueOf(sizes[i]));
		}

		return (arraySizes);
	}
	private int calcIndex(ArrayList<Integer> indexList)
	{
		int index = 0;

		if (mArraySizes.size() != indexList.size()){
			System.out.println("添字の数がおかしいです");
			return (-1);
		}

		for (int i = 0; i < indexList.size(); i++){
			index *= mArraySizes.get(i);
			index += indexList.get(i);
		}

		return (index);
	}

	public IzaBasic getValue(ArrayList<Integer> indexList)
	{
		int index = calcIndex(indexList);

		return (mValue.get(index));
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
		int num = calcIndex(toArraySizes(index));

		return (mValue.get(num));
	}

	public IzaBasic cast(int type)
	{
		System.out.println("キャスト出来ません");
		return (null);
	}
	public int getType()
	{
		return (mType);
	}
	public IzaBasic clone()
	{
		return (new IzaArray(this, mDataType));
	}

	public String toString()
	{
		return ("" + mValue);
	}
}
