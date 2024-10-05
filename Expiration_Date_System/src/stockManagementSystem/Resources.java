package stockManagementSystem;

/**
 * 
 * This class holds separate String arrays for each Menu used in the system.
 * Each Array holds a list of options that the User can choose from using the
 * getUserChoice() Method in Menu.
 */
public abstract class Resources {
	static String[] employeeMainMenuOptions = { "Manage Stock", "Staff Food Bank", "Update Account", "Quit" };
	static String[] adminMainMenuOptions = { "Manage Staff", "Manage Products", "Manage Stock",
			"View all Stock and Products", "Staff Food Bank", "Quit" };
	static String[] staffManagerMenuOptions = { "Add Staff", "Remove Staff", "Edit Staff", "Go Back" };
	static String[] productManagerMenuOptions = { "Add Product to System", "Edit Product", "Change the Global Discount for all Reduced Products", "Go back" };
	static String[] stockManagerMenuOptions = { "Add Batch", "Edit Batch", "Go Back" };
	static String[] stockEmployeeMenuOptions = { "Add Batch", "Edit Batch", "View All Stock and Products", "Go Back" };
	static String[] yesOrNoOptions = { "Yes", "No" };
	static String[] staffEmployeeUpdateOptions = { "Forename", "Surname", "Password", "Quit" };
	static String[] staffManagerUpdateOptions = { "Forename", "Surname", "Password", "Administrator Role",
			"Locked Out Status", "Quit" };
	static String[] overviewSubOptions = { "View Summary", "Search for Specific Product", "See Wastage in Last 30 days", "View products expiring soon",
			"Go Back" };
	static String[] handheldMainMenu = { "Add/Remove Amount of This Product Batch", "Mark Batch as Reduced/Not Reduced",
			"Exit" };
}
