package stockManagementSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 
 *         A temporary class to program the stockOverview menu in while the
 *         manager menu is being created.
 */
public abstract class StockOverviewMenu {
	private static Date currentDate;
	private static SimpleDateFormat ymd = new SimpleDateFormat("YYYY:M:d");

	private static Scanner input = new Scanner(System.in);

	public static void displayMenu() {
		// Obtaining the current date
		Date autoGenDate = new Date();
		currentDate = new Date(autoGenDate.getYear(), autoGenDate.getMonth(), autoGenDate.getDate()); // Stops
																										// milliseconds
																										// // from
																										// interfering
																										// with function
		// Create menu and obtain user input
		Menu mOverviewSubOptions = new Menu("See Overview", Resources.overviewSubOptions);
		int option = mOverviewSubOptions.getUserChoice();
		switch (option) {
		case 1: // View Summary
			viewSummary();
			break;
		case 2: // Search for Specific Product
			searchSpecific();
			break;
		case 3: // See wastage in Last 30 Days
			seeWastage();
			break;
		case 4 : // view products expiring soon
			expiringSoon();
		case 5: // Go Back

			break;
		}
	}

	/**
	 * Lists each product along with how much stock the shop has of them, and how
	 * much has been wasted in the last 30 days
	 */
	private static void viewSummary() {
		try {
			// Obtain list of all products
			ArrayList<Product> allProducts = ProductDAL.readAllProduct();
			ArrayList<Product> overstockedProducts = new ArrayList<Product>(); // Will hold products that have more than
																				// the allowed wastage every month
			int allowedExpiry = 5;

			// Calculate total amount of each product in stock and output
			for (Product current : allProducts) {
				ArrayList<ProductBatch> allBatchesForProduct = BatchDAL.findBatchesByProductID(current.getProductId());

				// Define variables to use in nested loop
				int totalStock = 0;
				int wasted = 0;

				// Run through all batches and differentiate the in-date and expired ones and
				// add to respective counter
				for (ProductBatch currentBatch : allBatchesForProduct) {
					if (currentBatch.getExpiryDate().compareTo(currentDate) >= 0) {
						totalStock += currentBatch.getQuantity();
					} else {
						wasted += currentBatch.getQuantity();
					}
				}

				// If too much waste, count product as overstocked
				if (wasted > allowedExpiry) {
					overstockedProducts.add(current);
				}

				// Output results for this product
				System.out.println(current.getProductName() + ": " + totalStock + " in stock - " + wasted
						+ " wasted in last 30 days");
			}

			// Give overview of what products to order less of to reduce wastage
			if (overstockedProducts.isEmpty()) {
				System.out.println("No overstocked products detected. Good work!");
			} else {
				System.out.println(
						"The following products have significant wastage. Order less of these products in the future: ");
				for (Product current : overstockedProducts) {
					System.out.println("- " + current.getProductName());
				}
			}

		} catch (IOException ioe) {
			System.out.println("List of Products could not be obtained. Returning to previous menu");
		}
	}

	/**
	 * Shows data on all batches of the specified product as well as how many have
	 * been wasted in the last 30 days
	 */
	private static void searchSpecific() {
		try {
			// Obtain specific ID to look for from user
			int id = 0;
			boolean invalidInput = true;
			do {
				System.out.print("Input the ID of the product to display (or -1 to return to previous menu): ");
				try {
					id = input.nextInt();
					input.nextLine();
					if (id == -1) {
						return; // Exit method if user wants to return to menu
					}
					invalidInput = ProductDAL.findProductByID(id).isEmpty();
					if (invalidInput) {
						System.out.println("No Product with that ID: try again");
					}
				} catch (Exception e) {
					System.out.print("Input invalid, try again: ");
				}
			} while (invalidInput);

			// Search products for ID and, obtain all batches of that product
			Product found = ProductDAL.findProductByID(id);
			ArrayList<ProductBatch> allBatches = BatchDAL.findBatchesByProductID(id);
			ArrayList<ProductBatch> sortedBatches = sortByExpiryDate(allBatches);

			// Output title
			System.out.println(found.getProductName());

			// Output in-date stock
			boolean firstExpiredFound = false;
			int indexOfFirstWasted = -1;
			int i = 0;
			System.out.println("Batches in stock:");
			while (!firstExpiredFound && i < sortedBatches.size()) {
				ProductBatch currentBatch = sortedBatches.get(i);
				if (sortedBatches.get(i).getExpiryDate().compareTo(currentDate) > 0) {
					System.out.println("- Expiring: " + ymd.format(currentBatch.getExpiryDate()) + " - "
							+ currentBatch.getQuantity());
				} else {
					firstExpiredFound = true;
					indexOfFirstWasted = i;
				}
				i++;
			}
			System.out.println();

			// Output wasted stock
			if (indexOfFirstWasted >= 0) {
				System.out.println("Stock wasted in past 30 days:");
				for (int j = indexOfFirstWasted; j < sortedBatches.size(); j++) {
					ProductBatch currentBatch = sortedBatches.get(j);
					System.out.println("- Expired: " + ymd.format(currentBatch.getExpiryDate()) + " - "
							+ currentBatch.getQuantity());
				}
			} else {
				System.out.println("No stock wasted in past 30 days. Well done!");
			}
		} catch (IOException ioe) {

		}
	}

