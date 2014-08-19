package tk.oknctict.izanagi.shell;

import tk.oknctict.izanagi.parser.*;
import tk.oknctict.izanagi.variable.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class ShellVisitor implements ExprParserVisitor
{
	private final ShellVarsManager mVarsMng;
	private final ShellFuncs mFuncs;
	private final Stack<String> mFuncsNameStack;
	private boolean mBreak;
	private boolean mContinue;
	private boolean mReturn;

	private final IzaBasic True = new IzaBoolean(true);
	private final IzaBasic False = new IzaBoolean(false);

	public ShellVisitor()
	{
		mVarsMng = ShellVarsManager.getInstance();
		mFuncs = ShellFuncs.getInstance();
		mFuncsNameStack = new Stack<String>();
		mBreak = false;
		mContinue = false;
		mReturn = false;
	}

	public Object visit(SimpleNode node, Object data)
	{
		return (null);
	}

	public Object visit(ASTStart node, Object data)
	{
		node.jjtGetChild(0).jjtAccept(this, null);
		return ("end");
	}

	public Object visit(ASTStmts node, Object data)
	{
		int size = node.jjtGetNumChildren();
		IzaBasic returnValue = new IzaNone();

		for (int i = 0; i < size; i++){
			returnValue = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);
		}

		return (returnValue);
	}

	public Object visit(ASTStmt node, Object data)
	{
		int size = node.jjtGetNumChildren();
		IzaBasic returnValue = new IzaNone();

		for (int i = 0; i < size; i++){
			returnValue = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);
		}

		return (returnValue);
	}

	public Object visit(ASTBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		IzaBasic returnValue = new IzaNone();

		for (int i = 0; i < size; i++){
			returnValue = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		return (returnValue);
	}
	public Object visit(ASTWhileBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		IzaBasic returnValue = new IzaNone();

		for (int i = 0; i < size; i++){
			if (mContinue == true){
				mContinue = false;
				break;
			}
			if (mBreak == true){
				break;
			}
			returnValue = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		return (returnValue);
	}
	public Object visit(ASTFuncBlock node, Object data)
	{
		int size = node.jjtGetNumChildren();
		IzaBasic returnValue = new IzaNone();

		for (int i = 0; i < size; i++){
			returnValue = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);

			if (mReturn == true){
				break;
			}
		}

		String name = mFuncsNameStack.peek();
		int type = mFuncs.get(name).getType();

		mFuncsNameStack.pop();
		mVarsMng.outFunc();

		if (mReturn == true){
			mReturn = false;
			returnValue = returnValue.cast(type);
			return (returnValue);
		}
		else {
			return (null);
		}
	}

	public Object visit(ASTDefFunc node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellArgs args = (ShellArgs)node.jjtGetChild(1).jjtAccept(this, null);
		String type = node.nodeValue;
		ASTFuncBlock block = (ASTFuncBlock)node.jjtGetChild(2);

		ShellFunc func = new ShellFunc(name, args, type, block);
		mFuncs.set(name, func);

		return (null);
	}
	public Object visit(ASTCallFunc node, Object data)
	{
		int size = node.jjtGetNumChildren();
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		ShellFunc shellFunc = mFuncs.get(name);
		ArrayList<ShellArg> argList = shellFunc.getArgs().getArgList();
		ArrayList<IzaBasic> valueList = new ArrayList<IzaBasic>();

		if (shellFunc == null){
			System.out.println("定義されていない関数です");
			return (null);
		}

		for (int i = 1; i < size; i++){
			IzaBasic value = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, null);
			valueList.add(value);
		}

		if (shellFunc.checkArgs(valueList) == false){
			return (null);
		}
		
		mFuncsNameStack.push(name);
		mVarsMng.intoFunc();
		for (int i = 0; i < valueList.size(); i++){
			mVarsMng.set(argList.get(i).getName(), valueList.get(i));
		}

		return (visit(shellFunc.getBlock(), data));
	}

	public Object visit(ASTIfStmt node, Object data)
	{
		//First child points if condition, second points if body,
		//third points elseif condition, fourth points elseif body,
		//and the last child points else body

		int size;
		IzaBasic resultValue = new IzaNone();
		size = node.jjtGetNumChildren();
		for (int i = 0; i < size - 1; i += 2){
			IzaString value = (IzaString)node.jjtGetChild(i).jjtAccept(this, data);

			if (value.EQ(True).mValue == true){
				resultValue = (IzaBasic)node.jjtGetChild(i + 1).jjtAccept(this, null);
				return (resultValue);
			}
		}

		//Check which else condition is exisits
		if (size % 2 != 0){
			resultValue = (IzaBasic)node.jjtGetChild(size - 1).jjtAccept(this, null);
		}

		return (resultValue);
	}

	public Object visit(ASTWhileStmt node, Object data)
	{
		//First child points while condition, second points while body

		while (true){
			if (mBreak == true){
				mBreak = false;
				break;
			}

			IzaBasic value = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, data);
			if (value.EQ(False).mValue == true){
				break;
			}

			node.jjtGetChild(1).jjtAccept(this, null);
		}

		return (null);
	}

	public Object visit(ASTSelectStmt node, Object data)
	{
		//First child points select condition, second points case condition
		//third points seca body, and last case else body

		int size;
		IzaBasic resultValue = new IzaNone();
		size = node.jjtGetNumChildren();

		IzaBasic oriValue = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, data);
		for (int i = 1; i < size - 1; i += 2){
			IzaBasic value = (IzaBasic)node.jjtGetChild(i).jjtAccept(this, data);

			if (oriValue.EQ(value).mValue == true){
				resultValue = (IzaBasic)node.jjtGetChild(i + 1).jjtAccept(this, null);
				return (resultValue);
			}
		}

		if (size % 2 == 0){
			resultValue = (IzaBasic)node.jjtGetChild(size - 1).jjtAccept(this, null);
		}

		return (resultValue);
	}

	public Object visit(ASTDimStmt node, Object data)
	{
		int type = toType(node.nodeValue);
		int size;
		IzaBasic value = new IzaNone();
		size = node.jjtGetNumChildren();
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		
		if (size == 1){
			value = new IzaNone().cast(type);
		}
		else {
			ArrayList<Integer> arraySizes;
			arraySizes = (ArrayList<Integer>)node.jjtGetChild(1).jjtAccept(this, null);

			value = new IzaArray(arraySizes, type);
		}

		mVarsMng.set(name, value);

		return (value);
	}
	private int toType(String str)
	{
		if (str.equals("Integer")){
			return (IzaBasic.TYPE_INTEGER);
		}
		else if (str.equals("Float")){
			return (IzaBasic.TYPE_FLOAT);
		}
		else if (str.equals("String")){
			return (IzaBasic.TYPE_STRING);
		}
		else if (str.equals("Boolean")){
			return (IzaBasic.TYPE_BOOLEAN);
		}
		else {
			return (IzaBasic.TYPE_NONE);
		}
	}

	public Object visit(ASTBreakStmt node, Object data)
	{
		mBreak = true;

		return (null);
	}

	public Object visit(ASTContinueStmt node, Object data)
	{
		mContinue = true;

		return (null);
	}

	public Object visit(ASTPrintStmt node, Object data)
	{
		IzaBasic value = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);

		System.out.println(value.toString());

		return (value);
	}

	public Object visit(ASTReturnStmt node, Object data)
	{
		IzaBasic value = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);

		mReturn = true;

		return (value);
	}

	public Object visit(ASTAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		//mVarsMng.set(name, right);
		left.set(right);

		return (left);
	}
	public Object visit(ASTAddAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.add(right);
		left.set(result);

		return (left);
	}
	public Object visit(ASTSubAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.sub(right);
		left.set(result);

		return (left);
	}
	public Object visit(ASTMulAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.mul(right);
		left.set(result);

		return (left);
	}
	public Object visit(ASTDivAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.div(right);
		left.set(result);

		return (left);
	}
	public Object visit(ASTModAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.mod(right);
		left.set(result);

		return (left);
	}
	public Object visit(ASTPowAssign node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		int type = left.getType();
		right = right.cast(type);

		IzaBasic result = left.pow(right);
		left.set(result);

		return (left);
	}

	public Object visit(ASTBAnd node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.BAnd(right));
	}
	public Object visit(ASTBOr node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.BOr(right));
	}

	public Object visit(ASTEq node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.EQ(right));
	}

	public Object visit(ASTNEq node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.NEQ(right));
	}

	public Object visit(ASTGe node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);
		
		return (left.GE(right));
	}
	public Object visit(ASTGt node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.GT(right));
	}
	public Object visit(ASTLe node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.LE(right));
	}
	public Object visit(ASTLt node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.LT(right));
	}

	public Object visit(ASTAdd node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.add(right));
	}

	public Object visit(ASTSub node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.sub(right));
	}

	public Object visit(ASTMul node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.mul(right));
	}

	public Object visit(ASTDiv node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.div(right));
	}

	public Object visit(ASTMod node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.mod(right));
	}

	public Object visit(ASTPower node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.pow(right));
	}

	public Object visit(ASTAnd node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.and(right));
	}

	public Object visit(ASTOr node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.or(right));
	}

	public Object visit(ASTNot node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		IzaBasic right = (IzaBasic)node.jjtGetChild(1).jjtAccept(this, null);

		return (left.not(right));
	}

	public Object visit(ASTUniExpr node, Object data)
	{
		IzaBasic left = (IzaBasic)node.jjtGetChild(0).jjtAccept(this, null);
		String value = node.nodeValue;

		if (value.equals("-")){
			return (left.minus());
		}

		return (left);
	}

	public Object visit(ASTInteger node, Object data)
	{
		int value = Integer.valueOf(node.nodeValue);
		return (new IzaInteger(value));
	}

	public Object visit(ASTFloat node, Object data)
	{
		float value = Float.valueOf(node.nodeValue);
		return (new IzaFloat(value));
	}

	public Object visit(ASTString node, Object data)
	{
		String value = node.nodeValue;
		return (new IzaString(value));
	}

	public Object visit(ASTVar node, Object data)
	{
		String name = node.nodeValue;
		int size;
		size = node.jjtGetNumChildren();
		
		if (mVarsMng.usedName(name)){
			IzaBasic value;
			if (size == 0){
				value = mVarsMng.get(name).getValue();
			}
			else {
				ArrayList<Integer> indexList;
				IzaArray izaArray = (IzaArray)mVarsMng.get(name).getValue();
				indexList = (ArrayList<Integer>)node.jjtGetChild(0).jjtAccept(this, null);
				value = izaArray.getValue(indexList);
			}

			return (value);
		}
		else {
			IzaBasic value = new IzaNone();
			return (value);
		}
	}
	public Object visit(ASTRefVar node, Object data)
	{
		String name = node.nodeValue;
		int size;
		size = node.jjtGetNumChildren();
		
		if (mVarsMng.usedName(name)){
			IzaBasic value;
			if (size == 0){
				value = mVarsMng.get(name).getValue().clone();
			}
			else {
				ArrayList<Integer> indexList;
				IzaArray izaArray = (IzaArray)mVarsMng.get(name).getValue();
				indexList = (ArrayList<Integer>)node.jjtGetChild(0).jjtAccept(this, null);
				value = izaArray.getValue(indexList).clone();
			}

			return (value);
		}
		else {
			IzaBasic shellValue = new IzaNone();
			return (shellValue);
		}
	}

	public Object visit(ASTSubscript node, Object data)
	{
		int size;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		size = node.jjtGetNumChildren();

		for (int i = 0; i < size; i++){
			IzaInteger value = (IzaInteger)node.jjtGetChild(i).jjtAccept(this, null);
			int num = value.mValue;
			indexList.add(num);
		}

		return (indexList);
	}

	public Object visit(ASTArguments node, Object data)
	{
		ShellArgs args = new ShellArgs();
		int size = node.jjtGetNumChildren();

		for (int i = 0; i < size; i++){
			ShellArg arg = (ShellArg)node.jjtGetChild(i).jjtAccept(this, null);
			args.add(arg);
		}

		return (args);
	}
	public Object visit(ASTArgument node, Object data)
	{
		String name = (String)node.jjtGetChild(0).jjtAccept(this, null);
		String type = node.nodeValue;
		ShellArg arg = new ShellArg(name, type);

		return (arg);
	}

	public Object visit(ASTFuncName node, Object data)
	{
		String name = node.nodeValue;

		return (name);
	}

	public Object visit(ASTIdentifier node, Object data)
	{
		String name = node.nodeValue;

		return (name);
	}
}
