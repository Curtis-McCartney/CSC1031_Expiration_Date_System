package stockManagementSystem;

import java.util.Scanner;

/**
 * 
 * 
 * This class defines menus that convey information to the user and obtain input
 * from them. Menu objects act as intermediaries between the User and the
 * System, forming an interface layer and abstracting the inner workings of the
 * code from the user.
 */
public class Menu {
	private String items[];
	private String title;
	private Scanner input;
	
	/**
	 * This constructs a new Menu object and assigns the data passed as parameters
	 * to the corresponding attributes of the Menu.
	 * 
	 * @param title - The title of the menu that will be displayed above the options
	 * @param data  - The options that will be presented to the User
	 */
	public Menu(String title, String data[]) {
		this.title = title;
		copyOptions(data);
		this.input = new Scanner(System.in);
	}
	
	/**
	 * This displays the options stored in the items attribute to the user in a
	 * specific formats
	 */
	private void display() {
		System.out.println(title);
		for(int count=0;count<title.length();count++) {
			System.out.print("+");
		}
		System.out.println();
		for(int option=1; option<=items.length; option++) {
			System.out.println(option + ". " + items[option-1] );
		}
		System.out.println();
	}
	
	/**
	 * Calls the display method and prompts the user for an input. Validation is
	 * applied and the method asks repeatedly until a valid value is given.
	 * 
	 * @return the integer input from the user
	 */
	public int getUserChoice() {
		display();
		int value = 0;
		
		// Dealing with invalid inputs. Ask user again
		while(value > items.length  || value < 1) {
			try {
				System.out.print("Enter Selection: ");
				value = input.nextInt();
				if(value > items.length  || value < 1) {
					System.out.println(value + " isn't a valid command; try again");
				}
			}catch(Exception e) { // Catches any unexpected inputs
				System.out.println("Invalid input - try again.");
			}
			
			input.nextLine(); // Clear scanner of leftover data
		}
		return value;
	}
	
	/**
	 * Updates the items array to contain new options to display to the user. This
	 * allows for more dynamic use (and reuse) of the same Menu objects for similar
	 * tasks.
	 * 
	 * @param options - The new options to display to the user when the display
	 *                method is called
	 */
	public void copyOptions(String options[]) {
		this.items = new String[options.length];
		
		for(int i = 0; i < options.length; i++) {
			items[i] = options[i];
		}
	}
}
