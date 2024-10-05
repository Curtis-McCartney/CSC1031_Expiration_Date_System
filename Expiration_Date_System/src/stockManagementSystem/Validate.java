package stockManagementSystem;

import java.io.IOException;
import java.util.Date;

/**
 * This Class will be used to store the Validation
 *         methods for the system.
 */
public abstract class Validate {

	/**
	 * This method is used to validate any human names input into the system.
	 * 
	 * @param name - The name to be validated
	 * @return Returns true if name is valid. Returns false if name is invalid, also
	 *         prints a message explaining what is invalid about the Input.
	 */
	public static boolean validateName(String name) {
		if (name.length() <= 1) {
			System.out.println("A name must be longer than 1 character!");
			return false;
		}

		for (int i = 0; i < name.length(); i++) {
			if (Character.isDigit(name.charAt(i))) {
				System.out.println("A name should not include numbers!");
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is used to validate a String to be secure enough for a password.
	 * The String must be: 1. 8 more more characters 2. Have at least one number 3.
	 * Have at least one symbol 4. Have at least one capital letter 5. Have at least
	 * one lowercase letter
	 * 
	 * @param password
	 * @return
	 */
	public static boolean validatePassword(String password) {
		if (password.length() < 8) {
			System.out.println("A Password must be longer than 8 characters!");
			return false;
		}

		boolean hasDigit = false;
		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasSymbol = false;

		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				hasDigit = true;
			} else if (Character.isUpperCase(password.charAt(i))) {
				hasUpperCase = true;
			} else if (Character.isLowerCase(password.charAt(i))) {
				hasLowerCase = true;
			} else if (!Character.isLetterOrDigit(password.charAt(i))) {
				hasSymbol = true;
			}
		}

		if (hasDigit && hasUpperCase && hasLowerCase && hasSymbol) {
			return true;
		}

		if (!hasDigit) {
			System.out.println("The System must have at least one number!");
		}

		if (!hasUpperCase) {
			System.out.println("The System must have at least one Uppercase Character!");
		}

		if (!hasLowerCase) {
			System.out.println("The System must have at least one Lowercase Character!");
		}

		if (!hasSymbol) {
			System.out.println("The System must have at least one Symbol!");
		}
		return false;
	}

	/**
	 * This Method is used to Validate the input given by the User for what Product
	 * they wish to take from the StaffFoodBank Menu. They also have the ability to
	 * type in "Quit" and leave this StaffFoodBank Menu.
	 * 
	 * @param choice - The User Input.
	 * @return
	 * @throws IOException
	 */
	public static boolean validateFoodBankChoice(String choice) throws IOException {
		if (choice.toLowerCase() == "quit") {
			return true;
		}
		try {
			int intChoice = Integer.parseInt(choice);
			if (intChoice < 1 || intChoice > BatchDAL.findBatchesByExpiryDate(new Date()).size()) {
				return false;
			}

		} catch (Exception ex) {
			System.out.println("Please input a valid Product ID or type the word \"Quit\".");
		}
		return true;
	}

}
