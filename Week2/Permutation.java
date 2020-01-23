import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	
	public static void main(String argc[]) {
		int i = 0;
		int k = 0;
		if (argc.length != 0)
			k = Integer.parseInt(argc[0]);
		//if ( k <= 0 ) throw new IllegalArgumentException("Argc should take a value of k!");

		String inputString = "";
		
		RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
		
		while(!StdIn.isEmpty()) {
			String text = StdIn.readString();
			rqueue.enqueue(text);
		}	
		
	  	Iterator<String> iter = rqueue.iterator();
    	while(iter.hasNext() && k-- > 0) {
    		String s = iter.next();
    		System.out.println(s);
    	}
		
	}
}
