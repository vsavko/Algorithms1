import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {	
	
	private double results[];
	private int experiments;
	private double mean, std, coLo, coHi;
		
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if (n <= 0 || trials <= 0) throw new IllegalArgumentException("values of n and "
    			+ "trials must be bigger than 0");	
    	
    	experiments = trials;
    	results = new double[trials];

    	int z = 0;
    	
    	for (int i = 0; i < trials; i++) {
    		
    		Percolation model = new Percolation(n);
    		
    		for(int j = 0; j < n*n; j++) {
    			
	   			 int randomNum = StdRandom.uniform(n*n);
	   			 int randCol = randomNum % n + 1;
	   			 int randRow = randomNum / n + 1;
	   			 
				 while(model.isOpen(randRow, randCol)) {
					 randomNum = StdRandom.uniform(n*n);
					 randCol = randomNum % n + 1;
					 randRow = randomNum / n + 1;    
				 }
				 
    		     model.open(randRow, randCol);
    		     
    		     if (j >= n-1) {
    		    	if (model.percolates()) {

    		    		results[z++] = ((double)model.numberOfOpenSites()) / (n*n) ;
    		    		model = null;
    		    		break;
    		    	}
    		     }
    		}
    	}
    	
    	mean = StdStats.mean(results);
    	std = StdStats.stddev(results);
    	coLo = mean - 1.96*std/Math.sqrt(experiments);
    	coHi = mean + 1.96*std/Math.sqrt(experiments);
    	
        	
    }
    
    
    public double mean() {
    	return mean;
    }
    
    public double stddev() {
    	return std;
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return coLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return coHi;
    }

   // test client (see below)
   public static void main(String[] args) {
	   int N = Integer.parseInt(args[0]);
	   int T = Integer.parseInt(args[1]);
	//  Stopwatch timer = new Stopwatch();
	   
   	//   System.out.println("time1 " + timer.elapsedTime());
   	
	   PercolationStats experiment =  new PercolationStats(N,T);
	   	 double mean = experiment.mean();
	   	 double std = experiment.stddev();
	     System.out.println("mean = " + mean);
	     System.out.println("stddev = " + std);
	     System.out.println("95% confidence interval = [" + experiment.confidenceLo() + ", "
	     + experiment.confidenceHi() + "]");
	   
	   
	 //  System.out.println("time2 " + timer.elapsedTime());
	   
   }
}
