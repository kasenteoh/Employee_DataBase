
/**
* Hash.java
* Contains the hash table code to insert, remove, and search
* Hash table of linked list
*/
import java.util.ArrayList;

public class Hash<T extends Comparable<T>> {

	private int numElements;
	private ArrayList<List<T>> Table;

	/**
	 * Constructor for the Hash.java class. Initializes the Table to be sized
	 * according to value passed in as a parameter. Inserts size empty Lists into
	 * the table. Sets numElements to 0
	 * 
	 * @param size the table size
	 */
	public Hash(int size) {
		Table = new ArrayList<List<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(new List<T>());
		}
		numElements = 0;
	}

	/** Accessors */

	/**
	 * Returns the hash value in the Table for a given key by taking modulus of the
	 * hashCode value for that key and the size of the table
	 * 
	 * @param t the key
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return code % Table.size();
	}

	/**
	 * Counts the number of keys at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of keys at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): " + "index is outside bounds of the table");
		}
		return Table.get(index).getLength();
	}

	/**
	 * Returns total number of keys in the Table
	 * 
	 * @return total number of keys
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Searches for a specified key in the Table
	 * 
	 * @param t the key to search for
	 * @return whether the key is in the Table
	 */
	public boolean search(T t) {
		int temp = 0;
		for (int i = 0; i < Table.size(); i++) {
			temp = Table.get(i).linearSearch(t);
			if (temp != -1) {
				return true;
			}
		}
		return false;
	}
	
	public void printSearch(T t) {
		int temp = 0;
		for (int i = 0; i < Table.size(); i++) {
			temp = Table.get(i).linearSearch(t);
			if (temp != -1) {
				Table.get(i).advanceToIndex(temp);
				System.out.print(Table.get(i).getIterator() + "");
			}
		}
	}
	
	/** Manipulation Procedures */

	/**
	 * Inserts a new key in the Table calls the hash method to determine placement
	 * 
	 * @param t the key to insert
	 */
	public void insert(T t) {
		int bucket = hash(t);
		Table.get(bucket).addLast(t);
		numElements++;
	}

	/**
	 * removes the key t from the Table calls the hash method on the key to
	 * determine correct placement has no effect if t is not in the Table
	 * 
	 * @param t the key to remove
	 */
	public void remove(T t) {
		int bucket = hash(t);
		int index = Table.get(bucket).linearSearch(t);
		if (index != -1) {
			Table.get(bucket).advanceToIndex(index);
			Table.get(bucket).removeIterator();
			numElements--;
		}
	}

	/** Additional Methods */

	/**
	 * Prints all the keys at a specified bucket in the Table. Each key displayed on
	 * its own line, with a blank line separating each key Above the keys, prints
	 * the message "Printing bucket #<bucket>:" Note that there is no <> in the
	 * output
	 * 
	 * @param bucket the index in the Table
	 */
	public void printBucket(int bucket) {
		if (!Table.get(bucket).isEmpty()) {
			Table.get(bucket).printNumberedList();
		}
	}

	/**
	 * Prints the first key at each bucket along with a count of the total keys with
	 * the message "+ <count> -1 more at this bucket." Each bucket separated with to
	 * blank lines. When the bucket is empty, prints the message "This bucket is
	 * empty." followed by two blank lines
	 */
	public void printTable() {
		for (int i = 0; i < Table.size(); i++) {
			System.out.println("Bucket: " + i);
			if (Table.get(i).isEmpty()) {
				System.out.println("This bucket is empty\n\n");
			} else {
				System.out.println(Table.get(i).getFirst());
				int remaining = Table.get(i).getLength() - 1;
				System.out.println("+ " + remaining + " more at this bucket\n\n");
			}
		}
	}

	public String bucket(int bucket) {
		String elements = "";
		if (Table.get(bucket).isEmpty()) {
			return "The bucket is empty";
		} else {
			elements = Table.get(bucket).toString();
		}
		return elements;
	}
}
