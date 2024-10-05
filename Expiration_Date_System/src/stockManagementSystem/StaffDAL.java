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
 *         This class handles reading/writing to the Staff.csv file and applies
 *         a level of abstraction when it is used. It contains methods to make
 *         accessing the file simple.
 *         
 */
public abstract class StaffDAL {

	// CLASS DATA //
	private static String fileName = "Staff.csv";
	private static File staffFile = new File(fileName);
	private static boolean hasHeader = true;

	// METHODS //
	/**
	 * Reads in all of the records on the staff file and returns them as an
	 * arraylist of Staff objects
	 * 
	 * @return an arraylist of all the staff on the file
	 * @throws IOException
	 */
	public static ArrayList<Staff> readAllStaff() throws IOException {
		ArrayList<Staff> staffMembers = new ArrayList<Staff>();

		try {
			FileReader fr = new FileReader(staffFile);
			BufferedReader br = new BufferedReader(fr);

			// Skip the headers if there are any
			if (hasHeader) {
				br.readLine();
			}

			// Look at each line and construct a staff object from each one
			String currentLine = br.readLine();
			while (currentLine != null) {
				String[] lineData = currentLine.split(",");

				// Only add a new staff member if all 6 attributes are present on the file
				if (lineData.length == 6) {
					// Convert the "true"/"false" read from the csv file into booleans for the
					// lockedOut and isAdmin attributes
					boolean lockedOut;
					if (lineData[4].equals("true")) {
						lockedOut = true;
					} else {
						lockedOut = false;
					}

					boolean isAdmin;
					if (lineData[5].equals("true")) {
						isAdmin = true;
					} else {
						isAdmin = false;
					}

					// Instantiate a Staff object and add it to the arraylist to be returned
					Staff obtainedStaff = new Staff(lineData[0], lineData[1], lineData[2], lineData[3], lockedOut,
							isAdmin);
					staffMembers.add(obtainedStaff);
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
		return staffMembers;
	}

	/**
	 * Adds a record of the inputed Staff object to the file
	 * 
	 * @param staffToAdd - The Staff object to be added to the file
	 * @return True if the record was added correctly and False if the record was
	 *         not added
	 * @throws IOException
	 */
	public static boolean addStaffToFile(Staff staffToAdd) throws IOException {
		if (staffToAdd == null || staffToAdd.isEmpty()) {
			return false;
		} else if (isUsernameFree(staffToAdd.getUsername())){ // If username already exists on file, do not add the new staff member
			try {
				FileWriter fw = new FileWriter(staffFile, true);
				PrintWriter pw = new PrintWriter(fw);

				// If no file exists, create a new one
				if (!staffFile.exists()) {
					staffFile.createNewFile();
					if (hasHeader) {
						pw.println("Forename,Surname,Username,Password,LockedOut,IsAdmin");
					}
				}

				// Write the data from inputed Staff object to a record on the file
				String recordToAdd = staffToAdd.getForename() + ",";
				recordToAdd += staffToAdd.getSurname() + ",";
				recordToAdd += staffToAdd.getUsername() + ",";
				recordToAdd += staffToAdd.getPassword() + ",";
				recordToAdd += staffToAdd.getLockedOut() + ",";
				recordToAdd += staffToAdd.getIsAdmin();
				pw.println(recordToAdd);

				pw.close();
				return true;
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Removes a specific staff object from the file. The data of the staff entered
	 * must match the data of a record on the file exactly or it wont be deleted.
	 * 
	 * @param staffToRemove - The Staff member to be removed from the file
	 * @return True if a record was removed from the file, False if not
	 * @throws IOException
	 */
	public static boolean removeStaffFromFile(Staff staffToRemove) throws IOException {
		if (staffToRemove == null || staffToRemove.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<Staff> staffMembers = readAllStaff();

				if (!staffMembers.isEmpty()) {
					// Finding Staff object that needs to be deleted and rewriting all of the others
					// to the file
					boolean found = false;
					int i = 0;
					while (!found && i < staffMembers.size()) {
						Staff current = staffMembers.get(i);

						// Check to see if the Staff object is the one to be deleted.
						if (current.equals(staffToRemove)) {
							staffMembers.remove(current);
							found = true;
						}
						i++;
					}

					// If the record was deleted, update the file with the new data
					if (found) {
						rewriteFile(staffMembers);
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
	 * Updates the data of a specific record on the file with the inputted data
	 * 
	 * @param usernameToUpdate - The username of the record to update
	 * @param updatedStaffInfo - The new data of the record
	 * @return True if the record was updated and False if not
	 * @throws IOException
	 */
	public static boolean updateStaffOnFile(String usernameToUpdate, Staff updatedStaffInfo) throws IOException {
		if (updatedStaffInfo == null || updatedStaffInfo.isEmpty()) {
			return false;
		} else {
			try {
				ArrayList<Staff> staffMembers = readAllStaff();

				// Check the file isnt empty
				if (!staffMembers.isEmpty()) {
					// If the username of the staff is going to be changed, the method ends if there
					// is already a user with that username on the file (excluding the one being
					// updated)
					if (!usernameToUpdate.equals(updatedStaffInfo.getUsername())
							&& !findStaffByUsername(updatedStaffInfo.getUsername()).isEmpty()) {
						return false;
					}

					// Finding Staff object that needs to be updated and rewriting the file
					boolean found = false;
					int i = 0;
					while (!found && i < staffMembers.size()) {
						Staff current = staffMembers.get(i);

						// Check to see if the Staff object is the one to be updated
						if (current.getUsername().equals(usernameToUpdate)) {
							staffMembers.set(i, updatedStaffInfo);
							found = true;
						}
						i++;
					}

					// If the record has been found, update the file with the new data
					if (found) {
						rewriteFile(staffMembers);
						return true;
					}
				} else {
					System.out.println("Hello World!");
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
	 * Searches the records on the Staff file for the one that matches the username
	 * inputed
	 * 
	 * @param username - The username to search for
	 * @return a Staff object containing the data of the record found, and an empty
	 *         staff object if no Staff with the inputed username exists
	 * @throws IOException
	 */
	public static Staff findStaffByUsername(String username) throws IOException {
		Staff locatedStaff = new Staff(); // Initialised to reference an empty Staff object

		if (username != null && !username.equals("")) {
			try {
				FileReader fr = new FileReader(staffFile);
				BufferedReader br = new BufferedReader(fr);

				// Skip the headers if there are any
				if (hasHeader) {
					br.readLine();
				}

				// Look at each line and construct a staff object from each one
				boolean found = false;
				String currentLine = br.readLine(); // Obtain the first line of data
				while (currentLine != null && found == false) {
					String[] lineData = currentLine.split(",");

					// Skip to next record if username does not match inputed username or current
					// line has incomplete data
					if (lineData.length == 6 && lineData[2].equals(username)) {

						// Convert the "true"/"false" read from the csv file into booleans for the
						// lockedOut and isAdmin attributes
						boolean lockedOut;
						if (lineData[4].equals("true")) {
							lockedOut = true;
						} else {
							lockedOut = false;
						}

						boolean isAdmin;
						if (lineData[5].equals("true")) {
							isAdmin = true;
						} else {
							isAdmin = false;
						}

						// Return a staff object with the data read from the file
						locatedStaff = new Staff(lineData[0], lineData[1], lineData[2], lineData[3], lockedOut,
								isAdmin);
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
		return locatedStaff;
	}

	/**
	 * Checks to see if the inputted Staff object is present on the file
	 * 
	 * @param staffToSearch - The Staff to search for
	 * @return True if the file contains the staff member, False if not
	 * @throws IOException
	 */
	public static boolean fileContains(Staff staffToSearch) throws IOException {
		if (staffToSearch != null && !staffToSearch.isEmpty()) {
			try {
				Staff foundStaff = findStaffByUsername(staffToSearch.getUsername());
				if (staffToSearch.equals(foundStaff)) {
					return true;
				}
			} catch (FileNotFoundException fnfe) {
				System.out.println(fileName + " could not be found");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isUsernameFree(String username) throws IOException{
		if(findStaffByUsername(username).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Rewrites the file and populates it with the contents of the arraylist passed
	 * as the parameter
	 * 
	 * @param fileContents - The staff objects to rewrite the file with
	 * @throws IOException
	 */
 	private static void rewriteFile(ArrayList<Staff> fileContents) throws IOException {
		FileWriter fw = new FileWriter(staffFile); // The file will be overwritten with a new one
		PrintWriter pw = new PrintWriter(fw);

		// Clear file
		pw.print("");

		// If there are meant to be headers, create them
		if (hasHeader) {
			pw.println("Forename,Surname,Username,Password,LockedOut,IsAdmin");
		}
		pw.close(); // Closing print writer as is no longer needed (addStaffToFile() will create its
					// own)

		// Write fileContents to file
		for (Staff current : fileContents) {
			addStaffToFile(current);
		}
	}

	/**
	 * Method used for testing only. Points the StaffDAL to a testing csv file so
	 * that the actual Staff.csv does not have its data changed or damaged
	 * 
	 * @param testing - If true, use StaffTest.csv, if false, use actual Staff.csv
	 *                file
	 */
	public static void switchToTestFile(boolean testing) {
		if (testing) {
			fileName = "StaffTest.csv";
			staffFile = new File(fileName);
		} else {
			fileName = "Staff.csv";
			staffFile = new File(fileName);
		}
	}
}
