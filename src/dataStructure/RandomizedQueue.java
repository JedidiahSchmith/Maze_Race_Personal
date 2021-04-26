package dataStructure;

public class RandomizedQueue<Type> {
	private Type[] queueArray;
	private int size;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		size = 0;
		queueArray = (Type[]) new Object[4];
	}

	public void enqueue(Type item) {
		if (item.equals(null))
			throw new NullPointerException();

		queueArray[size++] = item;
		resizeArray();
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int Size() {
		return size;
	}

	public Type dequeue() {
		int indexToDequeue = (int) (Math.random() * size);

		Type itemToRemove = queueArray[indexToDequeue];
		queueArray[indexToDequeue] = queueArray[--size];
		queueArray[size] = null;
		resizeArray();
		return itemToRemove;
	}

	@SuppressWarnings("unchecked")
	private void resizeArray() {
		Type[] newArray = null;
		boolean makeNewArray = false;
		if (size < queueArray.length / 4) {
			makeNewArray = true;
			newArray = (Type[]) new Object[queueArray.length / 2];
		} else if (size == queueArray.length) {
			makeNewArray = true;
			newArray = (Type[]) new Object[queueArray.length * 2];
		}

		if (makeNewArray) {
			for (int i = 0; i < size; i++) {
				newArray[i] = queueArray[i];
			}
			queueArray = newArray;
		}

	}

}
