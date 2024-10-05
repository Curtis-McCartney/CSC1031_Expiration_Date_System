package stockManagementSystemTesting;

import java.io.IOException;
import java.util.Scanner;

import stockManagementSystem.Staff;
import stockManagementSystem.StaffDAL;

/**
 * This Class is being used to Test the Log in Feature.
 *
 */
public class LoggingInTestCases {

	static Scanner keyb = new Scanner(System.in);
	static int attempts = 5;

	public static void main(String[] args) throws IOException {
		try {
			System.out.println("Logging in Test Cases");
			System.out.println("\nTest Case One: Correct Username, Correct Password");
			LoggingInTestCases.testCaseOne();

//			This test results in a loop of asking the User to re-input their Username and Password.
//			This leads to the System closing due to being locked out.
			System.out.println("\nTest Case Two: Correct Username, Incorrect Password");
			LoggingInTestCases.testCaseTwo();

//			This test results in a loop of asking the User to re-input their Username and Password.
//			This leads to the System closing due to being locked out.
			System.out.println("\nTest Case Three: Incorrect Username, A Correct Password");
			LoggingInTestCases.testCaseThree();

//			This test results in a loop of asking the User to re-input their Username and Password.
//			This leads to the System closing due to being locked out.
			System.out.println("\nTest Case Four");
			LoggingInTestCases.testCaseFour();

//			This test results in a loop of asking the User to re-input their Username and Password.
//			This leads to the System closing due to being locked out.
			System.out.println(
					"\nTest Case Five: Incorrect Username, Incorrect password, the User should be locked out of the system.");
			LoggingInTestCases.testCaseFive();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	// Correct Username, Correct Password
	public static void testCaseOne() throws IOException {
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = "DData";
			System.out.println("Please enter your Password: ");
			String password = "test";

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.getUsername().equals(username) && loggedInStaff.getPassword().equals(password)) {
				System.out.println("Login successful!");
				loggedIn = true;
				// You can store the loggedInStaff object for further use if needed
			} else {
				attempts--;
				if (attempts <= 0) {
					System.out.println(
							"You are locked out of the system. Please contact an administrator to unlock your account.");
					System.exit(0);
				}
				System.out.println("Invalid username or password. Please try again. You have " + attempts
						+ " attempts remaining.");
			}

		} while (!loggedIn);
	}

	// Correct Username, Incorrect Password
	public static void testCaseTwo() throws IOException {
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = "DData";
			System.out.println("Please enter your Password: ");
			String password = "IncorrectPassword";

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.getUsername().equals(username) && loggedInStaff.getPassword().equals(password)) {
				System.out.println("Login successful!");
				loggedIn = true;
				// You can store the loggedInStaff object for further use if needed
			} else {
				attempts--;
				if (attempts <= 0) {
					System.out.println(
							"You are locked out of the system. Please contact an administrator to unlock your account.");
					System.exit(0);
				}
				System.out.println("Invalid username or password. Please try again. You have " + attempts
						+ " attempts remaining.");
			}

		} while (!loggedIn);
	}

	// Incorrect Username, Correct Password
	public static void testCaseThree() throws IOException {
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = "IncorrectUsername";
			System.out.println("Please enter your Password: ");
			String password = "test";

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.getUsername().equals(username) && loggedInStaff.getPassword().equals(password)) {
				System.out.println("Login successful!");
				loggedIn = true;
				// You can store the loggedInStaff object for further use if needed
			} else {
				attempts--;
				if (attempts <= 0) {
					System.out.println(
							"You are locked out of the system. Please contact an administrator to unlock your account.");
					System.exit(0);
				}
				System.out.println("Invalid username or password. Please try again. You have " + attempts
						+ " attempts remaining.");
			}
		} while (!loggedIn);
	}

	// Incorrect Username, Incorrect Password
	public static void testCaseFour() throws IOException {
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = "IncorrectUsername";
			System.out.println("Please enter your Password: ");
			String password = "IncorrectPassword";

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.getUsername().equals(username) && loggedInStaff.getPassword().equals(password)) {
				System.out.println("Login successful!");
				loggedIn = true;
				// You can store the loggedInStaff object for further use if needed
			} else {
				attempts--;
				if (attempts <= 0) {
					System.out.println(
							"You are locked out of the system. Please contact an administrator to unlock your account.");
					System.exit(0);
				}
				System.out.println("Invalid username or password. Please try again. You have " + attempts
						+ " attempts remaining.");
			}
		} while (!loggedIn);
	}

	// Incorrect Username, Incorrect Password repeated 5 times.
	public static void testCaseFive() throws IOException {
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = "IncorrectUsername";
			System.out.println("Please enter your Password: ");
			String password = "IncorrectPassword";

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.getUsername().equals(username) && loggedInStaff.getPassword().equals(password)) {
				System.out.println("Login successful!");
				loggedIn = true;
				// You can store the loggedInStaff object for further use if needed
			} else {
				attempts--;
				if (attempts <= 0) {
					System.out.println(
							"You are locked out of the system. Please contact an administrator to unlock your account.");
					System.exit(0);
				}
				System.out.println("Invalid username or password. Please try again. You have " + attempts
						+ " attempts remaining.");
			}
		} while (!loggedIn);
	}

}