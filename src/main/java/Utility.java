//**********************************************************
//  In class Activity - ch 2  
//  Program Name :  Utility.java
//
//  Author: Kaylee
//  Date Written : 1/24/23 
//  Class: CSC110AA 
//
//  Brief Description:
//  A  Utility class that mostly handles asking the user stuff
//**********************************************************
import java.util.Scanner;

public class Utility {
	static Scanner scan = new Scanner(System.in);

	public static String promptMeasure(String measure) {
		System.out.print(measure);
		return scan.nextLine();
	}

}
