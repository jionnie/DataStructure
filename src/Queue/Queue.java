package Queue;

public interface Queue<E> {
	boolean offer(E value);
	E poll();
	E remove();
	E peek();
	E element();
	int size();
	void clear();
	boolean contains(Object value);
	boolean isEmpty();
}
