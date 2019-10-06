package com.concordia.soen7481.testpatterns;


public class ConditionHasNoEffectPatternTest {

	
	public void conditionOne() {
		if (true) {
	         System.out.println("Will always get printed");
		}

	}

	
	public void conditionTwo() {
		boolean var = false;
	       if (var) {
	    	   	System.out.println("Will never get Printed");
	       }
	}

	
	public void conditionThree() {
		boolean var = true;
		if (var) {
	         System.out.println("Will always get printed");
		}
	}
	
	public void conditionFour() {
		int var = 3;
		if (var % 2 == 0) {
	         System.out.println("Will never get printed");
		}
	}
}
