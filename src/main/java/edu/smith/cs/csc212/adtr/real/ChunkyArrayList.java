package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	private int chunkSize;
	private GrowableList<FixedSizeList<T>> chunks;

	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}
	
	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {
		checkNotEmpty();
		T removed = getIndex(0);
		setIndex(0, null);
		return removed;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T removed = getIndex(this.size() - 1);
		setIndex(this.size()-1, null);
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = getIndex(index);
		setIndex(index, null);
		return removed;
	}

	@Override
	public void addFront(T item) {
		throw new TODOErr();
	}

	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			setIndex(0, item);
		}
		setIndex(this.size(), item);
	}

	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.
		
		int chunkIndex = 0;
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index <= end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					throw new TODOErr();
				} else {
					// put right in this chunk, there's space.
					throw new TODOErr();
				}	
				// upon adding, return.
				// return;
			}
			
			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		int start = 0;
		if (this.chunks.size() == 0) {
			this.chunks.addBack(makeChunk());
		}
		for (FixedSizeList<T> chunk : this.chunks) {
			int end = start + chunk.size();
			if ((start <= index && index < end)) {
				chunk.setIndex(index-start, value);
				System.out.println(this.size());
				//return;
			}
			start = end;
		}
		if (index < 0 || (index >= this.size() && index != 0)) {
			throw new BadIndexError(index);
		}
	}

	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}