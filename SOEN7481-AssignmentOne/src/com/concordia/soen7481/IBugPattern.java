package com.concordia.soen7481;
import com.github.javaparser.ast.CompilationUnit;

public interface IBugPattern {

	public void parse(CompilationUnit cu, String file);
}
