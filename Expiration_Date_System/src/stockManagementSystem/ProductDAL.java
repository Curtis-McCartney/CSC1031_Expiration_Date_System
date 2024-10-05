package stockManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *         This class handles reading/writing to the Products.csv file and
 *         applies a level of abstraction when it is used. It contains methods
 *         to make accessing the file simple.
 * 
 *         // CURRENTLY UNTESTED //
 */
public abstract class ProductDAL {

	// CLASS DATA //
	private static String fileName = "Products.csv";
	private static File productFile = new File(fileName);
	private static boolean hasHeader = true;

	// METHODS //
	/**
	 * Reads in all of the records on the product file and returns them as an
	 * arraylist of Product objects
	 * 
	 * @return an arraylist of all the product on the file
	 * @throws IOException
	 */
	public static ArrayList<Product> readAllProduct() throws IOException {
		ArrayList<Product> productMembers = new ArrayList<Product>();

		try {
			FileReader fr = new FileReader(productFile);
			BufferedReader br = new BufferedReader(fr);

			// Skip the headers if there are any
			if (hasHeader) {
				br.readLine();
			}

			// Look at each line and construct a product object from each one
			String currentLine = br.readLine();
			while (currentLine != null) {
				String[] lineData = currentLine.split(",");

				// Check that all data is present
				if (lineData.length == 3) {

					// Converting the number attributes to the correct data types
					int id = Integer.parseInt(lineData[0]);
					double price = Double.parseDouble(lineData[2]);

					// Instantiate a Product object and add it to the arraylist to be returned
					Product obtainedProduct = new Product(id, lineData[1], price);
					productMembers.add(obtainedProduct);

					currentLine = br.readLine();
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
		return productMembers;
	}

	/**
	 * Adds a record of the inputed Product object to the file
	 * 
	 * @param productToAdd - The Product object to be added to the file
	 * @return True if the record was added correctly and False if the record was
	 *         not added
	 * @throws IOException
	 */
	public static boolean addProductToFile(Product productToAdd) throws IOException {
		if (productToAdd == null || productToAdd.isEmpty() || !isIDFree(productToAdd.getProductId())) {
			return false;
		} else {
			try {
				FileWriter fw = new FileWriter(productFile, true);
				PrintWriter pw = new PrintWriter(fw);

				// If no file exists, create a new one
				if (!productFile.exists()) {
					productFile.createNewFile();
					if (hasHeader) {
						pw.println("ProductID,Name,Price");
					}
				}

				// Write the data from inputed Product object to a record on the file
				String recordToAdd = productToAdd.getProductId() + ",";
				recordToAdd += productToAdd.getProductName() + ",";
				recordToAdd += productToAdd.getPrice();
				pw.println(recordToAdd);

				pw.close();
				return true;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * Removes a specific product object from the file. The data of the product
	 * entered must match the data of a record on the file exactly or it wont be
	 * deleted.
	 * 
	 * @param productToRemove - The Product member to be removed from the file
	 * @return True if a record was removed from the file, False if not
	 * @throws IOException
	 */
	public static boolean removeProductFromFile(Product productToRemove) throws IOException {
		if (productToRemove == null || productToRemove.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<Product> productMembers = readAllProduct();

				if (!productMembers.isEmpty()) {
					// Finding Product object that needs to be deleted and rewriting all of the
					// others
					// to the file
					boolean found = false;
					int i = 0;
					while (!found && i < productMembers.size()) {
						Product current = productMembers.get(i);

						// Check to see if the Product object is the one to be deleted.
						if (current.equals(productToRemove)) {
							productMembers.remove(current);
							found = true;
						}
						i++;
					}

					// If record was deleted, update the file with the new data
					if (found) {
						rewriteFile(productMembers);
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
	 * @param idToUpdate         - The ID of the record to update
	 * @param updatedProductInfo - The new data of the record
	 * @return True if the record was updated, False if not
	 * @throws IOException
	 */
	public static boolean updateProductOnFile(int idToUpdate, Product updatedProductInfo) throws IOException {
		if (updatedProductInfo == null || updatedProductInfo.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<Product> productMembers = readAllProduct();

				if (!productMembers.isEmpty()) {
					// Finding Product object that needs to be deleted and rewriting all of the
					// others
					// to the file
					boolean found = false;
					int i = 0;
					while (!found && i < productMembers.size()) {
						Product current = productMembers.get(i);

						// Check to see if the Product object is the one to be deleted.
						if (current.getProductId() == idToUpdate) {
							productMembers.set(i, updatedProductInfo);
							found = true;
						}
						i++;
					}

					// If record was updated, update the file with the new data
					if (found) {
						rewriteFile(productMembers);
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
	 * Searches the records on the Product file for the one that matches the id
	 * inputed
	 * 
	 * @param id - The id of the product to search for
	 * @return a Product object containing the data of the record found, and an
	 *         empty product object if no Product with the inputed username exists
	 * @throws IOException
	 */
	public static Product findProductByID(int id) throws IOException {
		Product locatedProduct = new Product();

		if (id > 0) {
			try {
				FileReader fr = new FileReader(productFile);
				BufferedReader br = new BufferedReader(fr);

				// Skip the headers if there are any
				if (hasHeader) {
					br.readLine();
				}

				// Look at each line and construct a product object from each one
				boolean found = false;
				String currentLine = br.readLine(); // Obtain the first line of data
				while (currentLine != null && found == false) {
					String[] lineData = currentLine.split(",");

					// Skip to next record if name does not match inputed name or current
					// line has incomplete data
					if (lineData.length == 3 && Integer.parseInt(lineData[0]) == id) {

						// Converting the number attributes to the correct data types
						String name = lineData[1];
						double price = Double.parseDouble(lineData[2]);

						// Return a product object with the data read from the file
						locatedProduct = new Product(id, name, price);
						found = true;
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
		}
		return locatedProduct;
	}
	
	/**
	 * Searches the records on the Product file for the one that matches the name
	 * inputed
	 * 
	 * @param name - The name of the product to search for
	 * @return a Product object containing the data of the record found, and an
	 *         empty product object if no Product with the inputed username exists
	 * @throws IOException
	 */
	public static Product findProductByName(String name) throws IOException {
		Product locatedProduct = new Product();

		if (name != null && !name.equals("")) {
			try {
				FileReader fr = new FileReader(productFile);
				BufferedReader br = new BufferedReader(fr);

				// Skip the headers if there are any
				if (hasHeader) {
					br.readLine();
				}

				// Look at each line and construct a product object from each one
				boolean found = false;
				String currentLine = br.readLine(); // Obtain the first line of data
				while (currentLine != null && found == false) {
					String[] lineData = currentLine.split(",");

					// Skip to next record if name does not match inputed name or current
					// line has incomplete data
					if (lineData.length == 3 && lineData[1].equals(name)) {

						// Converting the number attributes to the correct data types
						int id = Integer.parseInt(lineData[0]);
						double price = Double.parseDouble(lineData[2]);

						// Return a product object with the data read from the file
						locatedProduct = new Product(id, lineData[1], price);
						found = true;
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
		}
		return locatedProduct;
	}

	/**
	 * Checks to see if the inputted Product object is present on the file
	 * 
	 * @param productToSearch - The Product to search for
	 * @return True if the file contains the product member, False if not
	 * @throws IOException
	 */
	public static boolean fileContains(Product productToSearch) throws IOException {
		try {
			Product foundProduct = findProductByName(productToSearch.getProductName());
			if (productToSearch.equals(foundProduct)) {
				return true;
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fileName + " could not be found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks that the inputted ID is not present on the file already
	 * 
	 * @param id - The id to check for
	 * @return True if the ID is not present on the file, False if the ID is present
	 * @throws IOException
	 */
	public static boolean isIDFree(int id) throws IOException {
		Product found = findProductByID(id);
		if(found.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Rewrites the file and populates it with the contents of the arraylist passed
	 * as the parameter
	 * 
	 * @param fileContents - The product objects to rewrite the file with
	 * @throws IOException
	 */
	private static void rewriteFile(ArrayList<Product> fileContents) throws IOException {
		FileWriter fw = new FileWriter(productFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");

		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("ProductID,Name,Price");
		}
		pw.close(); // Closing print writer as its no longer needed (addProductToFile() will create
					// its own)

		// Write fileContents to file
		for (Product current : fileContents) {
			addProductToFile(current);
		}
	}
	
	/**
	 * Method used for testing only. Points the ProductDAL to a testing csv file so
	 * that the actual Product.csv does not have its data changed or damaged
	 * 
	 * @param testing - If true, use ProductTest.csv, if false, use actual Product.csv
	 *                file
	 */
	public static void switchToTestFile(boolean testing) {
		if (testing) {
			fileName = "ProductsTest.csv";
			productFile = new File(fileName);
		} else {
			fileName = "Products.csv";
			productFile = new File(fileName);
		}
	}
}
