package com.concordia.soen7481;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;

public class ConditionhasNoEffectPattern implements IBugPattern{

	@Override
    public void parse(CompilationUnit cu, String file)
    {
        try {
            List<IfStmt> ifStmtList = cu.findAll(IfStmt.class);
            for (IfStmt ifst : ifStmtList)
            {
            	checkIfStmt(cu,ifst,file);

            }
        }catch (Exception e){
            System.out.println("Error applying rule " + this.getClass().getName() + "to class" + cu.getClass().getTypeName());
        }
    }

    private void checkIfStmt (CompilationUnit cu, IfStmt ifst, String file) {
        if(ifst.getCondition() instanceof BooleanLiteralExpr)
        {            
            ResultFormat result = new ResultFormat();
            result.setClassName(cu.getClass().getName());
            result.setRuleName(this.getClass().getName());
            result.setDetectedSnippet(ifst.toString());
            result.setStartLine(ifst.getRange().get().begin.line);
            result.setEndLine(ifst.getRange().get().end.line);
            result.setFileName(file);
            StaticHelper.addResult(this.getClass().getName() ,result);
        }
        else if(ifst.getCondition() instanceof NameExpr)
        {
            NameExpr exp = (NameExpr) ifst.getCondition();
            
            Node intializationNode = getStatement(ifst, exp.getNameAsString());
           
            if(isInitializedWithBoolean(intializationNode))
            {                
                boolean checkFollowingNodes = false;
                boolean isVariableUsed = false;
                for(Node n: intializationNode.getParentNode().get().getChildNodes()) {
                    if(n.equals(ifst))
                        break;

                    if(n == intializationNode) {
                        checkFollowingNodes = true;
                    }
                    else if(checkFollowingNodes) {
                        if(isExprUsed(n, exp.getNameAsString())) {
                            isVariableUsed = true;
                            break;
                        }
                    }
                }
                if(!isVariableUsed) {
                    ResultFormat result = new ResultFormat();
                    result.setClassName(cu.getClass().getName());
                    result.setRuleName(this.getClass().getName());
                    result.setDetectedSnippet(ifst.toString());
                    result.setStartLine(ifst.getRange().get().begin.line);
                    result.setEndLine(ifst.getRange().get().end.line);
                    result.setFileName(file);
                    StaticHelper.addResult(this.getClass().getName() ,result);
                }
            }
        }
    }
    
    private boolean isExprUsed(Node node, String variableName) {
        if(node.getChildNodes() == null || node.getChildNodes().size() == 0) {
            return false;
        }

        for(Node childNode : node.getChildNodes()) {
            if(childNode instanceof NameExpr) {
                if(((NameExpr) childNode).getNameAsString().equals(variableName)) {
                    return true;
                }
            }
            return isExprUsed(childNode, variableName);
        }

        return false;
    }
    
    private boolean isInitializedWithBoolean(Node n) {
        String s = n.toString();
        if(s.contains("true") || s.contains("false"))
            return true;
        else
            return false;
    }
    
    private Node getStatement(Node node, String variableName) {

        if(node instanceof MethodDeclaration)
            return null;

        for(Node n: node.getChildNodes()) {
            if(n instanceof ExpressionStmt &&
                    n.getChildNodes() != null &&
                    n.getChildNodes().size() > 0 &&
                    n.getChildNodes().get(0) instanceof VariableDeclarationExpr
            ) {
                VariableDeclarationExpr declaration = (VariableDeclarationExpr) n.getChildNodes().get(0);
                if(declaration.getVariable(0).getName().getIdentifier().equals(variableName))
                    return n;
            }
        }
        return getStatement(node.getParentNode().get(), variableName);
    }

    
    
}