	/**
	 * Shows all wasted batches of products in the last 30 days ordered by date
	 */
	private static void seeWastage() {
		try {
			// Obtaining data
			SimpleDateFormat ymd = new SimpleDateFormat("YYYY:M:d");
			ArrayList<ProductBatch> allBatches = BatchDAL.readAllBatches();
			ArrayList<ProductBatch> wastedBatches = findExpiredBatches(allBatches);
			ArrayList<ProductBatch> sortedBatchesDate = sortByExpiryDate(wastedBatches);
			ArrayList<ProductBatch> sortedBatchesID = sortByProductID(sortedBatchesDate);
			int totalWastedProducts = 0;

			// Output wastages
			int latestID = 0;
			for (ProductBatch current : sortedBatchesID) {
				// If a new productID has appeared, take a new line and output the name of the
				// product before continuing
				if (current.getProductId() != latestID) {
					latestID = current.getProductId();
					System.out.println();
					Product referencedProduct = ProductDAL.findProductByID(latestID);
					System.out.println(referencedProduct.getProductName());
				}

				// Output current batch
				System.out.println("- Expired: " + ymd.format(current.getExpiryDate()) + " - " + current.getQuantity());
				totalWastedProducts += current.getQuantity();

			}
			System.out.println();
			System.out.println("Total products wasted over last 30 days: " + totalWastedProducts); // Total wastage

			// Output batches about to expire today
			ArrayList<ProductBatch> expiringToday = BatchDAL.findBatchesByExpiryDate(currentDate);
			ArrayList<ProductBatch> expiringTodaySorted = sortByProductID(expiringToday);
			System.out.println("Products about to expire today: ");
			// Output product name and the amount expiring for each batch expiring today
			for (ProductBatch current : expiringTodaySorted) {
				Product currentProduct = ProductDAL.findProductByID(current.getProductId());
				System.out.println(" - " + currentProduct.getProductName() + ": " + current.getQuantity());
			}

		} catch (IOException ioe) {
			System.out.println("Batches could not be read. Returning to previous menu");
		}
	}

	/**
	 * Sorts all batches inputted via their expiry date in ascending order
	 * 
	 * @param unsortedList - The list to sort
	 * @return - The sorted list, ordered by expiry date in ascending order
	 */
	private static ArrayList<ProductBatch> sortByExpiryDate(ArrayList<ProductBatch> unsortedList) {
		int j;
		ProductBatch temp;
		for (int i = 1; i < unsortedList.size(); i++) {
			j = i;
			while (j > 0
					&& unsortedList.get(j).getExpiryDate().compareTo(unsortedList.get(j - 1).getExpiryDate()) < 0) {
				temp = unsortedList.get(j);
				unsortedList.set(j, unsortedList.get(j - 1));
				unsortedList.set(j - 1, temp);
				j--;
			}
		}

		return unsortedList;
	}

