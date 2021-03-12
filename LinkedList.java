// Assignment #: 10
//         Name: Luca Fulmor
//    StudentID: 1217485191
//  Lab Lecture: MWF 11:45 - 12:50
// A linked list is a sequence of nodes with efficient
// element insertion and removal.
// This class contains a subset of the methods of the
// standard java.util.LinkedList class.

import java.util.NoSuchElementException;

public class LinkedList {
	// nested class to represent a node
	private class Node {
		public Object data;
		public Node next;

	}

	// only instance variable that points to the first node.
	private Node first;

	// Constructs an empty linked list.
	public LinkedList() {
		first = null;
	}

	// Returns the first element in the linked list.
	public Object getFirst() {
		if (first == null) {
			NoSuchElementException ex = new NoSuchElementException();
			throw ex;
		} else
			return first.data;
	}

	// Removes the first element in the linked list.
	public Object removeFirst() {
		if (first == null) {
			NoSuchElementException ex = new NoSuchElementException();
			throw ex;
		} else {
			Object element = first.data;
			first = first.next; // change the reference since it's removed.
			return element;
		}
	}

	// Adds an element to the front of the linked list.
	public void addFirst(Object element) {
		// create a new node
		Node newNode = new Node();
		newNode.data = element;
		newNode.next = first;
		// change the first reference to the new node.
		first = newNode;
	}

	// Returns an iterator for iterating through this list.
	public ListIterator listIterator() {

		return new LinkedListIterator();
	}

	public String toString() {
		// this method formats our content of the linked list to print out in between
		// curly braces

		ListIterator iterator = this.listIterator();
		String result = "{ ";
		while (iterator.hasNext())
			result += (iterator.next() + " ");
		result += "}\n";
		return result;

		// need to print it alphabetically

	}

	public int size() {
		// returns the number of elements inside our linked list
		ListIterator iterator = this.listIterator();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

	public void addElement(Object element) {

		ListIterator iterator = this.listIterator();

		// starts from the first string and checks its lexographic order to our element,
		// and either continues or places it if it is alphabetical after

		Node comparison = first;

		while (iterator.hasNext())

			while (comparison != null) {

				String result = comparison.data.toString();

				if (element.toString().compareTo(result) > 0 || element.toString().compareTo(result) == 0) {

					iterator.next();

				}

				else
					iterator.add(element);

			}

	}

	public void removeElementsAtEvenIndices()

	{

		ListIterator iterator = this.listIterator();

		// iterates each place of our data and removes those at even indexes
		int index = 0;

		while (iterator.hasNext()) {
			iterator.next();
			if (index % 2 == 0) {
				iterator.remove();

			}

			index++;

		}

	}

	public int howManyAppearBefore(Object element) {

		// searches for a string and then counts how many strings appear before it
		ListIterator iterator = this.listIterator();
		int count = 0;

		while (iterator.hasNext()) {
			if (iterator.next().equals(element)) {
				return count;
			}

			count++;
		}

		return count;
	}

	public int indexOfLast(Object element) {

		// finds the last occurence of a string in the linked list

		Node node = new Node();
		node.data = element;
		int index = 0;
		int position = -1;

		if (first == null) {
			NoSuchElementException error = new NoSuchElementException();
			throw error;

		} else {
			Node after = first;

			while (after != null) {

				String result = after.data.toString();

				if (element.toString().compareTo(result) == 0) {

					position = index;

				}
				index++;
				after = after.next;
			}
			return position;
		}

	}

	public void duplicateEach() {

		// duplicates each value by reading them and then adding them right after
		ListIterator iterator = this.listIterator();
		while (iterator.hasNext()) {
			String value = (String) iterator.next();
			iterator.add(value);

		}

	}

	public Object removeElementAt(int index) {

		// takes in an index int and then removes the value at that place, throws error
		// if number entered is out of the size of the list

		ListIterator iterator = this.listIterator();
		Node node = new Node();
		int counter = 0;

		if (index > this.size() || index < 0) {

			IndexOutOfBoundsException error = new IndexOutOfBoundsException();
			throw error;

		} else {
			while (counter <= index) {
				node.data = iterator.next();
				counter++;

			}

			Object element = node.data;
			iterator.remove();
			return element;
		}

	}

// nested class to define its iterator
	private class LinkedListIterator implements ListIterator {
		private Node position; // current position
		private Node previous; // it is used for remove() method

		// Constructs an iterator that points to the front
		// of the linked list.

		public LinkedListIterator() {
			position = null;
			previous = null;
		}

		// Tests if there is an element after the iterator position.
		public boolean hasNext() {
			if (position == null) // not traversed yet
			{
				if (first != null)
					return true;
				else
					return false;
			} else {
				if (position.next != null)
					return true;
				else
					return false;
			}
		}

		// Moves the iterator past the next element, and returns
		// the traversed element's data.
		public Object next() {
			if (!hasNext()) {
				NoSuchElementException ex = new NoSuchElementException();
				throw ex;
			} else {
				previous = position; // Remember for remove

				if (position == null)
					position = first;
				else
					position = position.next;

				return position.data;
			}
		}

		// Adds an element after the iterator position
		// and moves the iterator past the inserted element.
		public void add(Object element) {
			if (position == null) // never traversed yet
			{
				addFirst(element);
				position = first;
			} else {
				// making a new node to add
				Node newNode = new Node();
				newNode.data = element;
				newNode.next = position.next;
				// change the link to insert the new node
				position.next = newNode;
				// move the position forward to the new node
				position = newNode;
			}
			// this means that we cannot call remove() right after add()
			previous = position;
		}

		// Removes the last traversed element. This method may
		// only be called after a call to the next() method.
		public void remove() {
			if (previous == position) // not after next() is called
			{
				IllegalStateException ex = new IllegalStateException();
				throw ex;
			} else {
				if (position == first) {
					removeFirst();
				} else {
					previous.next = position.next; // removing
				}
				// stepping back
				// this also means that remove() cannot be called twice in a row.
				position = previous;
			}
		}

		// Sets the last traversed element to a different value.
		public void set(Object element) {
			if (position == null) {
				NoSuchElementException ex = new NoSuchElementException();
				throw ex;
			} else
				position.data = element;
		}
	} // end of LinkedListIterator class
} // end of LinkedList class