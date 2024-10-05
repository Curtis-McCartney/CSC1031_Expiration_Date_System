package stockManagementSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * This class represents the version of the system that will be
 * installed onto the handheld devices that the shop employees use to
 * scan products and receive info based on them
 * 
 */
public class MainHandheldSystem {
	private static Scanner input = new Scanner(System.in);
	private static SimpleDateFormat ymd = new SimpleDateFormat("YYYY:M:d");

	public static void main(String[] args) {
		// Declare Menu and ask user to scan product
		Menu mHandheldMain = new Menu("Handheld Device", Resources.handheldMainMenu);

		boolean running = true;
		while (running) {
			System.out.println("Scan Batch");
			input.nextLine();
			ProductBatch scannedBatch = scanBatch();
			displayBatchData(scannedBatch);

			// Display options and get user choice
			int command = mHandheldMain.getUserChoice();

			switch (command) {
			case 1: // Add/Remove Amount of This Product Batch
				editBatchAmount(scannedBatch);
				break;
			case 2: // Mark Batch as Reduced/Not Reduced
				markBatchAsReduced(scannedBatch);
				break;
			case 3: // Exit
				running = false;
				break;
			}
		}

		input.close();
	}

	/**
	 * This method will be called when scanning barcodes using the handheld device's
	 * built-in scanner system
	 * 
	 * @return a ProductBatch with the data found on the file after scanning a batch
	 */
	public static ProductBatch scanBatch() {
		ProductBatch foundBatch = new ProductBatch();

		// These hardcoded values are just placeholders for an algorithm that will take
		// input from the scanner and return the id and expiry of the batch being
		// scanned
		int foundID = 1;
		Date foundExpiry = new Date(124, 4, 15);

		// Locate the batch on the file from the data found through the scanning
		try {
			foundBatch = BatchDAL.findSpecificBatch(foundID, foundExpiry);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return foundBatch;
	}

	/**
	 * Displays the ProductBatch inputted in a dedicated format
	 * 
	 * @param inputtedBatch - the batch to be displayed
	 */
	public static void displayBatchData(ProductBatch inputtedBatch) {
		// Obtaining product referenced by the inputted batch
		try {
			// Output Data
			Product referencedProduct = ProductDAL.findProductByID(inputtedBatch.getProductId());
			System.out.println("========================================================");
			System.out.println("Product: " + referencedProduct.getProductName());
			System.out.println("Quantity: " + inputtedBatch.getQuantity());
			System.out.println("Expiry Date: " + ymd.format(inputtedBatch.getExpiryDate()));
			System.out.println("Discounted?: " + inputtedBatch.isDiscounted());
			System.out.println("========================================================");
		} catch (IOException ioe) {
			System.out.println("Referenced Product could not be found");
			ioe.printStackTrace();
		}
	}

	public static void editBatchAmount(ProductBatch inputtedBatch) {
		System.out.print("Enter the amount to increase the quanity by (negative values decrease): ");
		int changeAmount = input.nextInt();
		input.nextLine();

		// Update file with new data
		int currentQuantity = inputtedBatch.getQuantity();
		int newQuantity = currentQuantity + changeAmount;
		// If the new quantity would be negative, set it to 0 insetad
		if (inputtedBatch.getQuantity() <= 0) {
			inputtedBatch.setQuantity(0);
		} else {
			inputtedBatch.setQuantity(newQuantity);
		}

		// Update record on file
		try {
			BatchDAL.updateBatchOnFile(inputtedBatch.getProductId(), inputtedBatch.getExpiryDate(), inputtedBatch);
		} catch (IOException ioe) {
			System.out.println("Error occurred when attempting to update batch");
		}
	}

	public static void markBatchAsReduced(ProductBatch inputtedBatch) {
		System.out.println("Should batch be reduced? (Y/N): ");
		String answer = input.nextLine().toLowerCase(); // Forcing lower case so both lowercase and uppercase are fine

		// Understand user input
		if (answer.equals("y")) {
			inputtedBatch.setDiscounted(true);
		} else {
			inputtedBatch.setDiscounted(false);
		}

		// Update record on file
		try {
			BatchDAL.updateBatchOnFile(inputtedBatch.getProductId(), inputtedBatch.getExpiryDate(), inputtedBatch);
		} catch (IOException ioe) {
			System.out.println("Error occurred when attempting to update batch");
		}
	}
}
