package stockManagementSystem;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * This code activates the beginning of the System. The User is asked to log in
 * to progress into an infinite loop of Menu's, They are able to quit only
 * through choosing the corresponding Menu option.
 * 
 */
public class Main {

	private static int attempts = 5; // This holds the amount of attempts remaining this instance of the System has
								// left before it locks out the User from the System.

	public static Staff loggedInStaffMember;

	public static void main(String[] args) throws IOException, ParseException {
		System.out.println("Welcome to the Reduced Section Management System (RSMS)!");
		userLogin();
		Product.readProductsFromFile();	
		ProductBatch.readBatchesFromFile();
		boolean isAdmin = loggedInStaffMember.getIsAdmin();
		Menu mainMenu = null;
		Menu staffManagementMenu = null;
		Menu productManagementMenu = null;
		Menu stockManagementMenu = null;
		while (true) { // This will create the infinite loop to keep the User inside of the Menu's.
			if (isAdmin) { // If an Admin account has logged onto the System.
				mainMenu = new Menu("Main Menu", Resources.adminMainMenuOptions);
				int option = mainMenu.getUserChoice();
				switch (option) {
				case 1: // If the User decides to go to the Staff Management Menu.
					staffManagementMenu = new Menu("Staff Management", Resources.staffManagerMenuOptions);
					option = staffManagementMenu.getUserChoice();
					switch (option) {
					case 1:
						// Add a Staff Member to the System.
						StaffManager.addStaff();
						break;
					case 2:
						// Remove a Staff Member from the System.
						StaffManager.removeStaff();
						break;
					case 3:
						// Update a Staff Member on the System.
						StaffManager.updateStaffAsManager();
						break;
					case 4:
						// Go back to Main Menu
						break;
					}
					break; // End of the Staff Management Menu.
				case 2:
					productManagementMenu = new Menu("Product Management", Resources.productManagerMenuOptions);
					option = productManagementMenu.getUserChoice();
					switch (option) {
					case 1:
						// Add Product to the System
						Product.addProduct();
						break;
					case 2:
						// Edit a Product
						Product.editProduct();
						break;
					case 3:
						// Change the Global Discount for all Reduced Products
						Product.changeGlobalDiscount();
						break;
					case 4:
						// Go back to Main Menu
						break;
					}
					break; // End of the Product Management Menu.
				case 3:
					stockManagementMenu = new Menu("Stock Management", Resources.stockManagerMenuOptions);
					option = stockManagementMenu.getUserChoice();
					switch (option) {
					case 1:
						// Add a new Batch to the System
						ProductBatch.newBatch();
						break;
					case 2:
						// Edit a Product Batch
						ProductBatch.editBatch();
						break;
					case 3:
						// Go back to Main Menu
						break;
					}
					break; // End of the Stock Management Menu.
				case 4: // View all Products and Stock in System.
					StockOverviewMenu.displayMenu();
					break;
				case 5:
					StaffFoodBank.foodBank();
					break;
				case 6: // Exit the System.
					System.exit(0);
				}
			} else {
				// If an Employee account has logged onto the System.
				mainMenu = new Menu("Main Menu", Resources.employeeMainMenuOptions);
				int option = mainMenu.getUserChoice();
				switch (option) {
				case 1: // Manage Stock
					stockManagementMenu = new Menu("Stock Management", Resources.stockEmployeeMenuOptions);
					option = stockManagementMenu.getUserChoice();
					switch (option) {
					case 1:
						// Add a new Batch to the System
						ProductBatch.newBatch();
						break;
					case 2:
						// Edit a Product Batch
						ProductBatch.editBatch();
						break;
					case 3:
						// View all Stock and Products in the System
						StockOverviewMenu.displayMenu();
						break;
					case 4:
						// Go back to Main Menu
						break;
					}
					break; // End of the Stock Management Menu.
				case 2: // Staff Food Bank
					StaffFoodBank.foodBank();
					break;
				case 3: // Update Forename, Surname and Password as that User.
					StaffManager.updateStaffAsEmployee();
					break;
				case 4: // Quit System
					System.exit(0);
					break;
				}
			}
		}
	}

	/**
	 * This Method is used to get the User to log into the system, using a valid
	 * Username and it's corresponding Password. If too many failed attempts occur,
	 * the User is locked out of the System.
	 * 
	 * @throws IOException
	 */
	private static void userLogin() throws IOException {
		Scanner keyb = new Scanner(System.in);
		boolean loggedIn = false;
		do {
			System.out.println("Please enter your Username: ");
			String username = keyb.nextLine();
			if (StaffDAL.findStaffByUsername(username).getLockedOut()) {
				System.out.println(
						"This User is locked out of the System. Please contact your administrator to unlock your account.");
				System.exit(0);
			}

			System.out.println("Please enter your Password: ");
			String password = keyb.nextLine();

			// Check if the username exists and retrieve the corresponding staff object
			Staff loggedInStaff = StaffDAL.findStaffByUsername(username);
			if (loggedInStaff.isEmpty()) {
				System.out.println("Please input a valid Username and Password.");
			} else {
				if (loggedInStaff.getPassword().equals(password)) {
					System.out.println("Login successful!");
					loggedIn = true;
					loggedInStaffMember = loggedInStaff;
				} else {
					attempts--;
					if (attempts <= 0) {
						System.out.println(
								"You are locked out of the system. Please contact an administrator to unlock your account.");
						loggedInStaff.setLockedOut(true);
						StaffDAL.updateStaffOnFile(username, loggedInStaff);
						System.exit(0);
					}
					System.out.println("Invalid username or password. Please try again. You have " + attempts
							+ " attempts remaining.");
				}
			}
		} while (!loggedIn);
	}

}
