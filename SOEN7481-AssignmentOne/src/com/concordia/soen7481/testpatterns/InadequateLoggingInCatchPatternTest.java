package com.concordia.soen7481.testpatterns;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class InadequateLoggingInCatchPatternTest {

	public void conditionOne() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(""));
			
		} catch (FileNotFoundException e) {
			
			System.out.println("same");
			
		} catch (IOException e) {
			System.out.println("same");
			
		} finally {
			br.close();
		}
	}

	
	public void conditionTwo() {
		try {
			int i = 4;
			int [] ary = new int[1];
			ary[i/0] = 3;
		} catch (ArithmeticException e) {
			System.out.println("sample1");
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("sample2");
		}

	}

	
	public void conditionThree() {
		try {
			int i = 4;
			int [] ary = new int[1];
			ary[i/0] = 3;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace(); //same
			
		} catch (IllegalAccessError e) {
			e.printStackTrace();  //same
		} 
	}

	
	public void conditionFour() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(""));

			
		} catch (FileNotFoundException e) {
			System.out.println("sample");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}

	
	public void conditionFive() {
		try {
			int i = 4;
			int [] ary = new int[1];
			ary[i/0] = 3;
		} catch (ArithmeticException e) {
			System.out.println("sample3");
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("sample4");
		}
	}
	
}
