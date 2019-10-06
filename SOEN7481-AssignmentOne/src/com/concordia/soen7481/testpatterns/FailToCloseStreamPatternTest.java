package com.concordia.soen7481.testpatterns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FailToCloseStreamPatternTest {
	 
	
	String fileName = "C:\\Users\\Abhishek Rajput\\eclipse-workspace\\SOEN7481-Assignment1\\src\\com\\concordia\\soen7481\\testpatterns\\checkFile.txt";
	
	public void conditionOne() throws IOException {
		BufferedWriter writer1=null;
		try{
			File file = new File(fileName);
			writer1 = new BufferedWriter( new FileWriter(file));
			
		}catch(Exception e) {
			writer1.close();
		}
		System.out.println("This test should pass because Stream is closed in catch block");
	}

	
	public void conditionTwo() throws IOException {
		BufferedWriter writer2 = null;
		try{
			File file = new File(fileName);
			writer2 = new BufferedWriter( new FileWriter(file));
			writer2.close();
		}catch(Exception e) {
			
		}
		System.out.println("This test should fail because Stream not closed in finallly or catch block");
		
	}

	
	public void conditionThree() throws IOException {
		BufferedWriter writer3 = null;
		try{
			File file = new File(fileName);
			writer3 = new BufferedWriter( new FileWriter(file));

		}catch(Exception e) {

		}finally{
			writer3.close();
		}
		System.out.println("This test should pass because Stream is closed in finallly block");
	}

	
	public void conditionFour() {
		try{
			File file = new File(fileName);
			BufferedWriter writer4 = new BufferedWriter( new FileWriter(file));
			
		}catch(IOException e) {
		}
		System.out.println("This test should fail because Stream not closed in finally or catch block");

	}
	
	
}
