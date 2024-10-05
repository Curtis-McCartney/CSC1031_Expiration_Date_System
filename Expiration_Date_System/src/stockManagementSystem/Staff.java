package stockManagementSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This Class is used to define the details of a Staff Object.
 */
public class Staff {
	private String forename;
	private String surname;
	private String username;
	private String password;
	private boolean lockedOut;
	private boolean isAdmin;

	/**
	 * The constructor of the Staff Class which can be used to instantiate the Staff
	 * Object.
	 * 
	 * @param forename
	 * @param surname
	 * @param password
	 * @param jobType  The variable username is used to uniquely identify the object
	 *                 whenever it is instantiated. It will be created using a
	 *                 combination of the first name and last name of the Staff
	 *                 member.
	 * @throws IOException 
	 */
	public Staff(String forename, String surname, String password, boolean isAdmin) throws IOException {
		this.forename = forename;
		this.surname = surname;
		this.username = createUsername();
		this.password = password;
		this.lockedOut = false;
		this.isAdmin = isAdmin;
	}

	/**
	 * This is a default constructor for the Staff class that will create an empty
	 * Staff Object.
	 */
	public Staff() {
		this.forename = "";
		this.surname = "";
		this.username = "";
		this.password = "";
		this.lockedOut = false;
		this.isAdmin = false;
	}

	/**
	 * This is a constructor that will create a mostly empty Staff Object, but with
	 * fully manually added variables.
	 * 
	 * @param forename
	 * @param surname
	 * @param username
	 * @param password
	 * @param lockedOut
	 * @param isAdmin
	 */
	public Staff(String forename, String surname, String username, String password, boolean lockedOut,
			boolean isAdmin) {
		this.forename = forename;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.lockedOut = lockedOut;
		this.isAdmin = isAdmin;
	}

	// Setter methods

	/**
	 * Sets the Forename of the Staff Object.
	 * 
	 * @param forename
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * Sets the Surname of the Staff Object.
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Sets the Password of the Staff Object.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the LockedOut status of the Staff Object.
	 * 
	 * @param lockedOut
	 */
	public void setLockedOut(boolean lockedOut) {
		this.lockedOut = lockedOut;
	}

	/**
	 * Sets the IsAdmin status of the Staff Object.
	 * 
	 * @param isAdmin
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// Getter methods

	/**
	 * Gets the Forename variable of this Staff Object.
	 * 
	 * @return
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * Gets the Surname variable of this Staff Object.
	 * 
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Gets the Username variable of this Staff Object.
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the Password variable of this Staff Object.
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the LockedOut Status of this Staff Object.
	 * 
	 * @return
	 */
	public boolean getLockedOut() {
		return lockedOut;
	}

	/**
	 * Gets the isAdmin Status of this Staff Object.
	 * 
	 * @return
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * This method is used to create the Username of the Staff Object whenever it is
	 * instantiated. It uses the forename and surname variables to create a
	 * username, and if that username exists within the system, then it adds a
	 * number to the end of the Username.
	 * 
	 * @return - This returns the final, valid Username for the object to be
	 *         uniquely identified with.
	 * @throws IOException 
	 */
	private String createUsername() throws IOException {
		String username = getForename().charAt(0) + getSurname();
		ArrayList<Staff> staffList = StaffDAL.readAllStaff();
		int num = 1;
		for (int i = 0; i < staffList.size(); i++) {
			Staff current = staffList.get(i);
			// Compare the first and last names of all staff members against
			// the forename and surname of the Staff Object to be created.
			if(current.getForename().equals(getForename()) && current.getSurname().equals(getSurname())) {
				// Add a number to the end of Username
				while((username + num).equals(current.getUsername())) {
					num++;
				}
			}
		}
		return username + num;
	}

	/**
	 * This method is used to override the original toString() method within Java.
	 * This is used to output the wanted String whenever a Staff object is printed
	 * to the console.
	 */
	@Override
	public String toString() {
		return this.username + " is the account that belongs to " + this.forename + " " + this.surname + ".";
	}

	/**
	 * If every value in this Staff Object is equal to the empty constructor values
	 * for these variables, then return true. Else, return false.
	 * @return - This returns true if the Staff Object is "Empty".
	 */
	public boolean isEmpty() {
		if (getForename() == "" && getSurname() == "" && getUsername() == "" && getPassword() == ""
				&& getLockedOut() == false && getIsAdmin() == false) {
			return true;
		}
		return false;
	}

	/** If every value in this Staff Object is equal to every value in the comparedStaff Staff Object, then return true.
	 * @param comparedStaff - This holds the Staff Object that is being compared to the current Staff Object.
	 * @return - Returns true if this Staff Object is equal to the comparedStaff Staff Object.
	 */
	public boolean equals(Staff comparedStaff) {
		if (getForename().equals(comparedStaff.getForename()) && getSurname().equals(comparedStaff.getSurname())
				&& getUsername().equals(comparedStaff.getUsername()) && getPassword().equals(comparedStaff.getPassword())
				&& getLockedOut() == comparedStaff.getLockedOut() && getIsAdmin() == comparedStaff.getIsAdmin()) {
			return true;
		}
		return false;
	}
}
