package stockManagementSystem;

import java.util.Scanner;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * This Class is used to define the details of a product batch Object.
 */
public class ProductBatch {
		
			


		private int productId;
		private int quantity;
		private Date expiryDate;
		private boolean isDiscounted;
		private static ArrayList<ProductBatch> stock = new ArrayList<ProductBatch>();

		 /* The constructor of the product batch class which will be used to construct batches
	     * batches are not unique and only contain a quantity of a product expiring on a certain date
	     * It will be instantiated with a productId, quantity and expiry date
	     */
		
			public ProductBatch(int productId, int quantity, Date expiryDate)
		{
			this.productId = productId;
			this.quantity = quantity;
			this.expiryDate = expiryDate;
			this.isDiscounted = false;

		
		}

			public ProductBatch(int productId, int quantity, Date expiryDate, boolean isDiscounted)
		{
			this.productId = productId;
			this.quantity = quantity;
			this.expiryDate = expiryDate;
			this.isDiscounted = isDiscounted;
	
		}

			//default constructor creates empty productBatch
			public ProductBatch()
		{
		        this.productId = -1; // Set to -1 for invalid ID
		        this.quantity = 0;
		        this.expiryDate = new Date(0); // Set to earliest date
		        this.isDiscounted = false;
				
		}
			
