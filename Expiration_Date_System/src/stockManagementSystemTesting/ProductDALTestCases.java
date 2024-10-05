package stockManagementSystemTesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import stockManagementSystem.Product;
import stockManagementSystem.ProductDAL;

public class ProductDALTestCases {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		ProductDAL.switchToTestFile(true);
		try {
			productDALTest1();
			productDALTest2();
			productDALTest3();
			productDALTest4();
			productDALTest5();
			productDALTest6();
			productDALTest7();
			productDALTest8();
			productDALTest9();
			productDALTest10();
			productDALTest11();
			productDALTest12();
			productDALTest13();
			productDALTest14();
			productDALTest15();
			productDALTest16();
			productDALTest17();
			productDALTest18();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		ProductDAL.switchToTestFile(false);
		input.close();
	}

	public static void productDALTest1() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 1 - addProductToFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to be correctly added to the file");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		boolean added = ProductDAL.addProductToFile(addProduct);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest2() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 2 - addProductToFile() - Attempt to add empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		Product addProduct = new Product();
		boolean added = ProductDAL.addProductToFile(addProduct);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest3() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 3 - addProductToFile() - Attempt to add a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		boolean added = ProductDAL.addProductToFile(null);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest4() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println(
				"ProductDAL Test 4 - addProductToFile() - Attempt to add product with an ID that is already used");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: no record to be added");
		Product addProduct1 = new Product(1, "Apple", 0.75);
		Product addProduct2 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct1);
		ProductDAL.addProductToFile(addProduct2);
		Product testProduct = new Product(1, "Banana", 0.80);
		boolean added = ProductDAL.addProductToFile(testProduct);
		System.out.println("Added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest5() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 5 - readAllProduct()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: 2 Product objects to be outputed");
		Product addProduct1 = new Product(1, "Apple", 0.75);
		Product addProduct2 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct1);
		ProductDAL.addProductToFile(addProduct2);
		ArrayList<Product> result = ProductDAL.readAllProduct();
		for (Product current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest6() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 6 - readAllProduct() - reading with no records on file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: No records existing on the file");
		System.out.println("Expected Result: No product objects to be outputted");
		ArrayList<Product> result = ProductDAL.readAllProduct();
		for (Product current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest7() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 7 - removeProductFromFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: the record to be removed from the file");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		boolean removed = ProductDAL.removeProductFromFile(addProduct);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest8() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println(
				"ProductDAL Test 8 - removeProductFromFile() - Attempting to remove a record that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		Product testProduct = new Product(2, "Orange", 1.00);
		boolean removed = ProductDAL.removeProductFromFile(testProduct);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest9() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 9 - removeProductFromFile() - Attempting to remove with an empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.removeProductFromFile(new Product());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest10() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 10 - removeProductFromFile() - Attempting to remove with a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		boolean removed = ProductDAL.removeProductFromFile(null);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest11() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 11 - updateProductOnFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: the record to be updated with the correct info");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		Product testProduct = new Product(2, "Orange", 1.00);
		boolean updated = ProductDAL.updateProductOnFile(1, testProduct);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest12() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 12 - updateProductOnFile() - Updating with an ID that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		Product testProduct = new Product(2, "Orange", 1.00);
		boolean updated = ProductDAL.updateProductOnFile(2, testProduct);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest13() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println(
				"ProductDAL Test 13 - updateProductOnFile() - Attempting to update correct ID with empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		boolean updated = ProductDAL.updateProductOnFile(1, new Product());
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest14() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println(
				"ProductDAL Test 14 - updateProductOnFile() - Attempting to update correct ID with a null object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		ProductDAL.addProductToFile(addProduct);
		boolean updated = ProductDAL.updateProductOnFile(1, null);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest15() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 15 - findProductByID()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: The record for Apples to be output");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		Product addProduct1 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.addProductToFile(addProduct1);
		Product foundProduct = ProductDAL.findProductByID(addProduct.getProductId());
		System.out.println("Result: " + foundProduct.toString());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest16() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 16 - findProductByName()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: The record for Apple to be output");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		Product addProduct1 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.addProductToFile(addProduct1);
		Product foundProduct = ProductDAL.findProductByName("Apple");
		System.out.println("Result: " + foundProduct.toString());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest17() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 17 - fileContains()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record already on the file");
		System.out.println("Expected Result: True");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		Product addProduct1 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.addProductToFile(addProduct1);
		boolean contains = ProductDAL.fileContains(addProduct);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest18() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 18 - fileContains() - checking with a record that isnt on the file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record not on the file");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		Product addProduct1 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.addProductToFile(addProduct1);
		Product addProduct2 = new Product(3, "Banana", 0.80);
		boolean contains = ProductDAL.fileContains(addProduct2);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void productDALTest19() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("ProductDAL Test 19 - fileContains() - inputting a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A null value");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		Product addProduct = new Product(1, "Apple", 0.75);
		Product addProduct1 = new Product(2, "Orange", 1.00);
		ProductDAL.addProductToFile(addProduct);
		ProductDAL.addProductToFile(addProduct1);
		boolean contains = ProductDAL.fileContains(null);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void resetBetweenTests() throws IOException {
		// CLASS DATA //
		String fileName = "ProductsTest.csv";
		File productTestFile = new File(fileName);
		boolean hasHeader = true;
		FileWriter fw = new FileWriter(productTestFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");

		// If there are meant to be headers, create them
		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("ProductID,Name,Price");
		}
		pw.close();
	}

	public static void tapEnterToContinue() {
		System.out.print("Press Enter to continue: ");
		input.nextLine();
		System.out.println("Continuing....");
	}
}
