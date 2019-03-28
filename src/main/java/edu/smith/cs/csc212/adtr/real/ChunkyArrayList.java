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
		this.chunks.getFront().removeFront();
		if (this.chunks.getFront().isEmpty()) {
			this.chunks.removeFront();
		}
		return removed;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T removed = getIndex(this.size() - 1);
		this.chunks.getBack().removeBack();
		if (this.chunks.getBack().isEmpty()) {
			this.chunks.removeBack();
		}
		return removed;
	}

	@Override
	public T removeIndex(int index) {
		if (this.isEmpty()){
			throw new EmptyListError();
		}
		int start = 0;
		int count = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				T removed = chunk.removeIndex(index - start);
				if (chunk.isEmpty()) {
					chunks.removeIndex(count);	
				}
				return removed;
			}
			
			// update bounds of next chunk.
			start = end;
			count++;
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (this.isEmpty() || chunks.getFront().isFull()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addFront(chunk);
			return;
		}
		chunks.getFront().addFront(item);
	}

	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addFront(chunk);
			return;
		}
		if (chunks.getBack().isFull()) {
			FixedSizeList<T> chunk = makeChunk();
			chunk.addFront(item);
			chunks.addBack(chunk);
		}
		else {
			chunks.getBack().addBack(item);
		}
	}

	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.
		
		if(index == 0) {
			this.addFront(item);
			return;
		}
		if(index == this.size()) {
			this.addBack(item);
			return;
		}
		
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
					FixedSizeList<T> newChunk = makeChunk();
					T saved = null;
					T saved2 = null;
					for(int i = 0; i < chunk.size(); i++) {
						if (i == index - start) {
							saved = chunk.getIndex(i);
							chunk.setIndex(i, item);
						}
						if (i > index - start) {
							saved2 = chunk.getIndex(i);
							chunk.setIndex(i, saved);
							saved = saved2;
						}
					}
					newChunk.addFront(saved);
					chunks.addIndex(chunkIndex + 1, newChunk);
					return;
				} else {
					T saved = null;
					T saved2 = null;
					for(int i = 0; i < chunk.size(); i++) {
						if (i == index - start) {
							saved = chunk.getIndex(i);
							chunk.setIndex(i, item);
						}
						if (i > index - start) {
							saved2 = chunk.getIndex(i);
							chunk.setIndex(i, saved);
							saved = saved2;
						}
					}
					return;
					}
				// upon adding, return.
				//return;
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
		if (this.isEmpty()){
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
		checkNotEmpty();
		
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			int end = start + chunk.size();
			if ((start <= index && index < end)) {
				chunk.setIndex(index - start, value);
				return;
			}
			start = end;
		}
		throw new BadIndexError(index);
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