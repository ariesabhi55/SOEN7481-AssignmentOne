package com.concordia.soen7481;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.TryStmt;


public class InadequateLoggingInCatchPattern implements IBugPattern{

private static int counter = 0;
	
	@Override
	public void parse(CompilationUnit cu, String file) {
		try{
			List<TryStmt> tryBlockList = cu.findAll(TryStmt.class);
            for (TryStmt tryStmt : tryBlockList) {
            	List<CatchClause> catchList = tryStmt.getCatchClauses();
                int count = 0;
                for (CatchClause cc: catchList) {
                    processCatchClause(cc, catchList, count, cu, file);
                    count++;
                }
			}

        }catch (Exception e){
            System.out.println("Error applying rule " + this.getClass().getName() +  " to class: " + cu.getClass().getTypeName());
        }
	}

	private void processCatchClause(CatchClause cc, List<CatchClause> catchList, int index, CompilationUnit cu, String file) {
		for (int i = index + 1; i < catchList.size(); i++) {
			if(catchList.get(i).getBody().getStatements().equals(catchList.get(index).getBody().getStatements()) 
					&& !cc.getBody().getStatements().contains("e.printStackTrace()") 
					&& !cc.getBody().getStatements().contains("System.out.println(e)")
					) {				
				ResultFormat result = new ResultFormat();
                result.setClassName(cu.getClass().getName());
                result.setRuleName(this.getClass().getName());
                result.setDetectedSnippet(cc.toString());
                result.setStartLine(cc.getRange().get().begin.line);
                result.setEndLine(0);
                result.setFileName(file);
                StaticHelper.addResult(this.getClass().getName() ,result);
			}
		}
	}
}
