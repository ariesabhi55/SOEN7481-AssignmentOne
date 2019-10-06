package com.concordia.soen7481.testpatterns;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.concordia.soen7481.*;

class TestFile {

	String pathName = new File("").getAbsolutePath()+"\\src\\com\\concordia\\soen7481\\testpatterns";
	
	@BeforeEach public void initialize() {
		StaticHelper.SourceFolder = pathName;
		CodeParser codeParser = new CodeParser();
        codeParser.parseProject();
		}	
	
	@Test
	void testEqualsWithoutHashcode() {
		assertEquals(1, StaticHelper.getResults().get("com.concordia.soen7481.EqualsWithoutHashcodePattern").size());		
		assertEquals(pathName+"\\EqualsWithoutHashcodePatternTestFail.java", StaticHelper.getResults().get("com.concordia.soen7481.EqualsWithoutHashcodePattern").get(0).getFileName());
	}
	
	@Test
	void testConditionHasNoEffect() {
		assertEquals(3, StaticHelper.getResults().get("com.concordia.soen7481.ConditionhasNoEffectPattern").size());
	}
	
	@Test
	void testInadequateLoggingInCatch() {		
		assertEquals(2, StaticHelper.getResults().get("com.concordia.soen7481.InadequateLoggingInCatchPattern").size());
	}
	

}
