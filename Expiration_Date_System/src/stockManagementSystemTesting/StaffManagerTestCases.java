package stockManagementSystemTesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import stockManagementSystem.Main;
import stockManagementSystem.Staff;
import stockManagementSystem.StaffDAL;
import stockManagementSystem.StaffManager;

/**
 * This class will be used to test the addStaff, removeStaff,
 * updateStaffAsManager and updateStaffAsEmployee methods.
 * 
 *
 */
public class StaffManagerTestCases {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		StaffDAL.switchToTestFile(true);
		try {
			Main.loggedInStaffMember = new Staff("testForename", "testSurname", "tSurname1", "testPassword@1", false,
					true);
			testCaseOne();
			testCaseTwo();
			testCaseThree();
			testCaseFour();
			testCaseFive();
			testCaseSix();
			testCaseSeven();
			testCaseEight();
			testCaseNine();
			testCaseTen();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		StaffDAL.switchToTestFile(false);
		input.close();
	}

	public static void testCaseOne() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 1 - addStaff() with valid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to be correctly added to the file");
		System.out.println("Executing...");
		StaffManager.addStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseTwo() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 2 - addStaff() with invalid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println(
				"Expected Result: the record to be correctly added to the file. But only once the inputs have been validated.");
		System.out.println("Executing...");
		StaffManager.addStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseThree() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 3 - removeStaff() with valid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: the record to be correctly removed from the file");
		System.out.println("Executing...");
		StaffManager.removeStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseFour() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 4 - removeStaff() with no records on the file.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println(
				"Expected Result: Any input from User will result in \"There is no User on the system with that Username.\"");
		System.out.println("Executing...");
		StaffManager.removeStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseFive() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 5 - removeStaff() with invalid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println(
				"Expected Result: It will end the method and will output \"There is no User on the system with that Username.\"");
		System.out.println("Executing...");
		StaffManager.removeStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseSix() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 6 - removeStaff() when trying to delete yourself from the system.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: The User is logged into the system.");
		System.out.println(
				"Expected Result: It will end the method and will output \"You cannot delete yourself from the system!\"");
		System.out.println("Executing...");
		StaffManager.removeStaff();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseSeven() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 7 - updateStaffAsManager() with valid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: It will successfully update the record on the File.");
		System.out.println("Executing...");
		StaffManager.updateStaffAsManager();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseEight() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 8 - updateStaffAsManager() with invalid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println(
				"Expected Result: The User will be prompted to input valid data. Leading to the record being successfully updated on the file.");
		System.out.println("Executing...");
		StaffManager.updateStaffAsManager();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseNine() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 9 - updateStaffAsEmployee() with valid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: It will successfully update the record on the File.");
		System.out.println("Executing...");
		StaffManager.updateStaffAsEmployee();
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void testCaseTen() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("StaffManager Test 10 - updateStaffAsEmployee() with invalid input.");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println(
				"Expected Result: The User will be prompted to input valid data. Leading to the record being successfully updated on the file.");
		System.out.println("Executing...");
		StaffManager.updateStaffAsEmployee();
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
