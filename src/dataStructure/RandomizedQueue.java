package dataStructure;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * Similar to a stack or queue, but when items are removed, they are chosen
 * uniformly at randomly in the data structure.
 * 
 * @author Robert Morelli, Jedidiah Schmith
 *
 * @param <Item> is the type of the items that will be held in the
 *               RandomizedQueue data structure
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] queueArray;
	private int indexOfTail;

	public static void main(String nil[]) {

	}

	/**
	 * Is a Stack data structure that is intended to be used to locate the empty
	 * holes in the array of the RandomizedQueue data structure.
	 * 
	 * @param <StackType> is the type of the items that will be held in the Stack
	 *                    data structure
	 */

	@SuppressWarnings("unchecked")
	/**
	 * Constructs Randomized Queue data structure.
	 */
	public RandomizedQueue() {
		indexOfTail = -1;
		queueArray = (Item[]) new Object[1];
	}

	/**
	 * Determines if RandomizedQueue data structure is empty
	 * 
	 * @return true if RandomizedQueue data structure is empty
	 */
	public boolean isEmpty() {
		return indexOfTail == -1;
	}

	/**
	 * Finds the amount of items in RandomizedQueue data structure.
	 * 
	 * @return the number of items in RandomizedQueue data structure
	 */
	public int size() {
		return indexOfTail + 1;
	}

	/**
	 * Adds items to RandomizedQueue data structure.
	 * 
	 * @param item is the item to be added to the RandomizedQueue data structure.
	 */
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();

		int indexOfNewItem = ++indexOfTail;
		resizeArray();

		queueArray[indexOfNewItem] = item;
	}

	/**
	 * Removes a random item from the RandomizedQueue data structure.
	 * 
	 * @return an item that used to be held inside of the RandomizedQueue data
	 *         structure.
	 */
	public Item dequeue() {

		if (isEmpty())
			throw new java.util.NoSuchElementException();

		int randomIndex = StdRandom.uniform(size());

		Item itemToReturn = queueArray[randomIndex];

		queueArray[randomIndex] = queueArray[indexOfTail];
		queueArray[indexOfTail--] = null;
		resizeArray();
		return itemToReturn;
	}

	/**
	 * Chooses a random value held inside the RandomizedQueue data structure.
	 * 
	 * @return a random value found inside the RandomizedQueue data structure.
	 */
	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();

		int randomIndex = StdRandom.uniform(size());

		return queueArray[randomIndex];

	}

	@SuppressWarnings("unchecked")
	/**
	 * Resizes the Array that holds the data of the RandomizedQueue class.
	 */
	private void resizeArray() {
		if (size() >= queueArray.length || size() <= queueArray.length / 4) {
			Item[] newArray;
			int newArraySize;

			if (size() >= queueArray.length) {
				newArraySize = queueArray.length * 2;
			} else {
				newArraySize = (queueArray.length / 2);
			}
			newArray = (Item[]) new Object[newArraySize == 0 ? 1 : newArraySize];

			for (int i = 0; i < size(); i++) {
				newArray[i] = queueArray[i];
			}

			queueArray = newArray;
		}
	}

	@Override
	/**
	 * Iterates through the items inside the RandomizedQueue data structure
	 */
	public Iterator<Item> iterator() {
		return new QueueIterator<Item>(this.queueArray, size());
	}

}

/**
 * Is the iterator that iterates through a RandomizedQueue data structure.
 *
 * @param <IteratorType>
 */
class QueueIterator<IteratorType> implements Iterator<IteratorType> {

	int currentIndex;
	IteratorType[] array;

	/**
	 * Constructs the QueueItoerator.
	 * 
	 * @param queueArray the array of the RandomizedQueue data structure
	 * @param size       the number of items inside of the RandomizedQueue data
	 *                   structure
	 */
	public QueueIterator(IteratorType[] queueArray, int size) {

		array = (IteratorType[]) copyArray(queueArray, size);
		StdRandom.shuffle(array);
		currentIndex = 0;
	}

	@Override
	/**
	 * determines if there is a value next in the RandomizedQueue data structure
	 * 
	 * @return true if there is a value next in RandomizedQueue data structure
	 */
	public boolean hasNext() {
		if (array.length == 0)
			return false;

		// checks to see if the index at the end
		if (currentIndex == array.length)
			return false;
		else
			return true;

	}

	@Override
	/**
	 * gets the next value inside of the RandomizedQueue data structure
	 */
	public IteratorType next() {
		try {
			return array[currentIndex++];
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			throw new java.util.NoSuchElementException();
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * takes an array and returns a new array with the same values as the inputed
	 * one without empty spaces inside of it
	 * 
	 * @param inputArray an array to have null values removed
	 * @return a new array with no nulls.
	 */
	private IteratorType[] copyArray(IteratorType[] inputArray, int size) {
		IteratorType[] out = (IteratorType[]) new Object[size];
		for (int i = 0; i < size; i++) {

			out[i] = inputArray[i];

		}
		return out;
	}

}
