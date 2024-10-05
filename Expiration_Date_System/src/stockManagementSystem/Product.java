package stockManagementSystem;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Objects;


/**
 * This Class is used to define the details of a product
 *         Object.
 */
public class Product {
		
		private int productId;
		private String productName;
		private double price;
		private static HashMap<Integer, Product> products = new HashMap<>();



		private static int globalDiscount = 10;
		private static int nextId;

	
		   /** The constructor of the product class which will be used to construct products
	     * The product is used to uniquely identify each object
	     * It will be instantiated with its price and name 
	     */
		
		public Product(int productID, String productName, double price)
		{
			this.productId = productID;
		//	nextId += 1;
			this.productName = productName;
			this.price = price;
			products.put(productID, this);
		}
		

		//default constructor creates empty object
		public Product()
		{
		    this.productId = -1; // Set to -1 for invalid ID
			this.productName = "";
			this.price = 0;
			
		}
		
		public static void addProduct()
		{
			   Scanner scanner = new Scanner(System.in);

		    
		        int productId = nextId;
		        nextId += 1;

		        System.out.println("Enter product name:");
		        String productName = scanner.nextLine();

		        System.out.println("Enter product price:");
		        double price = scanner.nextDouble();

		        Product newProduct = new Product(productId, productName, price);
		        try {
					ProductDAL.addProductToFile(newProduct);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        System.out.println("Product added successfully!");
		    	readProductsFromFile(); // Add the product to the HashMap
		
			
		}
		
		public static void editProduct()
		{
			  Scanner scanner = new Scanner(System.in);

			  System.out.println("Do you want to select product to edit by product ID or product name?");
		        System.out.println("1. Product ID");
		        System.out.println("2. Product Name");
		        int option = scanner.nextInt();
		        scanner.nextLine(); // Consume newline character

		        Product productToEdit = null;
		        if (option == 1) {
		            System.out.println("Enter product ID to select:");
		            int idToEdit = scanner.nextInt();
		            scanner.nextLine(); // Consume newline character
		            productToEdit = Product.getProducts().get(idToEdit);
		        } else if (option == 2) {
		            System.out.println("Enter product name to select:");
		            String nameToEdit = scanner.nextLine();
		            productToEdit = Product.searchProductByName(nameToEdit);
		           
		        } else {
		            System.out.println("Invalid option!");
		            return;
		        }

		        if (productToEdit == null) {
		            System.out.println("Product not found!");
		            return;
		        }

		        System.out.println("Enter new product name (or leave blank to keep current name):");
		        String newName = scanner.nextLine();
		        if (!newName.isBlank()) {
		            productToEdit.setProductName(newName);
		        }

		        System.out.println("Enter new product price (or enter 0 to keep current price):");
		        double newPrice = scanner.nextDouble();
		        if (newPrice != 0) {
		            productToEdit.setPrice(newPrice);
		        }
		        int idToEdit = productToEdit.productId;
		        
		        try {
					ProductDAL.updateProductOnFile(idToEdit, productToEdit);
					readProductsFromFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        System.out.println("Product edited successfully!");
			
			
			
		}
		
		 public static void viewProduct() {
		        Scanner scanner = new Scanner(System.in);

		        System.out.println("Do you want to search by product ID or product name?");
		        System.out.println("1. Product ID");
		        System.out.println("2. Product Name");
		        int option = scanner.nextInt();
		        scanner.nextLine(); // Consume newline character

		        if (option == 1) {
		            System.out.println("Enter product ID:");
		            int idToView = scanner.nextInt();
		            Product productToView = Product.getProducts().get(idToView);
		            if (productToView != null) {
		                System.out.println(productToView);
		            } else {
		                System.out.println("Product not found!");
		            }
		        } else if (option == 2) {
		            System.out.println("Enter product name:");
		            String nameToView = scanner.nextLine();
		            Product productToView = Product.searchProductByName(nameToView);
		            if (productToView != null) {
		                System.out.println(productToView);
		            } else {
		                System.out.println("Product not found!");
		            }
		        } else {
		            System.out.println("Invalid option!");
		        }
		    }

		 
		public static void readProductsFromFile()
		{
			int nextId = 1;
			ArrayList<Product> allProducts = null;
			try {
				allProducts = ProductDAL.readAllProduct();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			for(int i = 0; i < allProducts.size(); i ++)
			{
				products.put(allProducts.get(i).productId, allProducts.get(i));
				nextId += 1;
					
			}
			
			setNextId(nextId);
			
			
		}
		
		
		public static int getNextId() {
			return nextId;
		}


		public static void setNextId(int nextId) {
			Product.nextId = nextId;
		}


		//.equals() override to compare two Products
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Product otherProduct = (Product) obj;
			return productId == otherProduct.productId &&
				Double.compare(otherProduct.price, price) == 0 &&
				Objects.equals(productName, otherProduct.productName);
		}
		
		
		//  toString method outputs string listing all product info
		
		public String toString()
		{
			String str = "Product ID - "+ productId + "\nProduct Name - " + productName + "\nBase Price - " + price;
			return str;
			
		}
	
	
		public static void changeGlobalDiscount()
		{
			
			Scanner in = new Scanner(System.in);
			System.out.print("Enter new discount percentage : ");
			setGlobalDiscount(in.nextInt());
		}
		/*
		 * Search method finds products via name
		 */
		
		public static Product searchProductByName(String name) {
        for (HashMap.Entry<Integer, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            if (product.productName.equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; // Product with the given name not found
    }
    

		//Getters and setters for Product class

		public static void setProducts(HashMap<Integer, Product> products) {
			Product.products = products;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getProductId() {
			return productId;
		}

		public static HashMap<Integer, Product> getProducts() {
			return products;
		}
	
	    public boolean isEmpty()
	    {
		    if (productId == -1 && productName == "" && price == 0)
				
				{return true;}
		
			else{return false;}
	    	
	    }


		public static int getGlobalDiscount() {
			return globalDiscount;
		}


		public static void setGlobalDiscount(int globalDiscount) {
			Product.globalDiscount = globalDiscount;
		}
		
}