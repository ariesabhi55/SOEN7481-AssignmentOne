package com.concordia.soen7481;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticHelper {
	
	public static String SourceFolder;
    public static String Log_File;
    private static HashMap<String, List<ResultFormat>> results = new HashMap<>();

    public static void addResult(String ruleName, ResultFormat result){
        if(results.containsKey(ruleName)){
            results.get(ruleName).add(result);
        }else{
            List<ResultFormat> list = new ArrayList<>();
            list.add(result);
            results.put(ruleName, list);
        }
    }

    public static HashMap<String, List<ResultFormat>> getResults() {
        return results;
    }

}
