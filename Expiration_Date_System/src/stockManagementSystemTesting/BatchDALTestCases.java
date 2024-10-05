package stockManagementSystemTesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import stockManagementSystem.BatchDAL;
import stockManagementSystem.ProductBatch;

public class BatchDALTestCases {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		BatchDAL.switchToTestFile(true);
		try {
		batchDALTest1();
		batchDALTest2();
		batchDALTest3();
		batchDALTest4();
		batchDALTest5();
		batchDALTest6();
		batchDALTest6();
		batchDALTest7();
		batchDALTest8();
		batchDALTest9();
		batchDALTest10();
		batchDALTest11();
		batchDALTest12();
		batchDALTest13();
		batchDALTest14();
		batchDALTest15();
		batchDALTest16();
		batchDALTest17();
		batchDALTest18();
		batchDALTest19();
		batchDALTest20();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		BatchDAL.switchToTestFile(false);
		input.close();
	}

	public static void batchDALTest1() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 1 - addBatchToFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to be correctly added to the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		boolean added = BatchDAL.addBatchToFile(addBatch);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest2() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 2 - addBatchToFile() - Attempt to add empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch();
		boolean added = BatchDAL.addBatchToFile(addBatch);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest3() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 3 - addBatchToFile() - Attempt to add a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 0 records existing on the file");
		System.out.println("Expected Result: the record to not be added to the file");
		System.out.println("Executing...");
		boolean added = BatchDAL.addBatchToFile(null);
		System.out.println("Record added?: " + added);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest4() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 4 - readAllBatches()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: 2 ProductBatch objects to be outputed");
		ProductBatch addBatch1 = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch2 = new ProductBatch(2, 5, new Date(124, 8, 02), false);
		BatchDAL.addBatchToFile(addBatch1);
		BatchDAL.addBatchToFile(addBatch2);
		ArrayList<ProductBatch> result = BatchDAL.readAllBatches();
		for(ProductBatch current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest5() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 5 - readAllBatches() - reading with no records on file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: No productBatch objects to be outputted");
		ArrayList<ProductBatch> result = BatchDAL.readAllBatches();
		for(ProductBatch current : result) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}

