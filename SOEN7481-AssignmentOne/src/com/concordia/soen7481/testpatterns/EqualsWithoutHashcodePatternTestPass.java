package com.concordia.soen7481.testpatterns;

public class EqualsWithoutHashcodePatternTestPass {

	@Override
	public boolean equals(Object obj) {
		System.out.println("Equal object has equal hash code");
		return true;
	}
	
	@Override
	public int hashCode() {		
		return super.hashCode();
	}
}
