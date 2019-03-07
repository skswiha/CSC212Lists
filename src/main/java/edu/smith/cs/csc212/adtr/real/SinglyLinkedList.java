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
		return removeIndex(0);
	}

	@Override
	public T removeBack() {
		return removeIndex(this.size() - 1);
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = this.getIndex(index);
		
		return removed;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		if (this.size() > 1) {
			addIndex(this.size() - 1, item);
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
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at == index) {
				n.value = item;
			}
			if (at > index) {
				n.value = n.next.value;
			}
			at++;
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
		if (index >= this.size() || index < 0) {
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
