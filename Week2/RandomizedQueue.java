import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int queueSize; 
	private int stackSize; 
	private int queueFilled; 
	private int stackFilled; 
	private int nextCell; //cell used to write new queue element
	
	//create a stack for empty values in the queue
	private int[] stackTable; //stores the empty field numbers of queue
	private Item[] queueTable;
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	queueSize = 1;
    	stackSize = 1;
    	queueFilled = 0;
    	stackFilled = 0;
    	stackTable = new int[1];
    	queueTable = (Item[]) new Object[1];
    	queueTable[0] = null;
    	nextCell = 0;
    }
    
    private void doubleQueue() {
    	Item[] newArray = (Item[]) new Object[queueSize * 2];
    	for (int i = 0; i < this.queueSize; i++ ) {
    		if (this.queueTable[i] != null)
    			newArray[i] = this.queueTable[i];
    	}
    	this.queueSize *= 2;
    	this.queueTable = newArray;
    	newArray = null;
    }
    
    private void doubleStack() {
    	int[] newStack = new int[queueSize * 2];
    	for (int i = 0; i < this.stackSize; i++ ) {
    		if (this.stackTable[i] != 0)
    			newStack[i] = this.stackTable[i];
    	}
    	this.stackSize *= 2;
    	this.stackTable = newStack;
    	newStack = null;
    }
    
    private void halveQueue() {
    	Item[] newArray = (Item[]) new Object[queueSize / 2];
    	int j = 0;
    	for (int i = 0; i < this.queueSize; i++ ) {
    		if (this.queueTable[i] != null) {
    			newArray[j++] = this.queueTable[i];
    		}
    	}
    	this.nextCell = j;
    	this.stackSize = 1;
    	this.stackFilled = 0;
    	this.stackTable = new int[1];
    	this.queueSize /= 2;
    	this.queueTable = newArray;
    	newArray = null;
    }
    
    private void halveStack() {
    	int[] newStack = new int[stackSize / 2];
    	for (int i = 0; i < this.stackFilled; i++ ) {
    			newStack[i] = this.stackTable[i];
    	}
    	this.stackSize /= 2;
    	this.stackTable = newStack;
    	newStack = null;
    }
    
    private void pushStack(int value) {
    	if (stackFilled >= stackSize) 
    		doubleStack();
    	this.stackTable[stackFilled] = value;
    	++stackFilled;	
    }
    
    private int popStack() {
    	if (stackFilled == 0) throw new IllegalArgumentException ("poping from an empty stack!");
    	if (this.stackFilled < this.stackSize/4 )
    		halveStack();
    	return this.stackTable[--stackFilled];
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
    	return queueFilled == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return queueFilled;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException ("Cant call enqueue() with a null argument.!");
    	int queueNum;
    	if (stackFilled > 0) {
    		queueNum = popStack();
    		queueTable[queueNum] = item;
    		//System.out.println("Item " + item + " enqued in " + queueNum);
    	}
    	else {
    		if (queueFilled >= queueSize) 
    			doubleQueue();
    		queueTable[nextCell++] = item;
    		//System.out.println("Item " + item + " enqued in " + (nextCell-1));
    	}
    	++queueFilled;
    }
    

    // remove and return a random item
    public Item dequeue() {
    	if (queueFilled < 1) throw new java.util.NoSuchElementException("Queue is empty!");
    	int chosenElement = -1;
    	Item temp;
    	
    	chosenElement = StdRandom.uniform(queueFilled);
    	
    	//swap
    	temp = queueTable[chosenElement]; 
    	if (chosenElement != queueFilled-1) {
    		queueTable[chosenElement] = queueTable[queueFilled-1];
    		queueTable[queueFilled-1] = null;
    	}
    	else
    		queueTable[chosenElement] = null;
    	
    	--queueFilled;
    	--nextCell;
    	
    	if (this.queueFilled < this.queueSize/4 ) {
    		halveQueue();
    	}
    	return temp;	
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (queueFilled < 1) throw new java.util.NoSuchElementException("Queue is empty!");
    	int chosenElement;
    	Item chosenItem; 
    	
    	chosenElement = StdRandom.uniform(queueFilled);
    	chosenItem = queueTable[chosenElement];
    	return chosenItem;	
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	 return new ListIterator();
    }
    
    
    private class ListIterator implements Iterator<Item>{
    	
    	private int nextTable[], checkTable[];
    	private int nextElement = 0;
 
    	
    	public ListIterator(){
    		this.nextTable = new int[queueFilled];
    		for (int i = 0 ; i < queueFilled; i++ )
    		{
    			nextTable[i] = i;
    		}
    		StdRandom.shuffle(nextTable);
    	}
    	
    	public boolean hasNext() {
    		return nextElement < queueFilled;
    	}
    	
    	public void remove() {
    		//not supported
    		throw new UnsupportedOperationException("Remove method is unsupported in the iterator!");
    	}
    	
    	public Item next() {
    		if (!hasNext()) throw new java.util.NoSuchElementException("Queue is empty!");
    		return queueTable[nextTable[nextElement++]];
    	}
    }

    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    	  rq.enqueue(48);
          rq.enqueue(558);
          System.out.println(rq.sample());
          rq.dequeue();

          System.out.println(rq.isEmpty());

          rq.enqueue(14);

    }

}