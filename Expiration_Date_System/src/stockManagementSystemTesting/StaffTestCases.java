package stockManagementSystemTesting;

import java.io.IOException;

import stockManagementSystem.Staff;

public class StaffTestCases {

	public static void main(String[] args) throws IOException {
		try {
			System.out.println("\nStaff Test Cases");
			System.out.println("\nTest Case One: Constructing a Staff Object");
			StaffTestCases.testCaseOne();

			System.out.println("\nTest Case Two: Testing the Getter Methods");
			StaffTestCases.testCaseTwo();

			System.out.println("\nTest Case Three: Testing the Setter Methods");
			StaffTestCases.testCaseThree();

			System.out.println("\nTest Case Four: Testing the toString() method for Staff Objects");
			StaffTestCases.testCaseFour();

			System.out.println("\nTest Case Five: Testing the isEmpty() method for using an empty Staff Object");
			StaffTestCases.testCaseFive();

			System.out.println("\nTest Case Six: Testing the isEmpty() method for using a non-empty Staff Object");
			StaffTestCases.testCaseSix();

			System.out.println("\nTest Case Seven: Testing the equals() method with two equal Staff Objects");
			StaffTestCases.testCaseSeven();

			System.out.println("\nTest Case Eight: Testing the equals() method with two different Staff Objects");
			StaffTestCases.testCaseEight();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	// Test Constructor
	public static void testCaseOne() throws IOException {
		Staff staff = new Staff("FirstName", "LastName", "Test_Password1", true);
	}

	// Test Getter Methods
	public static void testCaseTwo() throws IOException {
		Staff staff = new Staff("FirstName", "LastName", "Test_Password1", true);
		System.out.println("Forename: " + staff.getForename());
		System.out.println("Surname: " + staff.getSurname());
		System.out.println("Username: " + staff.getUsername());
		System.out.println("Password: " + staff.getPassword());
		System.out.println("IsAdmin: " + staff.getIsAdmin());
		System.out.println("LockedOut: " + staff.getLockedOut());
	}

	// Test Setter Methods
	public static void testCaseThree() throws IOException {
		Staff staff = new Staff("FirstName", "LastName", "Test_Password1", true);
		staff.setForename("Updated_FirstName");
		staff.setSurname("Updated_SecondName");
		staff.setPassword("Updated_Password");
		staff.setIsAdmin(false);
		System.out.println("Forename: " + staff.getForename());
		System.out.println("Surname: " + staff.getSurname());
		System.out.println("Username: " + staff.getUsername());
		System.out.println("Password: " + staff.getPassword());
		System.out.println("IsAdmin: " + staff.getIsAdmin());
		System.out.println("LockedOut: " + staff.getLockedOut());
	}

	// Test Setter Methods
	public static void testCaseFour() throws IOException {
		Staff staff = new Staff("FirstName", "LastName", "Test_Password1", true);
		System.out.println(staff);
	}

	// Test isEmpty Method with an empty Staff Object
	public static void testCaseFive() throws IOException {
		Staff staff = new Staff();
		if (staff.isEmpty()) {
			System.out.println("It is empty");
		} else {
			System.out.println("It is not empty");
		}
	}

	// Test isEmpty Method with an non-empty Staff Object
	public static void testCaseSix() throws IOException {
		Staff staff = new Staff("FirstName", "LastName", "Test_Password1", true);
		if (staff.isEmpty()) {
			System.out.println("It is empty");
		} else {
			System.out.println("It is not empty");
		}
	}

	// Testing the equals() Method with two equal Staff Objects
	public static void testCaseSeven() throws IOException {
		Staff staff1 = new Staff("FirstName", "LastName", "Test_Password1", true);
		Staff staff2 = new Staff("FirstName", "LastName", "Test_Password1", true);
		if (staff1.equals(staff2)) {
			System.out.println("They are the same.");
		} else {
			System.out.println("They are not the same.");
		}
	}

	// Testing the equals() Method with two different Staff Objects
	public static void testCaseEight() throws IOException {
		Staff staff1 = new Staff("FirstName", "LastName", "Test_Password1", true);
		Staff staff2 = new Staff("Different", "LastName", "Test_Password1", true);
		if (staff1.equals(staff2)) {
			System.out.println("They are the same.");
		} else {
			System.out.println("They are not the same.");
		}
	}

}
