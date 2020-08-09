import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		copyHelper(bst.root);
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if (node != null) {
			this.insert(node.data);
			copyHelper(node.left);
			copyHelper(node.right);
		}
		
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when preconditon is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (root == null) {
			throw new NoSuchElementException("getRoot: The BST is currently empty");
		}
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		} else if (node.left == null){
			return getSize(node.right) + 1;
		} else if (node.right == null) {
			return getSize(node.left) + 1;
		} else {
			return getSize(node.right) + getSize(node.left) + 1;
		}
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root); // remove this. just a default value
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		} else {
			if (getHeight(node.left) > getHeight(node.right)) {
				return getHeight(node.left) + 1;
			} else {
				return getHeight(node.right) + 1;
			}
		}
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (root == null) {
			throw new NoSuchElementException("findMin: The BST is currently empty");
		} else {
			return findMin(root);
		}
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		Node temp = node;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp.data;
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (root == null) {
			throw new NoSuchElementException("findMax: The BST is currently empty");
		} else {
			return findMax(root);
		}
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		Node temp = node;
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp.data;
	}

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if (root == null) {
			return false;
		} else {
			return search(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {
		if (data.compareTo(node.data) == 0) {
			return true;
		} else if (data.compareTo(node.data) < 0) {
			if (node.left == null) {
				return false;
			} else {
				return search(data, node.left);
			}
		} else {
			if (node.right == null) {
				return false;
			} else {
				return search(data, node.right);
			}
		}
	}

	/**
	 * Determines whether two trees store identical data in the same structural
	 * position in the tree
	 * 
	 * @param o another Object
	 * @return whether the two trees are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof BST)) {
            return false;
        } else {
        	BST B = (BST) o;
        	if (this.getSize() != B.getSize()) {
                return false;
            } else {
            	return equals(B.root, this.root);
            }
        }
	}

	/**
	 * Helper method for the equals method
	 * 
	 * @param node1 the node of the first bst
	 * @param node2 the node of the second bst
	 * @return whether the two trees contain identical data stored in the same
	 *         structural position inside the trees
	 */
	private boolean equals(Node node1, Node node2) {
		boolean rootEqual = false;
        boolean lEqual = false;
        boolean rEqual = false;  
		if (node1 != null && node2 != null) {
            if (node1.data == node2.data) {
            	rootEqual = true;
            }
            if (node1.left != null && node2.left != null) {
            	lEqual = equals(node1.left, node2.left);
            } else if (node1.left == null && node2.left == null) {
            	lEqual = true;
            } if (node1.right != null && node2.right != null) {
            	rEqual = equals(node1.right, node2.right);
            } else if (node1.right == null && node2.right == null) {
            	rEqual = true;
            }
            return (rootEqual && lEqual && rEqual);
        }
        return false;
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @precondition !isEmpty()
	 * @precondition the data is located in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public void remove(T data) throws NoSuchElementException {
		if (root == null) {
			throw new NoSuchElementException("remove: The BST is currently empty");
		} else if (!search(data)){
			throw new NoSuchElementException("remove: The element is not in the BST");
		} else {
			root = remove(data, root);
		}
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {
		if (node == null) {
			return node;
		} else if (data.compareTo(node.data) < 0) {
			node.left = remove(data, node.left);
		} else if (data.compareTo(node.data) > 0) {
			node.right = remove(data, node.right);
		} else {		//If the node we are removing is the root
			if (node.left == null && node.right == null) {
				node = null;
			} else if (node.right == null) {
				node = node.left;
			} else if (node.left == null) {
				node = node.right;
			} else {
				node.data = findMin(node.right);
				node.right = remove(node.data, node.right);
			}
		}
		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Prints the data in pre-order to the console
	 */
	public void preOrderPrint() {
		preOrderPrint(root);
		System.out.println();
	}

	/**
	 * Helper method to preOrderPrint method Prints the data in pre order to the
	 * console
	 */
	private void preOrderPrint(Node node) {
		if (node != null) {
			System.out.print(node.data + " ");
			preOrderPrint(node.left);
			preOrderPrint(node.right);
		}
	}

	/**
	 * Prints the data in sorted order to the console
	 */
	public void inOrderPrint() {
		inOrderPrint(root);
		System.out.println();
	}

	/**
	 * Prints the data in post order to the console
	 */
	public void postOrderPrint() {
		postOrderPrint(root);
		System.out.println();
	}

	/**
	 * Helper method to inOrderPrint method Prints the data in sorted order to the
	 * console
	 */
	private void inOrderPrint(Node node) {
		if (node != null) {
			inOrderPrint(node.left);
			System.out.print(node.data + "");
			inOrderPrint(node.right);
		}
	}

	/**
	 * Return string with in sorted order
	 */
	public String inOrderString() {
		StringBuilder sb = new StringBuilder();
		inOrderString(root, sb);
		return sb.toString();
	}

	/**
	 * Helper function to return string with in sorted order
	 */
	private void inOrderString(Node node, StringBuilder sb) {
		if (node != null) {
			inOrderString(node.left, sb);
			sb.append(node.data + " ");
			inOrderString(node.right, sb);
		}
	}
	
	/**
	 * Helper method to postOrderPrint method Prints the data in post order to the
	 * console
	 */
	private void postOrderPrint(Node node) {
		if (node != null) {
			postOrderPrint(node.left);
			postOrderPrint(node.right);
			System.out.print(node.data + " ");
		}
	}
}
