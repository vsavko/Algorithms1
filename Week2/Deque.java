import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node{
		Item item;
		Node next;
		Node previous;
		
		public Node(Item item, Deque<Item>.Node next, Deque<Item>.Node previous) {
			super();
			this.item = item;
			this.next = next;
			this.previous = previous;
		}
	}
	
	private Node first, last;
	private int size;



    // construct an empty deque
    public Deque() {
    	this.first = null;
    	this.last = null;
    	this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException("Item should not be empty!");
    	if (isEmpty())
    		first = last = new Node(item, null,null);
    	else {
    	first.previous = new Node(item, first, null);
    	first = first.previous;
    	}
    	++this.size;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException("Item should not be empty!");
    	if (isEmpty())
    		first = last = new Node(item, null,null);
    	else {
    	last.next = new Node(item, null, last);
    	last = last.next;
    	}
    	++this.size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	Item temp;
    	if (isEmpty()) throw new java.util.NoSuchElementException("The deque is allready empty!");
    	temp = first.item;
    	first = first.next;
    	if (first == null)
    		last = null;
    	else
    		first.previous = null;
    	--this.size;
    	return temp;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	Item temp;
    	if (isEmpty()) throw new java.util.NoSuchElementException("The deque is allready empty!");
    	temp = last.item;
    	last = last.previous;
    	if (last == null)
    		first = null;
    	else
    		last.next = null;
    	--this.size;
    	return temp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	 return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
    	private Node current = first;
    	
    	public boolean hasNext() {
    		return current != null;
    	}
    	
    	public void remove() {
    		//not supported
    		throw new UnsupportedOperationException("Remove method is unsupported in the iterator!");
    	}
    	
    	public Item next() {
    		if (!hasNext()) throw new java.util.NoSuchElementException("The iterator is empty!");
    		Item item = current.item;
    		current = current.next;
    		return item;
    	}
    }
    


    // unit testing (required)
    public static void main(String[] args) {
    	/*Deque<String> deque = new Deque<String>();
    	System.out.println("Is empty " + deque.isEmpty());
    	
       	Iterator<String> i = deque.iterator();
    	while(i.hasNext()) {
    		String s = i.next();
    		System.out.println("Iterator " + s);
    	}
    	
    	deque.addFirst("test");
    	i = deque.iterator();
    	while(i.hasNext()) {
    		String s = i.next();
    		System.out.println("Iterator " + s);
    	}
    	
    	deque.addFirst("test1");
    	System.out.println(deque.removeFirst());
    	deque.addLast("test3");
    	System.out.println(deque.removeLast());
    	deque.addLast("test4");
    	
    	i = deque.iterator();
    	while(i.hasNext()) {
    		String s = i.next();
    		System.out.println("Iterator " + s);
    	}
    	
    	System.out.println("Is empty " + deque.isEmpty());
    	System.out.println("Size " + deque.size());*/
    	
    	Deque<Integer> deque = new Deque<Integer>();
    	         deque.addFirst(1);
    	         deque.removeLast();
    	         System.out.println("Size " + deque.size());
    	         
    	        	Iterator<Integer> i = deque.iterator();
    	        	while(i.hasNext()) {
    	        		Integer s = i.next();
    	        		System.out.println("Iterator " + s);
    	        	}
    	        		         
    	


    }

}