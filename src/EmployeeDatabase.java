/*
 * Kasen Teoh
 * Employee Database
 * A hashtable populated with linked list
 * Search, remove, or add using a binary search tree
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EmployeeDatabase {

	static Scanner in = new Scanner(System.in);
	static String filename = "testEmployees.txt";
	static int sizeOfHashTable = 0;

	public static void main(String[] args)  throws IOException {
		// TODO Auto-generated method stub
		int count = 0; 		
		File data = new File(filename); 

		// Create a lineCounter scanner to count number 
		// of lines to decide hash table size 
	    Scanner lineCounter = new Scanner(data);

		while (lineCounter.hasNext()) {
			count++;
			lineCounter.nextLine();
		}
		lineCounter.close();

		// Apply the rule-of-thumb to let the table size be 
		// 3X the number of anticipated entries
		sizeOfHashTable = count / 6 * 3;
		
		Hash<Employee> employeeTable = new Hash<Employee>(sizeOfHashTable); // Two times the number of elements in the table
		BST<Employee> byId = new BST<Employee>();
		BST<String> byName = new BST<String>();
		readFile(employeeTable, byId, byName);
		String choice = "";
		boolean replay = true;
		Scanner input = new Scanner(System.in);
		
		printOutWelcome();

		do {
			printOutMenu();
			choice = input.next();
			String name, id, position;
			double pay;
			char gender;
			if (choice.equalsIgnoreCase("a")) {
				System.out.println("\nAdding an employee!\n");
				System.out.print("Enter the name: ");
				name = in.nextLine();
				System.out.print("Enter the id: ");
				id = in.nextLine();
				System.out.print("Enter the pay: ");
				pay = Double.parseDouble(in.nextLine());
				System.out.print("Enter the position: ");
				position = in.nextLine();
				System.out.print("Enter the gender: ");
				gender = in.nextLine().charAt(0);
				Employee e = new Employee(name, id, pay, position, gender);
				employeeTable.insert(e);
				byId.insert(e);
				byName.insert(name);
				System.out.println("\n" + name + " was added!\n");
			} else if (choice.equalsIgnoreCase("r")) {			//the data is replaced with 2 blank lines fix it
				System.out.println("\nRemoving an employee!\n");
				System.out.print("Enter the name: ");
				name = in.nextLine();
				System.out.print("Enter the id: ");
				id = in.nextLine();
				
				Employee e = new Employee(name, id, 0, "", ' ');
				if (!employeeTable.search(e)) {
					System.out.println("\nI cannot find " + id + " in the database");
				} else {
					try {
						employeeTable.remove(e);
						byId.remove(e);
						byName.remove(name);
						System.out.println("\nEmployee " + id + " was removed!");
					} catch (NoSuchElementException nse) {
						System.out.println("\nI cannot find " + name + " " + id + " in the database");
					}
				}
			
			} else if (choice.equalsIgnoreCase("p")) {
				System.out.println("\nPrinting Employee Database\n");
				String choice3;
                System.out.print("Would you like to print \nU. Unsorted \nI. Sorted by ID \nN. Sorted by NAME");
                System.out.print("\n\nEnter your choice: ");
				choice3 = in.nextLine();
				if(choice3.equalsIgnoreCase("u")) {
					for (int i = 0; i < sizeOfHashTable; i++) {
					employeeTable.printBucket(i);
					}
				} else if(choice3.equalsIgnoreCase("i")) {
					byId.inOrderPrint();
				} else {
					String[] inOrderArray = byName.inOrderString().split(" ");
					List<String> printList = new List<String>();
					
					for (int index = 0; index < inOrderArray.length; index++) {
						String printName = inOrderArray[index];
						if (printList.binarySearch(printName) == -1) {
							printList.addLast(printName);
							Employee e = new Employee(printName, " ", 0, " ", ' ');
							employeeTable.printSearch(e);
						}
					}					
				}
				
			} else if (choice.equalsIgnoreCase("s")) {
				String choice2;
				System.out.println("Searching for an employee!\n");
				System.out.print("Would you like to search using the \nN. NAME \nI. ID");
				System.out.print("\n\nEnter your choice: ");
				choice2 = in.nextLine();
				if (choice2.equalsIgnoreCase("n")) {
					System.out.print("Enter the name: ");
					name = in.nextLine();
					Employee e = new Employee(name, " ", 0, " ", ' ');
					if (!employeeTable.search(e)) {
						System.out.println("\n" + name + " is not in the database.\n");
					}
					else {
						employeeTable.printSearch(e);
					}
				} else {
					System.out.print("Enter the id: ");
					id = in.nextLine();
					Employee e = new Employee(" ", id, 0, "", ' ');
					if (!employeeTable.search(e)) {
						System.out.println("\nEmployee " + id + " is not in the database.\n");
					}
					else {
						employeeTable.printSearch(e);
					}
				}				
				
			} else if (choice.equalsIgnoreCase("w")) {
				writeToFile(employeeTable);
			} else if (choice.equalsIgnoreCase("q")) {
				replay = false;
				System.out.println("\nGood bye!");
                writeToFile(employeeTable);
			} else if (!choice.equalsIgnoreCase("q")) {
				System.out.print("\n\nInvalid option!\n\n");
			}
		} while (replay);
		input.close();
	}

	public static void printOutWelcome() {
		System.out.print("Welcome to the Employee Database!\n");
	}

	public static void printOutMenu() {
		System.out.println("\nPlease select your option from the menu:\n");
		System.out.println("A. Add an employee");
		System.out.println("R. Remove an employee");
		System.out.println("P. Print all employees");
		System.out.println("S. Search an employee");
		System.out.println("W. Write to file");
		System.out.println("Q. Quit");
		System.out.print("\nEnter your choice: ");
	}

	public static void readFile(Hash<Employee> employeeTable, BST<Employee> byId, BST<String> byName) {
		boolean eof = false, replay = true;
		String line1, line2, line4, temp, input = "";
		double line3;
		char line5;
		try {
			
			BufferedReader buff = new BufferedReader(new FileReader(filename));
			while (!eof) {
				line1 = buff.readLine();
				if (line1 == null) {
					eof = true;
					break;
				} else if (line1.isEmpty()) {
					line1 = buff.readLine();
					if (line1 == null) {
						eof = true;
						break;
					}
				}
				line1 = line1.substring(6);		//name
				line2 = buff.readLine();		//id
				line2 = line2.substring(4);
				temp = buff.readLine();
				temp = temp.substring(5);
				line3 = Double.parseDouble(temp);	//pay
				line4 = buff.readLine();
				line4 = line4.substring(10);		//position
				temp = buff.readLine();
				line5 = temp.charAt(8);				//gender

				Employee e = new Employee(line1, line2, line3, line4, line5);
				employeeTable.insert(e);
				byId.insert(e);
				byName.insert(line1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeToFile(Hash<Employee> employeeTable) {
		try {
			String element = "";
			BufferedWriter writer = new BufferedWriter(new FileWriter("employeeDatabaseOutput.txt"));
			for (int i = 0; i < sizeOfHashTable; i++) {
				if (employeeTable.bucket(i).equalsIgnoreCase("The bucket is empty")) {
					continue;
				}
				element = employeeTable.bucket(i);
				writer.write(element);	
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}