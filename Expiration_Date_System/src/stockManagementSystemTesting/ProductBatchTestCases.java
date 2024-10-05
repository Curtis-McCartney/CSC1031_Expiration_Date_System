package stockManagementSystemTesting;

import java.io.IOException;
import java.util.Date;
import stockManagementSystem.ProductBatch;


public class ProductBatchTestCases {

	public static void main(String[] args) throws IOException {
	try {
		  System.out.println("\n\t\tProductBatch Test Cases");

          System.out.println("\nTC1 - ProductBatch construction");
          ProductBatchTestCases.tc1();

          System.out.println("\nTC2 - Testing isDiscounted() method");
          ProductBatchTestCases.tc2();
          
          System.out.println("\nTC3 - Testing setDiscounted() method");
          ProductBatchTestCases.tc3();

          System.out.println("\nTC4 - Testing getQuantity() method");
          ProductBatchTestCases.tc4();

          System.out.println("\nTC5 - Testing getExpiryDate() method");
          ProductBatchTestCases.tc5();

          System.out.println("\nTC6 - Testing setQuantity() method");
          ProductBatchTestCases.tc6();
			
			
			
			
			
		} catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}

	}

	// Test Constructor
	public static void tc1() throws IOException {
	      Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(1, 100, expiryDate);
	    }

	    // Test isDiscounted()
	    public static void tc2() throws IOException {
	        Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(2, 50, expiryDate);
			System.out.println("Batch discount status : " + batch.isDiscounted());
	    }
	    
	    // Test setDiscounted()
	    public static void tc3() throws IOException {
	        Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(5, 100, expiryDate);
	        System.out.println("Before Discount: " + batch.isDiscounted());
	        batch.setDiscounted(true);
	        System.out.println("After Discount: " + batch.isDiscounted());
	    }


	    // Test getQuantity() method
	    public static void tc4() throws IOException {
	        Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(3, 200, expiryDate);
	        System.out.println("Batch Quantity: " + batch.getQuantity());
	    }

	    // Test getExpiryDate() method
	    public static void tc5() throws IOException {
	        Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(4, 150, expiryDate);
	        System.out.println("Batch Expiry Date: " + batch.getExpiryDate());
	    }

	    // Test setQuantity() method
	    public static void tc6() throws IOException {
	        Date expiryDate = new Date(2025, 4, 30); // Example expiry date
	        ProductBatch batch = new ProductBatch(5, 100, expiryDate);
	        System.out.println("Before Quantity: " + batch.getQuantity());
	        batch.setQuantity(120);
	        System.out.println("After Quantity: " + batch.getQuantity());
	    }

	

}
