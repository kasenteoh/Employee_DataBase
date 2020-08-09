
/**
 * Group Project
 */

import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 * 
	 * @postcondition The post condition for the constructor is that a new object is
	 *                created with the length is set to 0 and the first, last, and
	 *                iterator nodes are null
	 */
	public List() {
		first = null;
		last = null;
		iterator = null;
		length = 0;
	}

	/**
	 * Instantiates a new List by copying another List
	 * 
	 * @param original the List to make a copy of
	 * @postcondition a new List object, which is an identical but separate copy of
	 *                the List original
	 */
	public List(List original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition The precondition for this method is that the list is not empty
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition The precondition for this method is that the list is not empty,
	 *               that there is a last node
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 * 
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 * 
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		if (length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the data of the node the iterator is currently pointing to
	 * 
	 * @precondition the iterator cannot be null
	 * @return the data of the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException(
					"getIterator: Iterator is currently pointing to null. " + "Cannot return any value");
		}
		return iterator.data;
	}

	/**
	 * Returns whether the iterator is null
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Determines whether two Lists have the same data in the same order
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List L = (List) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { // Lists are same length
					if (temp1.data != temp2.data) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**
	 * Determines whether a List is sorted by calling its recursive helper method
	 * isSorted Note: An empty List can be considered to be (trivially) sorted
	 * 
	 * @return whether this List is sorted
	 */
	public boolean inSortedOrder() {
		return inSortedOrder(first);
	}

	/**
	 * Helper method to inSortedOrder Determines whether a List is sorted in
	 * ascending order recursively
	 * 
	 * @return whether this List is sorted
	 */
	private boolean inSortedOrder(Node node) {
		if (node == null) {
			return true;
		} else if (node.next == null) { // If there is only one node in the list
			return true;
		} else {
			return (node.data.compareTo(node.next.data) < 0) && inSortedOrder(node.next);	//-1 means node.data < node.next.data 0 means they are equal
		}
	}

	/**
	 * Returns the index of the iterator from 1 to n. Note that there is no index 0.
	 * Does not use recursion.
	 * 
	 * @precondition iterator is not pointing to null
	 * @return the index of the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public int getIndex() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIndex: iterator is pointing to null");
		} else {
			Node temp = first;
			int index = 1;
			while (temp != iterator) {
				index++;
				temp = temp.next;
			}
			return index;
		}
	}

	/**
	 * Uses the iterative linear search algorithm to locate a specific element in
	 * the list
	 * 
	 * @param element the value to search for
	 * @return the location of value in the List or -1 to indicate not found Note
	 *         that if the List is empty we will consider the element to be not
	 *         found
	 * @postcondition: position of the iterator remains unchanged!
	 */
	public int linearSearch(T element) {
		Node temp = first;
		temp = first;
		temp = first;
		
		for (int i = 0; i < length; i ++) {
			if (temp == null) {
				return -1;
			} else if (temp.data.equals(element)) {
				return i + 1;
			} else {
				temp = temp.next;
			}
		}
		return -1;
	}
	
	/**
     * Returns the index from 1 to length
     * where value is located in the List
     * by calling the private helper method
     * binarySearch
     * @param value the value to search for
     * @return the index where value is
     * stored from 1 to length, or -1 to
     * indicate not found
     * @precondition isSorted()
     * @postcondition the position of the
     * iterator must remain unchanged!
     * @throws IllegalStateException when the
     * precondition is violated.
     */
    public int binarySearch(T value) throws IllegalStateException {
    	if (!inSortedOrder()) {
    		throw new IllegalStateException("binarySearch: The list is currently not sorted!");
    	}
        return binarySearch(1, length, value);
    }
   
    /**
     * Searches for the specified value in
     * the List by implementing the recursive
     * binarySearch algorithm
     * @param low the lowest bounds of the search
     * @param high the highest bounds of the search
     * @param value the value to search for
     * @return the index at which value is located
     * or -1 to indicate not found
     * @postcondition the location of the iterator
     * must remain unchanged
     */
    private int binarySearch(int low, int high, T value) {
    	if (high < low) {
    		return -1;
    	} 
    	int mid = low + (high - low) / 2;
    	Node temp = first;
    	for (int i = 1; i < mid; i++) {
    		temp = temp.next;
    	}
    	if (temp.data.equals(value)) {
    		return mid;
    	} else if (value.compareTo(temp.data) == -1) {	//value < temp.data
    		return binarySearch(low, mid - 1, value);
    	} else {	// temp.data > value
    		return binarySearch(mid + 1, high, value);
    	}
    }

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the list
	 * @postcondition The postcondition for this mutator is that it successfully
	 *                adds a new node to the front of the list
	 */

	public void addFirst(T data) {
		if (first == null) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the list
	 * @postcondition The postcondition for this mutator is that is successfully
	 *                appends a new node at the end of the list and redirects the
	 *                previous last node to the current last node
	 */

	public void addLast(T data) {
		if (last == null) {
			last = first = new Node(data);
		} else {
			Node N = new Node(data);
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * removes the element at the front of the list
	 * 
	 * @precondition The precondition for this mutator is that the list is not empty
	 * @postcondition The post condition for this mutator is that the first element
	 *                is successfully removed the the following node is the new head
	 *                of the list
	 * @throws NoSuchElementException when precondition is violated
	 */

	public void removeFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			first = first.next;
			first.prev = null; // Setting the new first node prev to null
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 * 
	 * @precondition The precondition for this mutator is that the list is not empty
	 *               and that the list has a tail (the last node)
	 * @postcondition The postcondition for this mutator is that the tail of the
	 *                list is successfully deleted from the list and the second last
	 *                node of the list is the new tail
	 * @throws NoSuchElementException when precondition is violated
	 */

	public void removeLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeLast(): Cannot remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		length--;
	}

	/**
	 * Sets the iterator to the first node of the list
	 * 
	 * @postcondition iterator points to the first node of the list
	 */
	public void pointIterator() {
		iterator = first;
	}

	/**
	 * Adds an element after the node the iterator is pointing at
	 * 
	 * @precondition The precondition for this mutator is iterator is not pointing
	 *               to null
	 * @postcondition The postcondition for this mutator is that it successfully
	 *                adds an element after the node that iterator is pointing to
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void addIterator(T data) throws NoSuchElementException {
		if (iterator == null) {
			throw new NoSuchElementException(
					"addIterator: Iterator is currently pointing to null. Cannot add after null");
		} else if (iterator == last) {
			addLast(data);
		} else {
			Node N = new Node(data);
			iterator.next.prev = N;
			N.next = iterator.next;
			iterator.next = N;
			N.prev = iterator;
			length++;
		}
	}

	/**
	 * Removes the element iterator is pointing at
	 * 
	 * @precondition The precondition for this mutator is iterator is not pointing
	 *               to null
	 * @postcondition The postcondition for this mutator is that it successfully
	 *                removes the node that iterator is pointing to and iterator
	 *                points to null afterwards
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeIterator() throws NoSuchElementException {
		if (iterator == null) {
			throw new NoSuchElementException(
					"removeIterator: Iterator is currently pointing to null. Cannot remove null");
		} else if (length == 1) {
			first = last = null;
		} else if (iterator == last) { // If the iterator is the last node, then call removeLast
			removeLast();
		} else if (iterator == first) { // If the iterator is the first node, then call removeFirst
			removeFirst();
		} else {
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			length--;
		}
		iterator = null;
	}

	/**
	 * Advances the iterator by one node in the List
	 * 
	 * @precondition iterator cannot be null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator(): " + "iterator is offEnd. Cannot advance");
		}
		iterator = iterator.next;
	}

	/**
	 * Reverses the iterator by one node in the List
	 * 
	 * @precondition iterator cannot be null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void reverseIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator(): " + "iterator is offEnd. Cannot reverse");
		}
		iterator = iterator.prev;
	}

	/**
	 * Places the iterator at first and then iteratively advances it to the
	 * specified index no recursion
	 * 
	 * @param index the index where the iterator should be placed
	 * @precondition 1 <= index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */
	public void advanceToIndex(int index) throws IndexOutOfBoundsException {
		if (index < 1 || index > length) {
			throw new IndexOutOfBoundsException("advanceToIndex: Invalid index number");
		} else {
			pointIterator();
			for (int i = 0; i < index - 1; i++) { // Index - 1 because pointIterator sets the iterator to the first
													// element already
				advanceIterator();
			}
		}
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value on its own line At the end of the List a new line
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + ""; // Appends the data into one string
			temp = temp.next; // Moves onto the next node
		}
		return result;
	}

	/**
	 * prints the contents of the linked list to the screen in the format #.
	 * <element> followed by a newline
	 */
	public void printNumberedList() {
		Node temp = first;
		while(temp != null) {
		for (int i = 0; i < length; i++) {
			System.out.printf(temp.data + "\n\n", i + 1);
			temp = temp.next;
		}
	  }
	}

	/**
	 * Prints a linked list to the console in reverse by calling the private
	 * recursive helper method printInReverse
	 */
	public void printInReverse() {
		printInReverse(last);
	}

	/**
	 * Recursively prints a linked list to the console in reverse order from last to
	 * first (no loops) Each element separated by a space Should print a new line
	 * after all elements have been displayed
	 */

	private void printInReverse(Node node) {
		if (length == 0) {
			System.out.println();
		} else if (node == first) {
			System.out.println(first.data);
		} else {
			System.out.print(node.data + " ");
			printInReverse(node.prev);
		}
	}
}