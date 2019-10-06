package com.concordia.soen7481.testpatterns;

public class EqualsWithoutHashcodePatternTestFail {
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("Equal object does not have equal hash code");
		return false;
	}
}
