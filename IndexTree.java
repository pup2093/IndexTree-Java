
import java.io.*;
import java.util.Scanner;

//import BinaryTree.Node;

//import BinaryTree.Node;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;

	// Make your constructor
	// It doesn't need to do anything
	public IndexTree() {
		this.root = null; // not sure
	}

	// complete the methods below

	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber) {
		this.root = add(this.root, word, lineNumber);

	}

	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists,
	// you want to add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber) {
		if (root == null) {
			return new IndexNode(word, lineNumber);
		}
		int comparisonResult = root.word.compareTo(word);
		if (comparisonResult == 0) { // if same word
			if(!(root.list.contains(lineNumber))) {
				root.list.add(lineNumber);
			}	
			root.occurences++;
			return root;
		} else if (comparisonResult < 0) {
			root.left = add(root.left, word, lineNumber);
			// this.root.left.occurences++;
			return root;
		} else {
			root.right = add(root.right, word, lineNumber);
			// this.root.right.occurences++;
			return root;
		}

	}

	// returns true if the word is in the index
	public boolean contains(String word) {
		return contains(this.root, word);
	}

	private boolean contains(IndexNode root, String word) {
		if (root == null) {
			return false;
		}
		int comparisonResult = root.word.compareTo(word);
		if (comparisonResult == 0) {
			return true;
		} else if (comparisonResult < 0) {
			return contains(root.left, word);
		} else {
			return contains(root.right, word);
		}

	}

	// call your recursive method
	// use book as guide
	public void delete(String word) {
		this.root = delete(this.root, word);
	}

	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word) {
		if (root == null) {
			return null;
		}
		int comparisonResult = root.word.compareTo(word);
		if (comparisonResult < 0) {
			root.left = delete(root.left, word);
			return root;
		} else if (comparisonResult > 0) {
			root.right = delete(root.right, word);
			return root;
		} else { // root is the item we want to delete

			// case 1, root is leaf
			if (root.left == null && root.right == null) {
				return null;
			} // case 2, root has only left child
			else if (root.left != null && root.right == null) {
				return root.left;
			} else if (root.left == null && root.right != null) {
				return root.right;
			} else {
				IndexNode rootOfLeftSubtree = root.left;
				IndexNode parentOfPredecessor = null;
				IndexNode predecessor = null;

				if (rootOfLeftSubtree.right == null) {
					root.word = rootOfLeftSubtree.word;
					root.left = rootOfLeftSubtree.left;
					return root;
				} else {
					parentOfPredecessor = rootOfLeftSubtree;
					IndexNode current = rootOfLeftSubtree.right;
					while (current.right != null) {
						parentOfPredecessor = current;
						current = current.right;
					}

					predecessor = current;
					root.word = predecessor.word;
					parentOfPredecessor.right = predecessor.left;
					return root;

				}
			}

		}

	}

	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the
	// list of all occurrences
	// each word and its data gets its own line
	/*
	 * public static void printIndex(IndexNode root){ if ( root == null ) return;
	 * 
	 * printIndex(root.left); // System.out.print( "Node data: " + root);
	 * //System.out.print("\n"); printIndex(root.right); System.out.print( "Node: "
	 * + root); System.out.print("\n");
	 * 
	 * 
	 * }
	 */

	public static void inOrder(IndexNode root) {
		if (root == null) {
			return;
		}
		inOrder(root.right);
		System.out.printf("%s ", root);
		System.out.print("\n");
		inOrder(root.left);
	}

	public static void main(String[] args) {
		IndexTree index = new IndexTree();

		// add all the words to the tree

		// print out the index

		// test removing a word from the index

		// read file
		String fileName = "Test.txt";
		try {
			Scanner scan = new Scanner(new File(fileName));
			int currentLine = 0;
			String line = "";

			while (scan.hasNextLine()) {

				line = scan.nextLine();
				if (line.length() == 0) {
					currentLine++;
					continue;
				}

				currentLine++;
				String[] words = line.replaceAll("\\p{Punct}", "").toLowerCase().split("\\s+");
				for (String word : words) {
					index.add(word, currentLine);

				}

			}

			scan.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// printIndex(index.root);
		index.delete("bear");
		index.delete("william");
		inOrder(index.root);

		System.out.println(index.contains("thy"));

	}
}