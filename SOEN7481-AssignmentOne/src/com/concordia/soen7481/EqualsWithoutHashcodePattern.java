package com.concordia.soen7481;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;


public class EqualsWithoutHashcodePattern implements IBugPattern{

	private static int count = 0;
	@Override
	public void parse(CompilationUnit cu, String file) {
		List<MethodDeclaration> methodList = cu.findAll(MethodDeclaration.class);
	    for(MethodDeclaration mdOuter : methodList){
	        String nameOuter = mdOuter.getName().getIdentifier();
	        if(nameOuter.equals("equals")) {
	    	        boolean exist = false;
		        	for(MethodDeclaration mdInner : methodList){
		    	        String nameInner = mdInner.getName().getIdentifier();
		    	        if(nameInner.equals("hashCode")) {
		    	        		exist = true;
		    	        		break;
		    	        }
		    	    }
		        	if(!exist) {
		        		ResultFormat result=new ResultFormat();
		        		 result.setClassName(cu.getClass().getName());
		                    result.setRuleName(this.getClass().getName());
		                    result.setDetectedSnippet(nameOuter.toString());
		                    result.setEndLine(0);
		                    result.setFileName(file);
		                    StaticHelper.addResult(this.getClass().getName(), result);
		        	}
	        }
	    }
	}
}
