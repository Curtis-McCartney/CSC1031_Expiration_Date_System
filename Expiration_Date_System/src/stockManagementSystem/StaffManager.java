package stockManagementSystem;

import java.io.IOException;
import java.util.Scanner;

/**
 * This Class is used to manage the Staff class on the
 *         system. This includes methods to add, remove and edit Staff on the
 *         System.
 * 
 */
public abstract class StaffManager {

	private static Scanner keyb = new Scanner(System.in);

	/**
	 * The addStaff() method is used to add a Staff member to the system, using the
	 * inputs from the User to define the name, password and security type of the
	 * new Staff member.
	 * 
	 * @throws IOException
	 */
	public static void addStaff() throws IOException {
		boolean valid = false;

		String forename = getValidName("Please Enter The Forename Of The Staff Member: ");
		String surname = getValidName("Please Enter The Surname Of The Staff Member: ");

		String password = "";
		System.out
				.println("A Password must have 8 or more characters." + "\nIt must have at least one Uppercase Letter."
						+ "\nIt must have at least one Lowercase Letter." + "\nIt must have at least one Symbol.");
		while (!valid) { // Validation of Password
			System.out.println("Please Enter The Password For This Staff Member: ");
			password = keyb.nextLine().trim();
			if (Validate.validatePassword(password)) {
				valid = true;
			}
		}
		valid = false;

		Menu yesOrNo = new Menu("Should this be an Administrator Account?", Resources.yesOrNoOptions);
		int option = yesOrNo.getUserChoice();
		Staff newStaff;
		switch (option) {
		case 1:
			newStaff = new Staff(forename, surname, password, true);
			StaffDAL.addStaffToFile(newStaff);
			break;
		case 2:
			newStaff = new Staff(forename, surname, password, false);
			StaffDAL.addStaffToFile(newStaff);
			break;
		}
	}

	/**
	 * This method is used in the addStaff() method in order to return a valid
	 * Forename / Surname.
	 * 
	 * @param instructions - This holds a String of instructions for the User of
	 *                     what they need to enter.
	 * @return - It returns the User's final, valid input.
	 */
	public static String getValidName(String instructions) {
		boolean valid = false;
		String input = "";
		while (!valid) {
			System.out.println(instructions);
			input = keyb.nextLine().trim();
			if (Validate.validateName(input)) {
				valid = true;
			}
		}
		return input;
	}

	/**
	 * This allows a Manager to remove a Staff Member from the System.
	 * 
	 * @throws IOException
	 */
	public static void removeStaff() throws IOException {
		System.out.println("Please input the username of the Staff Member you wish to delete from the System.");
		String username = keyb.nextLine();
		Staff staffToDelete = StaffDAL.findStaffByUsername(username);
		if (staffToDelete == null) {
			System.out.println("There is no User on the system with that Username.");
			return;
		}

		if (staffToDelete.equals(Main.loggedInStaffMember)) {
			System.out.println("You cannot delete yourself from the system!");
			return;
		}

		Menu confirmationMenu = new Menu("Are you sure you wish to delete " + username + " from the system?",
				Resources.yesOrNoOptions);
		int option = confirmationMenu.getUserChoice();
		switch (option) {
		case 1:
			StaffDAL.removeStaffFromFile(staffToDelete);
			System.out.println(username + " has been deleted from the system.");
			break;
		case 2:
			System.out.println(username + " has not been deleted from the system.");
			break;
		}
	}

