package stockManagementSystemTesting;

import java.io.IOException;

import stockManagementSystem.Product;


public class ProductTestCases {

	public static void main(String[] args) throws IOException {
		try {
			
		
				System.out.println("\n\t\tProduct Test Cases");
				System.out.println("\n\nTC1 - Product construction");
				ProductTestCases.tc1();
			

				System.out.println("\nTC2 - Testing Product toString()");
				ProductTestCases.tc2();

				System.out.println("\nTC3 - Testing getProductName() method");
				ProductTestCases.tc3();

				System.out.println("\nTC4 - Testing getPrice() method");
				ProductTestCases.tc4();

				System.out.println("\nTC5 - Testing setProductName() method");
				ProductTestCases.tc5();

				System.out.println("\nTC6 - Testing setPrice() method");
				ProductTestCases.tc6();
				

		

				
			} catch (IOException ioe) 
			{
				ioe.printStackTrace();
			}

		}

		// Test Constructor
		public static void tc1() throws IOException {
			Product newProd = new Product(1, "Bread", 9.99);
		}

		// Test ToString
		public static void tc2() throws IOException {
			Product newProd = new Product(2, "Milk", 2.49);
			System.out.println(newProd.toString());
		}

		// Test getProductName() method
		public static void tc3() throws IOException {
			Product newProd = new Product(3, "Cheese", 5.99);
			System.out.println("Product Name: " + newProd.getProductName());
		}

		// Test getPrice() method
		public static void tc4() throws IOException {
			Product newProd = new Product(4, "Butter", 3.49);
			System.out.println("Product Price: " + newProd.getPrice());
		}

		// Test setProductName() method
		public static void tc5() throws IOException {
			Product newProd = new Product(5, "Eggs", 1.99);
			System.out.println("Before: " + newProd.getProductName());
			newProd.setProductName("Organic Eggs");
			System.out.println("After: " + newProd.getProductName());
			
		}
		// Test setProductName() method
		public static void tc6() throws IOException {
			Product newProd = new Product(5, "Eggs", 1.99);
			System.out.println("Before: " + newProd.getPrice());
			newProd.setPrice(2.99);
			System.out.println("After: " + newProd.getPrice());
		
		}
}
