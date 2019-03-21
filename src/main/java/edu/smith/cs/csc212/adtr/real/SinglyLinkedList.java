package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list.
	 * Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	@Override
	public T removeFront() {
		T removed = this.getFront();
		
		this.start = this.start.next;
		
		return removed;
	}

	@Override
	public T removeBack() {
		T removed = this.getBack();
		this.setIndex(this.size() - 1, null);
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = this.getIndex(index);
		int at = 0;
		for (Node<T> n = this.start; n.value != null; n = n.next) {
			if (at == index - 1) {
				n.next.value = this.getIndex(index + 1);
			}
			at++;
		}
		
		return removed;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);	
	}

	@Override
	public void addBack(T item) {
		if (this.size() > 0) {
			addIndex(this.size(), item);
		}
		else {
			this.start = new Node<T>(item, start);
		}	
	}

	@Override
	public void addIndex(int index, T item) {
		checkNotEmpty();
		if (index > this.size() || index < 0) {
			throw new BadIndexError(index);
		}
		
		if (index == 0) {
			addFront(item);
		}
		else {
		
			int at = 0;
			
			for (Node<T> n = this.start; n.value != null; n = n.next) {
				if (at == index - 1) {
					Node<T> x = new Node<T>(item, n.next);
					n.next = x;
				}
				at++;
				}	
		}
	}
	
	
	
	@Override
	public T getFront() {
		checkNotEmpty();
		return getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return getIndex(this.size() - 1);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		if (index > this.size() - 1 || index < 0) {
			throw new BadIndexError(index);
		} 
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				n.value = value;
			}
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