	/**
	 * This allows a Manager to update any Staff Member on the system for any of
	 * their values (except for Username).
	 * 
	 * @throws IOException
	 */
	public static void updateStaffAsManager() throws IOException {
		for (int i = 0; i < StaffDAL.readAllStaff().size(); i++) {
			System.out.println(StaffDAL.readAllStaff().get(i).getUsername());
		}
		System.out.println("Please input the Username of the Staff Member you wish to update: ");
		Scanner keyb = new Scanner(System.in);
		String usernameToUpdate = keyb.nextLine();
		Staff staffToUpdate = StaffDAL.findStaffByUsername(usernameToUpdate);

		// An infinite loop until the User inputs a valid Username or the word "Quit".
		while (staffToUpdate.isEmpty()) {
			// Lets the User exit the updating Menu if they type "Quit".
			if (usernameToUpdate.toLowerCase().trim().equals("quit")) {
				return;
			}
			System.out.println("Please input a Valid Username or type \"Quit\" to quit.");
			usernameToUpdate = keyb.nextLine();
			staffToUpdate = StaffDAL.findStaffByUsername(usernameToUpdate);
		}

		Menu updateStaffMenu = new Menu("What do you wish to update about " + usernameToUpdate,
				Resources.staffManagerUpdateOptions);
		int option = updateStaffMenu.getUserChoice();
		switch (option) {
		case 1: // Update Forename of the User.
			System.out.println("What do you wish to change this Staff Member's Forename to?: ");
			String updatedForename = keyb.nextLine();
			while (!Validate.validateName(updatedForename)) {
				updatedForename = keyb.nextLine();
			}
			staffToUpdate.setForename(updatedForename);
			StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
			break;
		case 2: // Update Surname of the User.
			System.out.println("What do you wish to change this Staff Member's Surname to?: ");
			String updatedSurname = keyb.nextLine();
			while (!Validate.validateName(updatedSurname)) {
				updatedSurname = keyb.nextLine();
			}
			staffToUpdate.setSurname(updatedSurname);
			StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
			break;
		case 3: // Update Password of the User.
			System.out.println("What do you wish to change this Staff Member's Password to?: ");
			String updatedPassword = keyb.nextLine();
			while (!Validate.validatePassword(updatedPassword)) {
				updatedPassword = keyb.nextLine();
			}
			staffToUpdate.setSurname(updatedPassword);
			StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
			break;
		case 4: // Update the Administration Role of this User.
			if (staffToUpdate.equals(Main.loggedInStaffMember)) {
				System.out.println("Sorry, you cannot update this for yourself.");
			} else {
				Menu giveAdminMenu = new Menu("Should this User be given Administrator Privileges?",
						Resources.yesOrNoOptions);
				int adminChoice = giveAdminMenu.getUserChoice();
				switch (adminChoice) {
				case 1:
					staffToUpdate.setIsAdmin(true);
					StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
					break;
				case 2:
					staffToUpdate.setIsAdmin(false);
					StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
					break;
				}
			}
			break;
		case 5: // Update the Locked Out Status of this Staff Member.
			if (staffToUpdate.equals(Main.loggedInStaffMember)) {
				System.out.println("Sorry, you cannot update this for yourself.");
			} else {
				Menu updateLockedOutMenu = new Menu("Should this User be Locked Out?", Resources.yesOrNoOptions);
				int lockedOutChoice = updateLockedOutMenu.getUserChoice();
				switch (lockedOutChoice) {
				case 1:
					staffToUpdate.setLockedOut(true);
					StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
					break;
				case 2:
					staffToUpdate.setLockedOut(false);
					StaffDAL.updateStaffOnFile(usernameToUpdate, staffToUpdate);
					break;
				}
			}
			break;
		case 6: // Quit
			return;
		}
	}

	/**
	 * This lets an Employee change their own forename, surname and password for
	 * their own account.
	 * @throws IOException 
	 */
	public static void updateStaffAsEmployee() throws IOException {
		Staff updatedStaff = Main.loggedInStaffMember;
		Menu updateStaffMenu = new Menu("What do you wish to update?", Resources.staffEmployeeUpdateOptions);
		int option = updateStaffMenu.getUserChoice();
		switch (option) {
		case 1: // Update Forename of the User.
			System.out.println("What do you wish to change your forename to?: ");
			String updatedForename = keyb.nextLine();
			while (!Validate.validateName(updatedForename)) {
				updatedForename = keyb.nextLine();
			}
			updatedStaff.setForename(updatedForename);
			StaffDAL.updateStaffOnFile(updatedStaff.getUsername(), updatedStaff);
			break;
		case 2: // Update Surname of the User.
			System.out.println("What do you wish to change your surname to?: ");
			String updatedSurname = keyb.nextLine();
			while (!Validate.validateName(updatedSurname)) {
				updatedSurname = keyb.nextLine();
			}
			updatedStaff.setSurname(updatedSurname);
			StaffDAL.updateStaffOnFile(updatedStaff.getUsername(), updatedStaff);
			break;
		case 3: // Update Password of the User.
			System.out.println("What do you wish to change your Password to?: ");
			String updatedPassword = keyb.nextLine();
			while (!Validate.validatePassword(updatedPassword)) {
				updatedPassword = keyb.nextLine();
			}
			updatedStaff.setPassword(updatedPassword);
			StaffDAL.updateStaffOnFile(updatedStaff.getUsername(), updatedStaff);
			break;
		case 4: // Quit
			return;
		}
	}

}
