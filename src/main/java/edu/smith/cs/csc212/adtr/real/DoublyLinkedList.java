package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;


public class DoublyLinkedList<T> extends ListADT<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		T removed = start.value;
		Node<T> second = start.after;
		if (second != null) {
			start = second;
			start.after = second.after;
			start.before = null;
		}
		else {
			start = null;
			end = null;
		}
		return removed;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T removed = end.value;
		Node<T> secondLast = end.before;
		if (secondLast != null) {
			end = secondLast;
			end.before = secondLast.before;
			end.after = null;
		}
		else {
			end = null;
			start = null;
		}
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = this.getIndex(index);
		int at = 0;
		if (index == 0) {
			return removeFront();
		}
		for (Node<T> n = start; n != null; n = n.after) {
			if (at == index - 1) {
				n.after = n.after.after;
			}
			if (at == index + 1) {
				n.before = n.before.before;
				return removed;
			}
		at++;
		}
		return removed;
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> second = start;
			start = new Node<T>(item);
			start.before = null;
			start.after = second;
		}

	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		Node<T> toAdd = null;
		if (end == null) {
			start = end = new Node<T>(item);
		} 
		else if (index > this.size() || index < 0) {
			throw new BadIndexError(index);
		}
		else if (index == 0) {
			this.addFront(item);
		}
		else {
			int at = 0;
			for (Node<T> n = start; n != null; n = n.after) {
				if (at == index -1) {
					toAdd = new Node<T>(item);
					toAdd.before = n;
					toAdd.after = n.after;
					n.after = toAdd;
				}
				if (at == index + 1) {
					n.before = toAdd;
					return;
				}
				at++;
			}
			end = toAdd;
		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return end.value;
		
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for(Node<T> n = start; n != null; n = n.after) {
			if (at == index) {
				return n.value;
			}
			at++;
		}
		throw new BadIndexError(index);
	}
	
	public void setIndex(int index, T value) {
		checkNotEmpty();
		if(index < 0 || index > this.size() - 1) {
			throw new BadIndexError(index);
		}
		int at = 0;
		for(Node<T> n = start; n != null; n = n.after) {
			if (at == index) {
				n.value = value;
			}
			at++;
		}
	}

	@Override
	public int size() {
		int count = 0;
		for(Node<T> n = start; n != null; n = n.after) {
			count ++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (end != null && start != null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