	public static void batchDALTest6() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 6 - removeBatchFromFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: the record to be removed from the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		boolean removed = BatchDAL.removeBatchFromFile(addBatch);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest7() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 7 - removeBatchFromFile() - Attempting to remove a record that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		ProductBatch testProductBatch = new ProductBatch(2, 5, new Date(124, 8, 2), false);
		boolean removed = BatchDAL.removeBatchFromFile(testProductBatch);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest8() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 8 - removeBatchFromFile() - Attempting to remove with an empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.removeBatchFromFile(new ProductBatch());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest9() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 9 - removeBatchFromFile() - Attempting to remove with a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 records existing on the file");
		System.out.println("Expected Result: no record to be removed from the file");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		boolean removed = BatchDAL.removeBatchFromFile(null);
		System.out.println("Removed?: " + removed);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest10() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 10 - updateBatchOnFile()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: the record to be updated with the correct info");
		System.out.println("Executing...");
		Date day = new Date(124, 7, 13);
		ProductBatch addBatch = new ProductBatch(1, 2, day, false);
		BatchDAL.addBatchToFile(addBatch);
		ProductBatch testProductBatch = new ProductBatch(1, 3, new Date(124, 7, 13), false);
		boolean updated = BatchDAL.updateBatchOnFile(1, day, testProductBatch);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest11() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 11 - updateBatchOnFile() - Updating with an ID that doesnt exist");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Date day = new Date(124, 7, 13);
		ProductBatch addBatch = new ProductBatch(1, 2, day, false);		BatchDAL.addBatchToFile(addBatch);
		ProductBatch testProductBatch = new ProductBatch(1, 5, day, false);
		boolean updated = BatchDAL.updateBatchOnFile(3, day, testProductBatch);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest12() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 12 - updateBatchOnFile() - Updating with a expiry date that doesnt exist for given ID");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Date day = new Date(124, 7, 13);
		ProductBatch addBatch = new ProductBatch(1, 2, day, false);		BatchDAL.addBatchToFile(addBatch);
		ProductBatch testProductBatch = new ProductBatch(1, 5, day, false);
		boolean updated = BatchDAL.updateBatchOnFile(1, new Date(124, 3, 4), testProductBatch);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest13() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 13 - updateBatchOnFile() - Attempting to update correct ID and Expiry with empty object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Date day = new Date(124, 7, 13);
		ProductBatch addBatch = new ProductBatch(1, 2, day, false);
		BatchDAL.addBatchToFile(addBatch);
		boolean updated = BatchDAL.updateBatchOnFile(1, day, new ProductBatch());
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest14() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 14 - updateBatchOnFile() - Attempting to update correct ID and Expiry with a null object");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 1 record existing on the file");
		System.out.println("Expected Result: no record to be updated");
		System.out.println("Executing...");
		Date day = new Date(124, 7, 13);
		ProductBatch addBatch = new ProductBatch(1, 2, day, false);		BatchDAL.addBatchToFile(addBatch);
		boolean updated = BatchDAL.updateBatchOnFile(1, day, null);
		System.out.println("Updated?: " + updated);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest15() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 15 - findBatchesByProductID()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: The record for apples batch to be output");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		ArrayList<ProductBatch> foundProductBatch = BatchDAL.findBatchesByProductID(1);
		System.out.println("Result: " + foundProductBatch.toString());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest16() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 16 - findBatchesByProductID() - Input an ID that none of the records on the file use");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: No records to be output");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		ArrayList<ProductBatch> foundProductBatches = BatchDAL.findBatchesByProductID(5);
		System.out.println("Result: ");
		for(ProductBatch current : foundProductBatches) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest17() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 17 - findBatchesByExpiryDate()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: The record for apple batch to be output");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		ArrayList<ProductBatch> foundProductBatches = BatchDAL.findBatchesByExpiryDate(new Date(124, 7, 13));
		System.out.println("Result: ");
		for(ProductBatch current : foundProductBatches) {
			System.out.println(current);
		}
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest18() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 18 - findBatchesByExpiryDate() - searching with a null date");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Expected Result: An empty record to be output");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		ArrayList<ProductBatch> foundProductBatch = BatchDAL.findBatchesByExpiryDate(null);
		System.out.println("List Returned. Is Empty List?: " + foundProductBatch.isEmpty());
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest19() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 19 - fileContains()");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record already on the file");
		System.out.println("Expected Result: True");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		boolean contains = BatchDAL.fileContains(addBatch);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest20() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 20 - fileContains() - checking with a record that isnt on the file");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A record not on the file");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		ProductBatch addBatch2 = new ProductBatch(3, 6, new Date(124, 5, 23), true);
		boolean contains = BatchDAL.fileContains(addBatch2);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void batchDALTest22() throws IOException {
		resetBetweenTests();
		System.out.println("==================================================================================");
		System.out.println("BatchDAL Test 22 - fileContains() - inputting a null value");
		System.out.println("==================================================================================");
		System.out.println("Preconditions: 2 records existing on the file");
		System.out.println("Input: A null value");
		System.out.println("Expected Result: False");
		System.out.println("Executing...");
		ProductBatch addBatch = new ProductBatch(1, 2, new Date(124, 7, 13), false);
		ProductBatch addBatch1 = new ProductBatch(2, 5, new Date(124, 7, 13), false);
		BatchDAL.addBatchToFile(addBatch);
		BatchDAL.addBatchToFile(addBatch1);
		boolean contains = BatchDAL.fileContains(null);
		System.out.println("Record Present?: " + contains);
		System.out.println("Program executed, check results in file before continuing");
		tapEnterToContinue();
	}
	
	public static void resetBetweenTests() throws IOException {
		// CLASS DATA //
		String fileName = "BatchesTest.csv";
		File productBatchTestFile = new File(fileName);
		boolean hasHeader = true;
		FileWriter fw = new FileWriter(productBatchTestFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");
		
		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("ProductID,Quantity,ExpiryDate,IsDiscounted");
		}
		pw.close();
	}

	public static void tapEnterToContinue() {
		System.out.print("Press Enter to continue: ");
		input.nextLine();
		System.out.println("Continuing....");
	}
}