			@SuppressWarnings("deprecation")
			public static void newBatch()
			{
				Scanner in = new Scanner(System.in);
				System.out.println("Please select \n1. Enter product ID\n2. Search for product name");
				
		boolean complete = false;
			while (!complete) 
			{
				switch(in.next())
				{
				
				case "1" :
				/*
				 * Enter products details to create a new batch
				 * directly inputting product id
				 * may refactor and deal with user input in a seperate class but testing this for now
				 * 
				 */
					System.out.print("Enter Product ID : ");
					int id = in.nextInt();
					System.out.print("Enter Quantity : ");
					int quant = in.nextInt();
					System.out.print("Enter Expiry Date\nDay :  ");
					int day = in.nextInt();
					System.out.print("Month : ");
					int month = in.nextInt();
					System.out.print("Year : ");
					int year = in.nextInt();
					Date expDate = new Date();
					expDate.setDate(day);
					expDate.setMonth(month-1);
					expDate.setYear(year-1900);
					
					addBatch(id, quant, expDate);
					System.out.println("New batch has been added");
					complete = true;
					break;
				
				case "2" :
	
		
			boolean found = false;
		while (!found) 
		{
				System.out.print("Please enter product name : ");
				String name = in.next();
				Product result = Product.searchProductByName(name);		
				if (result != null) {
			    		
					System.out.println("Product found: " + result.toString());
				    found = true;
				    id = result.getProductId();			   	
				   	System.out.print("Enter Quantity : ");
				   	quant = in.nextInt();
				   	System.out.print("Enter Expiry Date\nDay :  ");
				   	day = in.nextInt();
				   	System.out.print("Month : ");
				   	month = in.nextInt();
				   	System.out.print("Year : ");
				   	year = in.nextInt();
					expDate = new Date();
					expDate.setDate(day);
					expDate.setMonth(month-1);
					expDate.setYear(year-1900);
			
					addBatch(id, quant, expDate);
					System.out.println("New batch has been added");
					   
				} 
				
			else	{			
								System.out.println("Product not found."); 			
					}
			}			
				complete = true;		
					break;
					
					default : //loop method until user decides to either enter id or search name
								System.out.print("Please enter either 1 or 2 : ");
								
					}
					
		}
			
			}

			
			//.equals() override compares two productBatches
			public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			ProductBatch otherBatch = (ProductBatch) obj;
			return productId == otherBatch.productId &&
				quantity == otherBatch.quantity &&
				isDiscounted == otherBatch.isDiscounted &&
				Objects.equals(expiryDate, otherBatch.expiryDate);

		}


			/*
			 * method creates new batch and adds to arrlist storing all batch/stock info
			 * however in the case that the product and expiry date is identical, the quantity is
			 * added to original productBatch
			 */
			public static void addBatch(int productId, int quantity, Date expiryDate)
		{
				
			boolean batchExists = false;
	        for(int i = 0; i < stock.size(); i++)
	        {
	        	if(stock.get(i).productId == productId && stock.get(i).expiryDate.getDay() == expiryDate.getDay() && stock.get(i).expiryDate.getMonth() == expiryDate.getMonth() && stock.get(i).expiryDate.getYear() == expiryDate.getYear())
	        	{
	        		stock.get(i).quantity += quantity;
	        		  try {
							BatchDAL.updateBatchOnFile(stock.get(i).productId, stock.get(i).expiryDate, stock.get(i));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		
	        		batchExists = true;
	        		break;
	        		
	        	}
	         
	        }	
	        if(!batchExists)
	        	{	
	        	
	        	ProductBatch newBatch = new ProductBatch(productId, quantity, expiryDate);
	        	try {
					BatchDAL.addBatchToFile(newBatch);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	readBatchesFromFile();
	        	}
	        	
	        
		}
			
			
			public static void viewBatch(int productId, Date expiryDate)
			{
				for(int i = 0; i < stock.size(); i++)	
				{
					if(stock.get(i).productId == productId && stock.get(i).expiryDate == expiryDate)
					{
						System.out.println(stock.get(i).toString());
						return;
					}
				
					
				}
				System.out.println("Batch not found");
		        
			}
			
			public static void readBatchesFromFile()
			{
				
			try {
				stock = BatchDAL.readAllBatches();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        
			}
			
			public static void batchSearch()
			{
		        Scanner scanner = new Scanner(System.in);

		        System.out.println("Enter product ID to view batch:");
		        int productId = scanner.nextInt();

		        // Search for the product ID
		        System.out.println("Enter expiry date (format: DD MM YYYY):");
		        System.out.print("Day : ");
		        int day = scanner.nextInt();
		        System.out.print("Month : ");
		        int month = scanner.nextInt();
		        System.out.print("Year : ");
		        int year = scanner.nextInt();
		        Date expiryDate = new Date(year - 1900, month - 1, day); // Year starts from 1900, month is zero-based
		        expiryDate.setTime(0);
		    
		            viewBatch(productId, expiryDate); // Call viewBatch method to display the details of the batch
		       
			}
		    
			   public static void editBatch() {
			        Scanner scanner = new Scanner(System.in);

			        System.out.println("Enter product ID to select batch:");
			        int productId = scanner.nextInt();

			        // Search for the product ID
			        System.out.println("Enter expiry date (format: DD MM YYYY):");
			        System.out.print("Day : ");
			        int day = scanner.nextInt();
			        System.out.print("Month : ");
			        int month = scanner.nextInt();
			        System.out.print("Year : ");
			        int year = scanner.nextInt();
			        Date expiryDate = new Date(year - 1900, month - 1, day); // Year starts from 1900, month is zero-based
			
			      
			        boolean found = false;
			        for(int i = 0; i < stock.size(); i++) {
			   
			        	if(stock.get(i).productId == productId && stock.get(i).expiryDate.equals(expiryDate))
			            {
			                System.out.println("Current quantity: " + stock.get(i).quantity);
			                System.out.println("Current discounted status: " + stock.get(i).isDiscounted);
			                System.out.println("Enter new quantity:");
			                int newQuantity = scanner.nextInt();
			                stock.get(i).quantity = newQuantity;

			                System.out.println("Set discounted status? (true/false)");
			                boolean newDiscounted = scanner.nextBoolean();
			                stock.get(i).isDiscounted = newDiscounted;
			                	
			                
			                
			                try {
								BatchDAL.updateBatchOnFile(stock.get(i).productId, stock.get(i).expiryDate, stock.get(i));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	readBatchesFromFile();
			                System.out.println("Batch edited successfully!");
			                found = true;
			                break;
			            }
			        }
			        if (!found) {
			            System.out.println("Batch not found.");
			        }
			    }
			
			public static int countStockofanitem(int id)
		  
			{
		    int count = 0;
		    	for(int i = 0; i<stock.size();i++)
		    	{
		    		if(stock.get(i).getProductId() == id)
		    		count += stock.get(i).getQuantity();
		    	}
		    		return count;
		    }
			
			
			// toString method outputs all details of a batch
			public String toString()
		{
				
			Product prod = Product.getProducts().get(productId);
			
	
				
			String str = "Product ID - "+ productId + "\tProduct Name - " + prod.getProductName();
			str += "\nQuantity - " + quantity + "\nExpiry date - " + expiryDate;
			if(isDiscounted == true)
			{
				str += "\nPrice per unit = " + (prod.getPrice() / 100) * (100 - Product.getGlobalDiscount()) + "\n" + Product.getGlobalDiscount() + "% discount applied\n";
			}
			else
			{
			
				str += "\nPrice per unit = " + prod.getPrice();
			}
			return str;
			
		}

			/*
			 * Getters and setters for ProductBatch class
			 * 
			 * 
			 * 
			 */
			public int getQuantity() {
				return quantity;
			}


			public void setQuantity(int quantity) {
				this.quantity = quantity;
			}


			public Date getExpiryDate() {
				return expiryDate;
			}


			public void setExpiryDate(Date expiryDate) {
				this.expiryDate = expiryDate;
			}


			public boolean isDiscounted() {
				return isDiscounted;
			}


			public void setDiscounted(boolean isDiscounted) {
				this.isDiscounted = isDiscounted;
			}


			public int getProductId() {
				return productId;
			}
			
		    public static ArrayList<ProductBatch> getStock() {
		        return stock;
		    }

			public boolean isEmpty()
			{
			if (productId == -1 && quantity == 0 && expiryDate.equals(new Date(0)) && isDiscounted == false)
				
				{return true;}
		
			else{return false;}
				
			}

}

