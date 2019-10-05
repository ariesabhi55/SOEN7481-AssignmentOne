package com.concordia.soen7481;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.metamodel.CompilationUnitMetaModel;

public class CodeParser {

	private List<String> javaFiles = new ArrayList<String>();
    private List<IBugPattern> bugPatterns = new ArrayList<IBugPattern>();

    public void parseProject(){

    	//bugPatterns.add(new EqualsWithoutHashcodePattern());
    	//bugPatterns.add(new FailToCloseStreamPattern());
    	//bugPatterns.add(new ConditionhasNoEffectPattern());
    	//bugPatterns.add(new InadequateLoggingInCatchPattern());            
       
        
        loadJavaFiles();
        System.out.println("Total files to parse: " + javaFiles.size());
        int i= 0;
        for(String file: javaFiles){
            applyRuleEvaluators(file);
            //printAST(file);
            System.out.println("Evaluating file: " + i++ + "  Name: " + file);
        }
        
        
            }

    private void applyRuleEvaluators(String fileName){
        try{
            Path pathToJavaFile = Paths.get(fileName);
            CompilationUnit compilationUnit = JavaParser.parse(pathToJavaFile.toFile());

            for (IBugPattern pattern: this.bugPatterns) {
            	pattern.parse(compilationUnit, fileName);
            }

        }catch (Exception e){
            System.out.println("Failed to parse file: " + fileName);
            e.printStackTrace();
        }
    }
   
    private void loadJavaFiles(){

        File sourceDir = new File(StaticHelper.SourceFolder);
        Path start = Paths.get(sourceDir.getPath());
        int maxDepth = Integer.MAX_VALUE;
        try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
                String.valueOf(path).endsWith(".java"))) {
            stream.forEach(file -> javaFiles.add(file.toString()));
        } catch (IOException e) {
            System.out.println("Can't read directory " + StaticHelper.SourceFolder + " " + e.getMessage());
        }
    }

}
