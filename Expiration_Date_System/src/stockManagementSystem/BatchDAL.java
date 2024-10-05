package stockManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public abstract class BatchDAL {

	// CLASS DATA //
	private static String fileName = "Batches.csv";
	private static File batchFile = new File(fileName);
	private static boolean hasHeader = true;

	// METHODS //
	/**
	 * Reads in all of the records on the batch file and returns them as an
	 * arraylist of ProductBatch objects
	 * 
	 * @return an arraylist of all the batch on the file
	 * @throws IOException
	 */
	public static ArrayList<ProductBatch> readAllBatches() throws IOException {
		ArrayList<ProductBatch> batchMembers = new ArrayList<ProductBatch>();

		try {
			FileReader fr = new FileReader(batchFile);
			BufferedReader br = new BufferedReader(fr);

			// Skip the headers if there are any
			if (hasHeader) {
				br.readLine();
			}

			// Look at each line and construct a productBatch object from each one
			String currentLine = br.readLine();
			while (currentLine != null) {
				String[] lineData = currentLine.split(",");

				// Check that all data is present
				if (lineData.length == 4) {

					// Splitting the date up into year, month and date
					String[] dateData = lineData[2].split(":");

					// Only include this record in the arraylist if all of its data relating to the
					// date is present and complete
					if (dateData.length == 3) {
						int year = Integer.parseInt(dateData[0]) - 1900;
						int month = Integer.parseInt(dateData[1]) - 1;
						int day = Integer.parseInt(dateData[2]);
						Date expiry = new Date(year, month, day);

						// Converting the non-string attributes to the correct data types
						int id = Integer.parseInt(lineData[0]);
						int quantity = Integer.parseInt(lineData[1]);

						// Obtaining true/false for boolean value from the string read from the file
						boolean isDiscounted;
						if (lineData[3].equals("true")) {
							isDiscounted = true;
						} else {
							isDiscounted = false;
						}

						// Instantiate a ProductBatch object and add it to the arraylist to be returned
						ProductBatch obtainedBatch = new ProductBatch(id, quantity, expiry, isDiscounted);
						batchMembers.add(obtainedBatch);

						currentLine = br.readLine();
					}
				}
			}
			// Read next line of file
			currentLine = br.readLine();

			br.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println(fileName + " could not be found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return batchMembers;
	}

	/**
	 * Adds a record of the inputed ProductBatch object to the file
	 * 
	 * @param batchToAdd - The ProductBatch object to be added to the file
	 * @return True if the record was added correctly and False if the record was
	 *         not added
	 * @throws IOException
	 */
	public static boolean addBatchToFile(ProductBatch batchToAdd) throws IOException {
		if (batchToAdd == null || batchToAdd.isEmpty()) {
			return false;
		} else {
			try {
				FileWriter fw = new FileWriter(batchFile, true);
				PrintWriter pw = new PrintWriter(fw);

				// If no file exists, create a new one
				if (!batchFile.exists()) {
					batchFile.createNewFile();
					if (hasHeader) {
						pw.println("ProductID,Quantity,ExpiryDate,IsDiscounted");
					}
				}

				// Preparing the expiry date attribute to be formatted as a string for the file
				Date expiry = batchToAdd.getExpiryDate();
				SimpleDateFormat ymd = new SimpleDateFormat("YYYY:M:d");

				// Write the data from inputed ProductBatch object to a record on the file
				String recordToAdd = batchToAdd.getProductId() + ",";
				recordToAdd += batchToAdd.getQuantity() + ",";
				recordToAdd += ymd.format(expiry) + ",";
				recordToAdd += batchToAdd.isDiscounted();
				pw.println(recordToAdd);

				pw.close();
				return true;
			} catch (IOException ioe) {
				return false;
			}
		}
	}

	/**
	 * Removes a specific ProductBatch object from the file. The data of the batch
	 * entered must match the data of a record on the file exactly or it wont be
	 * removed. Rewrites the entire file to not include the record that was
	 * 'deleted'.
	 * 
	 * @param batchToRemove - The ProductBatch to be removed from the file
	 * @return True if a record was removed from the file, False if not
	 * @throws IOException
	 */
	public static boolean removeBatchFromFile(ProductBatch batchToRemove) throws IOException {
		if (batchToRemove == null || batchToRemove.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<ProductBatch> batchMembers = readAllBatches();

				// If the record is present in the file, rewrite the file
				if (fileContains(batchToRemove)) {
					// Finding ProductBatch object that needs to be deleted and rewriting all of the
					// others to the file
					boolean found = false; // Flag to be raised once the record to be removed has been reached
					int i = 0;
					while (i < batchMembers.size()) {
						ProductBatch current = batchMembers.get(i);

						// Check to see if the ProductBatch object is the one to be deleted.
						if (current.equals(batchToRemove)) {
							batchMembers.remove(current);
							found = true;
						}
						i++;
					}

					// If the record was deleted, rewrite the file with the new info
					if (found) {
						rewriteFile(batchMembers);
						return true;
					}
				}
			} catch (FileNotFoundException fnfe) {
				System.out.println(fileName + " could not be found");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Updates a specific record on the file with the data passed as the parameter
	 * 
	 * @param idToUpdate       - The id of the record to update
	 * @param dateToUpdate     - The expiry date of the record to update
	 * @param updatedBatchInfo - The new data of the record
	 * @return True if the record was updated, False if not
	 * @throws IOException
	 */
	public static boolean updateBatchOnFile(int idToUpdate, Date dateToUpdate, ProductBatch updatedBatchInfo)
			throws IOException {
		if (idToUpdate < 1 || dateToUpdate == null || updatedBatchInfo == null || updatedBatchInfo.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<ProductBatch> batchMembers = readAllBatches();

				// If the record is present in the file
				if (!findSpecificBatch(idToUpdate, dateToUpdate).isEmpty()) {
					// Finding ProductBatch object that needs to be deleted and rewriting all of the
					// others to the file
					boolean found = false; // Flag to be raised once the record to be removed has been reached
					int i = 0;
					while (i < batchMembers.size()) {
						ProductBatch current = batchMembers.get(i);

						// Check to see if the ProductBatch object is the one to be deleted.
						if (current.getProductId() == idToUpdate && current.getExpiryDate().equals(dateToUpdate)) {
							batchMembers.set(i, updatedBatchInfo);
							found = true;
						}
						i++;
					}

					// If the record was deleted, rewrite the file with the new info
					if (found) {
						rewriteFile(batchMembers);
						return true;
					}
				}
			} catch (FileNotFoundException fnfe) {
				System.out.println(fileName + " could not be found");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Searches the records on the Batches file for the one that matches the name
	 * inputed
	 * 
	 * @param idToLocate - The ID of the batches to search for
	 * @return an arraylist of ProductBatch objects that have the matching
	 *         ProductID, or an empty arraylist if none were found
	 * @throws IOException
	 */
	public static ArrayList<ProductBatch> findBatchesByProductID(int idToLocate) throws IOException {
		ArrayList<ProductBatch> locatedBatches = new ArrayList<ProductBatch>();

		try {
			FileReader fr = new FileReader(batchFile);
			BufferedReader br = new BufferedReader(fr);

			// Skip the headers if there are any
			if (hasHeader) {
				br.readLine();
			}

			// Look at each record and compare its ID to idToLocate
			String currentLine = br.readLine(); // Obtain the first line of data
			while (currentLine != null) {
				String[] lineData = currentLine.split(",");
				int productID = Integer.parseInt(lineData[0]);

				// Skip to next record if name does not match inputed name or current
				// line has incomplete data
				if (productID == idToLocate && lineData.length == 4) {

					// Only add this record if the data relating to expiry date has 3 values (year,
					// month, date)
					String[] dateData = lineData[2].split(":");
					if (dateData.length == 3) {
						// Quantity
						int quantity = Integer.parseInt(lineData[1]);

						// Expiry date
						int year = Integer.parseInt(dateData[0]) - 1900;
						int month = Integer.parseInt(dateData[1]) - 1;
						int day = Integer.parseInt(dateData[2]);
						Date expiry = new Date(year, month, day);

						// IsDiscounted
						boolean isDiscounted;
						if (lineData[3].equals("true")) {
							isDiscounted = true;
						} else {
							isDiscounted = false;
						}

						// Add a ProductBatch object containing the data from the current record to the
						// arraylist
						ProductBatch batchFromData = new ProductBatch(productID, quantity, expiry, isDiscounted);
						locatedBatches.add(batchFromData);
					}
				}

				// Read next line of file
				currentLine = br.readLine();
			}

			br.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println(fileName + " could not be found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return locatedBatches;
	}

	/**
	 * Searches the records on the Batches file for the one that matches the expiry
	 * date inputed inputed
	 * 
	 * @param idToLocate - The ID of the batches to search for
	 * @return an arraylist of ProductBatch objects that have the matching
	 *         ProductID, or an empty arraylist if none were found
	 * @throws IOException
	 */
	public static ArrayList<ProductBatch> findBatchesByExpiryDate(Date expiryToLocate) throws IOException {
		ArrayList<ProductBatch> locatedBatches = new ArrayList<ProductBatch>();

		if (expiryToLocate == null) {
			return locatedBatches;
		} else {
			try {
				FileReader fr = new FileReader(batchFile);
				BufferedReader br = new BufferedReader(fr);
				SimpleDateFormat dateToStringFormat = new SimpleDateFormat("YYYY:M:d"); // Will be used to compare
																						// expiry
																						// dates

				// Skip the headers if there are any
				if (hasHeader) {
					br.readLine();
				}

				// Look at each record and compare its expiry date to expiryToLocate
				String currentLine = br.readLine(); // Obtain the first line of data
				while (currentLine != null) {
					String[] lineData = currentLine.split(",");

					// Only add this record if the data relating to expiry date has 3 values (year,
					// month, date)
					String[] dateData = lineData[2].split(":");
					if (dateData.length == 3) {
						// Expiry date
						int year = Integer.parseInt(dateData[0]) - 1900;
						int month = Integer.parseInt(dateData[1]) - 1;
						int day = Integer.parseInt(dateData[2]);
						Date currentExpiry = new Date(year, month, day);

						// Compare the expiry date of the record with the one to look for
						String currentExpiryString = dateToStringFormat.format(currentExpiry);
						String expiryStringtoLocate = dateToStringFormat.format(expiryToLocate);
						boolean datesMatched = currentExpiryString.equals(expiryStringtoLocate);

						// Skip to next record if date is not the same or current
						// line has incomplete data
						if (datesMatched && lineData.length == 4) {
							// ProductID
							int productID = Integer.parseInt(lineData[0]);

							// Quantity
							int quantity = Integer.parseInt(lineData[1]);

							// IsDiscounted
							boolean isDiscounted;
							if (lineData[3].equals("true")) {
								isDiscounted = true;
							} else {
								isDiscounted = false;
							}

							// Add a ProductBatch object containing the data from the current record to the
							// arraylist
							ProductBatch batchFromData = new ProductBatch(productID, quantity, currentExpiry,
									isDiscounted);
							locatedBatches.add(batchFromData);
						}
					}

					// Read next line of file
					currentLine = br.readLine();
				}

				br.close();
			} catch (FileNotFoundException fnfe) {
				System.out.println(fileName + " could not be found");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return locatedBatches;
		}
	}

	/**
	 * Locates the batch on the file with the inputted ProductID and Expiry Date.
	 * 
	 * @param idToFind   - The productID of the batch to find
	 * @param dateToFind - The Expiry Date of the batch to find
	 * @return a productBatch object with the data of the record on the system, or
	 *         an empty productBatch object if no such record was found
	 * @throws IOException
	 */
	public static ProductBatch findSpecificBatch(int idToFind, Date dateToFind) throws IOException {
		ArrayList<ProductBatch> batchesOfProduct = findBatchesByProductID(idToFind);
		ProductBatch batchToReturn = new ProductBatch();

		if (dateToFind == null) {
			return batchToReturn;
		} else {
			// Search through arraylist until batch with the correct expiry date is found or
			// end is reached
			boolean found = false;
			int i = 0;
			while (!found && i < batchesOfProduct.size()) {
				ProductBatch current = batchesOfProduct.get(i);

				// Check if dates match
				if (current.getExpiryDate().equals(dateToFind)) {
					batchToReturn = current;
				}
				i++;
			}

			return batchToReturn;
		}
	}

	/**
	 * Checks to see if the inputted ProductBatch object is present on the file
	 * 
	 * @param batchToSearch - The ProductBatch to search for
	 * @return True if the file contains the batch, False if not
	 * @throws IOException
	 */
	public static boolean fileContains(ProductBatch batchToSearch) throws IOException {
		try {
			ArrayList<ProductBatch> allBatchesWithID = findBatchesByProductID(batchToSearch.getProductId());
			for (ProductBatch current : allBatchesWithID) {
				if (current.equals(batchToSearch)) {
					return true;
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fileName + " could not be found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

	/**
	 * Updates the file to remove all batches that expired over the amount of time inputted ago and mark the ones that have less than 3 days left before expiring as reduced
	 * 
	 * @param currentDate - The date of today
	 * @param age - The amount of time in days batches are allowed to be kept after expiring
	 * @throws IOException
	 */
	public static void updateAllBatches(Date currentDate, int age) throws IOException{
		// Obtain current date and the date 30 days ago
		long time = currentDate.getTime();
		long days = age * 86400000l;
		Date targetDate = new Date();
		targetDate.setTime(time - days);
		
		// Obtain date 3 days from now to mark products as reduced
		long reducedTime = 3 * 86400000l;
		Date reducedDate = new Date();
		reducedDate.setTime(time + reducedTime);
		
		// Find batches that expired before targetDate and those that need to be marked as reduced (3 days left until expiry)
		ArrayList<ProductBatch> allBatches = readAllBatches();
		ArrayList<ProductBatch> newBatchList = new ArrayList<>();
		for(ProductBatch current : allBatches) {
			if (current.getExpiryDate().compareTo(targetDate) >= 0) { // If batch expired over 30 daysago
				newBatchList.add(current);
			} else if(current.getExpiryDate().compareTo(reducedDate) <= 0 ) { // If batch has 3 days left until expiry
				current.setDiscounted(true);
				newBatchList.add(current);
			}
		}
		
		// Rewrite file with new batches
		rewriteFile(newBatchList);
	}

	/**
	 * Rewrites the file and populates it with the contents of the arraylist passed
	 * as the parameter
	 * 
	 * @param fileContents - The product objects to rewrite the file with
	 * @throws IOException
	 */
	private static void rewriteFile(ArrayList<ProductBatch> fileContents) throws IOException {
		FileWriter fw = new FileWriter(batchFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");

		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("ProductID,Quantity,ExpiryDate,IsDiscounted");
		}
		pw.close(); // Closing print writer as its no longer needed (addProductToFile() will create
					// its own)

		// Write fileContents to file
		for (ProductBatch current : fileContents) {
			addBatchToFile(current);
		}
	}

	/**
	 * Method used for testing only. Points the BatchDAL to a testing csv file so
	 * that the actual Batch.csv does not have its data changed or damaged
	 * 
	 * @param testing - If true, use BatchTest.csv, if false, use actual Batch.csv
	 *                file
	 */
	public static void switchToTestFile(boolean testing) {
		if (testing) {
			fileName = "BatchesTest.csv";
			batchFile = new File(fileName);
		} else {
			fileName = "Batch.csv";
			batchFile = new File(fileName);
		}
	}
}
