package com.concordia.soen7481;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) {		
		
    	String projectPath= "C:\\Users\\Abhishek Rajput\\eclipse-workspace\\cloudstack-4.9\\cloudstack-4.9";
    	
    	try{
            
            File sourcePath  = new File(projectPath);
            if(sourcePath.isDirectory())
                StaticHelper.SourceFolder = projectPath;

        }catch (Exception ex){
            System.out.println("Path is invalid");
            ex.printStackTrace();
        }    	
        
        CodeParser codeParser = new CodeParser();
        codeParser.parseProject();
        printResults();

    }	
	

    private static void printResults(){        
        System.out.println("*****************************      RESULTS       *********************************");
        System.out.println("**********************************************************************************");
        HashMap<String, List<ResultFormat>> results = StaticHelper.getResults();      
        for(String ruleType : results.keySet()){
            System.out.println("Issue Type: " + ruleType + "   Count: " + results.get(ruleType).size());
			
        }
        
    }
}


