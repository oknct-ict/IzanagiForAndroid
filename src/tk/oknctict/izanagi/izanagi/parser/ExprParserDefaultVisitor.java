/* Generated By:JavaCC: Do not edit this line. ExprParserDefaultVisitor.java Version 6.0_1 */
package tk.oknctict.izanagi.parser;

public class ExprParserDefaultVisitor implements ExprParserVisitor{
  public Object defaultVisit(SimpleNode node, Object data){
    node.childrenAccept(this, data);
    return data;
  }
  public Object visit(SimpleNode node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStart node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStmts node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBlock node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTWhileBlock node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTFuncBlock node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTArguments node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTArgument node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDefFunc node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTCallFunc node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTIfStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTWhileStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSelectStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDimStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBreakStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTContinueStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPrintStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTReturnStmt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAddAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSubAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMulAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDivAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTModAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPowAssign node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBAnd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTBOr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTEq node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNEq node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTGe node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTGt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLe node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTLt node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAdd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSub node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMul node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTDiv node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTMod node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTPower node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTAnd node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTOr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTNot node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTUniExpr node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTInteger node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTFloat node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTString node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTVar node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTRefVar node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTSubscript node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTFuncName node, Object data){
    return defaultVisit(node, data);
  }
  public Object visit(ASTIdentifier node, Object data){
    return defaultVisit(node, data);
  }
}
/* JavaCC - OriginalChecksum=caef354fa3c9b08823e1db5f5ecedd86 (do not edit this line) */