	/**
	 * Sorts all batches inputted via their productID in ascending order
	 * 
	 * @param unsortedList - The list to sort
	 * @return The sorted list of batches, ordered by productID in ascending order
	 */
	private static ArrayList<ProductBatch> sortByProductID(ArrayList<ProductBatch> unsortedList) {
		int j;
		ProductBatch temp;
		for (int i = 1; i < unsortedList.size(); i++) {
			j = i;
			while (j > 0 && unsortedList.get(j).getProductId() > unsortedList.get(j - 1).getProductId()) {
				temp = unsortedList.get(j);
				unsortedList.set(j, unsortedList.get(j - 1));
				unsortedList.set(j - 1, temp);
				j--;
			}
		}

		return unsortedList;
	}

	/**
	 * Obtains all batches in the inputted list that have expired
	 * 
	 * @param batchList - A list of batches to search for expired ones from
	 * @return an arraylist containing the batches that are expired
	 */
	private static ArrayList<ProductBatch> findExpiredBatches(ArrayList<ProductBatch> batchList) {
		// Loop through array, removing batches that arent expired
		ArrayList<ProductBatch> listToReturn = new ArrayList<ProductBatch>();
		for (int i = 0; i < batchList.size(); i++) {
			if (batchList.get(i).getExpiryDate().compareTo(currentDate) < 0) {
				listToReturn.add(batchList.get(i));
			}
		}

		return listToReturn;
	}
	/**
	* @author Aaron Heaney
	*/
	public static void expiringSoon()
	{
	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, 3);

	
		ArrayList<ProductBatch> stock = ProductBatch.getStock();
		for (int i = 0; i<stock.size();i++)
			{
			
				if ( stock.get(i).getExpiryDate().after(currentDate) && stock.get(i).getExpiryDate().before(calendar.getTime()))
				{
					System.out.println(stock.get(i).toString());
				}		
				
			}
		System.out.println("\nAll of these batches expire within the next 3 days,\nit is recommended you either donate them via the staff foodbank or order less\nof these items in the future\n");

	}

	/**
	 * Clears the file and populates it with some dummy data for testing
	 * 
	 * @throws IOException
	 */
	/*
	 * private static void initialise() throws IOException { // Clear batch file
	 * ArrayList<ProductBatch> allBatches = BatchDAL.readAllBatches(); for
	 * (ProductBatch current : allBatches) { BatchDAL.removeBatchFromFile(current);
	 * } // Clear product file ArrayList<Product> allProducts =
	 * ProductDAL.readAllProduct(); for (Product current : allProducts) {
	 * ProductDAL.removeProductFromFile(current); }
	 * 
	 * // Populate product file ProductDAL.addProductToFile(new Product(1, "Apple",
	 * 1.00)); ProductDAL.addProductToFile(new Product(2, "Orange", 0.89));
	 * ProductDAL.addProductToFile(new Product(3, "White Bread", 1.00));
	 * ProductDAL.addProductToFile(new Product(4, "Pasta", 2.00));
	 * 
	 * // Populate batch file BatchDAL.addBatchToFile(new ProductBatch(1, 3, new
	 * Date(124, 4, 14), false)); BatchDAL.addBatchToFile(new ProductBatch(2, 5, new
	 * Date(124, 3, 19), true)); BatchDAL.addBatchToFile(new ProductBatch(3, 12, new
	 * Date(124, 5, 1), false)); BatchDAL.addBatchToFile(new ProductBatch(4, 8, new
	 * Date(124, 4, 13), false)); BatchDAL.addBatchToFile(new ProductBatch(1, 2, new
	 * Date(124, 2, 30), true)); BatchDAL.addBatchToFile(new ProductBatch(1, 12, new
	 * Date(124, 4, 27), false)); BatchDAL.addBatchToFile(new ProductBatch(2, 1, new
	 * Date(124, 2, 31), true)); BatchDAL.addBatchToFile(new ProductBatch(1, 7, new
	 * Date(124, 4, 15), false)); BatchDAL.addBatchToFile(new ProductBatch(3, 9, new
	 * Date(124, 1, 26), false)); BatchDAL.addBatchToFile(new ProductBatch(3, 2, new
	 * Date(124, 3, 16), true)); BatchDAL.addBatchToFile(new ProductBatch(4, 1, new
	 * Date(124, 3, 16), true)); }
	 */
}
