package stockManagementSystem;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * This Class is used to hold all the methods to do with the Staff Food Bank.
 */
public class StaffFoodBank {

	/**
	 * This is the method that actually runs the Staff Food Bank, allowing the Staff
	 * to see a list of all food going to expire today and letting them take this
	 * food for themselves.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void foodBank() throws IOException, ParseException {
		System.out.println("Staff Food Bank");
		System.out.println("Listing all food that is expiring by the end of today.");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dateFormat.format(new Date());
		Date today = dateFormat.parse(formattedDate);
		System.out.println(today);
		// BatchDAL.addBatchToFile(new ProductBatch(1, 2, today));

		ArrayList<ProductBatch> list = new ArrayList<ProductBatch>();
		list = BatchDAL.findBatchesByExpiryDate(new Date()); // An ArrayList of all the Batches that are expiring today.

		if (list.size() == 0) {
			System.out.println("Sorry, there is no food left to claim today, try again tomorrow!");
		} else {
			System.out.println("Please input the Product ID of the Product you wish to take home with you.");
			for (int i = 0; i < list.size(); i++) {
				int productID = list.get(i).getProductId();
				System.out.println(productID + ". " + ProductDAL.findProductByID(productID).getProductName()
						+ " - Quantity: " + list.get(i).getQuantity());
			}

			String choice = getValidOption();
			if (choice.toLowerCase() == "quit") {
				return;
			} else {
				int intChoice = Integer.parseInt(choice);
				// Once a valid Product ID has been entered, take it off the quantity for that
				// batch.
				ProductBatch batchToUpdate = BatchDAL.findSpecificBatch(intChoice, today);
				int updatedQuantity = batchToUpdate.getQuantity() - 1;
				if (updatedQuantity <= 0) {
					// If that was the final Product in that Batch, delete it from the file.
					BatchDAL.removeBatchFromFile(batchToUpdate);
				} else {
					System.out.println("updatedQuantity");
					batchToUpdate.setQuantity(updatedQuantity);
					BatchDAL.updateBatchOnFile(batchToUpdate.getProductId(), today, batchToUpdate);
				}
				System.out.println("You have successfully saved a Product from being thrown out, good job!");
			}

		}
	}

	/** This is Validation for the input from the User for what Product they wish to take from the system.
	 * They enter in either the word "Quit" to quit out of the StaffFoodBank.
	 * Or they enter in an integer correlating to the ProductID of the Product that they wish to take from the system.
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String getValidOption() throws NumberFormatException, IOException {
		boolean valid = false;
		Scanner keyb = new Scanner(System.in);
		String choice = keyb.nextLine();
		while (!valid) {
			if (Validate.validateFoodBankChoice(choice)) {
				valid = true;
			} else {
				System.out.println("Please input a valid Product ID or type the word \"Quit\".");
				choice = keyb.nextLine();
			}
		}
		return choice;
	}

	/** This Method was created in order to find which record the User was trying to update.
	 * @param choice - The input from the User for the ProductID for the record they wish to find.
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static int findRecordID(int productID) throws IOException, ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dateFormat.format(new Date());
		Date today = dateFormat.parse(formattedDate);
		ProductBatch batchToUpdate = BatchDAL.findSpecificBatch(productID, today);
		System.out.println(batchToUpdate);
		ArrayList<ProductBatch> allBatches = BatchDAL.readAllBatches();
		for (int i = 0; i < allBatches.size(); i++) {
			if (batchToUpdate.equals(allBatches.get(i))) {
				return i;
			}
		}
		return -1;
	}

}
