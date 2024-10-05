package stockManagementSystemTesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import stockManagementSystem.Staff;
import stockManagementSystem.StaffDAL;

public class StaffDALTestCases {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		StaffDAL.switchToTestFile(true);
		try {
		staffDALTest1();
		staffDALTest2();
		staffDALTest3();
		staffDALTest4();
		staffDALTest5();
		staffDALTest6();
		staffDALTest7();
		staffDALTest8();
		staffDALTest9();
		staffDALTest10();
		staffDALTest11();
		staffDALTest12();
		staffDALTest13();
		staffDALTest14();
		staffDALTest15();
		staffDALTest16();
		staffDALTest17();
		staffDALTest18();
		staffDALTest19();
		staffDALTest20();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		StaffDAL.switchToTestFile(false);
		input.close();
	}

	public static void staffDALTest1() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 1 - addStaffToFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to be correctly added to the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		boolean added = StaffDAL.addStaffToFile(addStaff);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest2() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 2 - addStaffToFile() - Attempt to add empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff();
		boolean added = StaffDAL.addStaffToFile(addStaff);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest3() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 3 - addStaffToFile() - Attempt to add a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		boolean added = StaffDAL.addStaffToFile(null);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest4() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 4 - addStaffToFile() - Attempt to add staff with a username that is already taken");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: no record to be added");
		Staff addStaff1 = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff2 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff1);
		StaffDAL.addStaffToFile(addStaff2);
		Staff testStaff = new Staff("Test2", "Testing", "TTesting1", "password1234", false, false);
		boolean added = StaffDAL.addStaffToFile(testStaff);
		System.out.println("Added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest5() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 5 - readAllStaff()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: 2 Staff objects to be outputed");
		Staff addStaff1 = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff2 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff1);
		StaffDAL.addStaffToFile(addStaff2);
		ArrayList<Staff> result = StaffDAL.readAllStaff();
		for(Staff current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest6() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 6 - readAllStaff() - reading with no records on file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: no records existing on the file");
		System.out.println("Expected Result: No staff objects to be outputted");
		ArrayList<Staff> result = StaffDAL.readAllStaff();
		for(Staff current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest7() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 7 - removeStaffFromFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: the record to be removed from the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		boolean removed = StaffDAL.removeStaffFromFile(addStaff);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest8() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 8 - removeStaffFromFile() - Attempting to remove a record that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		Staff testStaff = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		boolean removed = StaffDAL.removeStaffFromFile(testStaff);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest9() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 9 - removeStaffFromFile() - Attempting to remove with an empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.removeStaffFromFile(new Staff());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest10() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 10 - removeStaffFromFile() - Attempting to remove with a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		boolean removed = StaffDAL.removeStaffFromFile(null);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest11() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 11 - updateStaffOnFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: the record to be updated with the correct info");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		Staff testStaff = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		boolean updated = StaffDAL.updateStaffOnFile("TTesting", testStaff);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest12() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 12 - updateStaffOnFile() - Updating with a username that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		Staff testStaff = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		boolean updated = StaffDAL.updateStaffOnFile("TUntesting", testStaff);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest13() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 13 - updateStaffOnFile() - Attempting to update correct username with empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		Staff testStaff = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		boolean updated = StaffDAL.updateStaffOnFile("TTesting", new Staff());
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest14() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 14 - updateStaffOnFile() - Attempting to update correct username with a null object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		StaffDAL.addStaffToFile(addStaff);
		Staff testStaff = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		boolean updated = StaffDAL.updateStaffOnFile("TTesting", null);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest15() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 15 - findStaffByUsername()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: The record for TTesting to be output");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		Staff foundStaff = StaffDAL.findStaffByUsername(addStaff.getUsername());
		System.out.println("Result: " + foundStaff.toString());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest16() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 16 - findStaffByUsername() - searching with an empty username string");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: An empty record to be output");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		Staff foundStaff = StaffDAL.findStaffByUsername("");
		System.out.println("Object Returned. Is Empty?: " + foundStaff.isEmpty());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest17() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 17 - findStaffByUsername() - searching with a null username");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: An empty record to be output");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		Staff foundStaff = StaffDAL.findStaffByUsername(null);
		System.out.println("Object Returned. Is Empty?: " + foundStaff.isEmpty());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest18() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 18 - fileContains()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record already on the file");
		System.out.println("Expected Result: True");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		boolean contains = StaffDAL.fileContains(addStaff);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest19() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 19 - fileContains() - checking with a record that isnt on the file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record not on the file");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		Staff addStaff2 = new Staff("Test2", "Testing", "TTesting2", "password1235", false, false);
		boolean contains = StaffDAL.fileContains(addStaff2);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void staffDALTest20() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffDAL Test 20 - fileContains() - inputting a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A null value");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		Staff addStaff = new Staff("Test", "Testing", "TTesting", "password", false, false);
		Staff addStaff1 = new Staff("Test1", "Testing", "TTesting1", "password123", false, false);
		StaffDAL.addStaffToFile(addStaff);
		StaffDAL.addStaffToFile(addStaff1);
		boolean contains = StaffDAL.fileContains(null);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void resetBetweenTests() throws IOException {
		// CLASS DATA //
		String fileName = "StaffTest.csv";
		File staffTestFile = new File(fileName);
		boolean hasHeader = true;
		FileWriter fw = new FileWriter(staffTestFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");
		
		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("Forename,Surname,Username,Password,LockedOut,IsAdmin");
		}
		pw.close();
	}

	public static void tapEnterToContinue() {
		System.out.print("Press Enter to continue: ");
		input.nextLine();
		System.out.println("Continuing....");
	}
}
