package com.JDBC.util;

import java.util.Scanner;

public class ScannerSingleton {
	private static Scanner scannerThing;
	
	private ScannerSingleton(){}
	
	public static Scanner getScanner() 
	{
		if (scannerThing == null) 
		{
			scannerThing = new Scanner(System.in);
			
		}
		return scannerThing;
		
	}
	
	public static void killScanner() throws Exception 
	{
		if(scannerThing == null) 
		{
			throw new Exception("Scanner has not been instantiated!");	
		}
		else 
		{
			scannerThing.close();
			scannerThing = null;
		}
	}
	
	
}
