package com.concordia.soen7481;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;


public class FailToCloseStreamPattern implements IBugPattern{

	public HashSet<String> ioClasses = new HashSet<>();

    public FailToCloseStreamPattern(){
        ioClasses.add("BufferedInputStream");
        ioClasses.add("BufferedOutputStream");
        ioClasses.add("BufferedReader");
        ioClasses.add("BufferedWriter");
        ioClasses.add("DataInputStream");
        ioClasses.add("DataOutputStream");
        ioClasses.add("FileDescriptor");
        ioClasses.add("FileInputStream");
        ioClasses.add("FileOutputStream");
        ioClasses.add("FileReader");
        ioClasses.add("FileWriter");
        ioClasses.add("InputStream");
        ioClasses.add("InputStreamReader");
        ioClasses.add("OutputStream");
        ioClasses.add("OutputStreamWriter");
        ioClasses.add("PrintStream");
        ioClasses.add("PrintWriter");
        ioClasses.add("Reader");
        ioClasses.add("StringReader");
        ioClasses.add("StringBufferInputStream");
        ioClasses.add("StringWriter");
        ioClasses.add("Writer");
    }

    @Override
    public void parse(CompilationUnit cu, String file) {



        List<MethodDeclaration> methodList = cu.findAll(MethodDeclaration.class);

        for(MethodDeclaration method: methodList){
            HashMap<String, Boolean> variableMap = new HashMap<>();
            HashMap<String, ResultFormat> varResultMap = new HashMap<>();

            List<ExpressionStmt> expressions = method.findAll(ExpressionStmt.class);

            for(ExpressionStmt stmt : expressions){
                String variableName = null;
                String className = null;
                List<VariableDeclarator> vdList = stmt.findAll(VariableDeclarator.class);
                for(VariableDeclarator declarator : vdList){
                    variableName = declarator.getName().getIdentifier();
                    List<ClassOrInterfaceType> types = declarator.findAll(ClassOrInterfaceType.class);
                    for (ClassOrInterfaceType type : types){
                        className =  type.getName().getIdentifier();
                        break;
                    }
                    break;
                }
                if(ioClasses.contains(className)){
                	variableMap.put(variableName, Boolean.FALSE);
                    varResultMap.put(variableName, getResultObject(cu, file, stmt.toString(), stmt.getBegin().get().line));
                }
            }

            List<TryStmt> tryBlocks = method.findAll(TryStmt.class);
            for(TryStmt tryBlock : tryBlocks){
                Optional<BlockStmt> finallyBlock = tryBlock.getFinallyBlock();

                if(finallyBlock.isPresent()) {
                    for (String var : variableMap.keySet()) {
                        if (finallyBlock.get().toString().contains(var + ".close")) {
                        	variableMap.put(var, Boolean.TRUE);
                        }
                    }
                }
            }

            List<CatchClause> catches = method.findAll(CatchClause.class);
            for (CatchClause cc: catches) {
                String exceptionType = cc.getParameter().getType().toString();
                if(exceptionType.equals("Exception")){
                    String codeBlock = cc.getBody().toString().trim();
                    for(String var : variableMap.keySet()){
                        if(codeBlock.contains(var + ".close")){
                        	variableMap.put(var, Boolean.TRUE);
                        }
                    }
                }
            }

            for(String var: variableMap.keySet()){
                if(variableMap.get(var) == Boolean.FALSE)
                    StaticHelper.addResult(this.getClass().getName() ,varResultMap.get(var));
            }
        }

    }

    private ResultFormat getResultObject(CompilationUnit cu, String file, String snippet, int begin){
    	ResultFormat result = new ResultFormat();
            result.setClassName(cu.getClass().getName());
            result.setRuleName(this.getClass().getName());
            result.setDetectedSnippet(snippet);
            result.setStartLine(begin);
            result.setEndLine(0);
            result.setFileName(file);
            return result;
    }
	
}
