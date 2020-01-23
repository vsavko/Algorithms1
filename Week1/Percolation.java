import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean percArray[][];
	private WeightedQuickUnionUF UF;
	private WeightedQuickUnionUF Full;
	private int openSites;
	
	// constructor
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n < 1) throw new IllegalArgumentException("Grid size should be at least 1");	
    	
    	this.percArray = new boolean[n][n];
    	this.UF = new WeightedQuickUnionUF(n*n);
    	this.Full = new WeightedQuickUnionUF(n*n);
  		this.openSites = 0;

    	for (int i = 1; i < n; i++) {
    		Full.union(0,i);
    		UF.union(0, i);
    	}
    	
    	for (int i = n*n-n+1; i < n*n; i++) {
    		Full.union(n*n-n,i);
    	}
    	

    }
    
    private int findCell(int row, int col) {
    	int cell = row*percArray.length + col;
    	return cell;
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (row < 1 || col < 1) throw new IllegalArgumentException("values of col and "
    			+ "row must be bigger than 1");	
    	if (row > percArray.length || col > percArray.length) throw new IllegalArgumentException("values of col and "
    			+ "row must be smaller than size " + percArray.length);

		if (isOpen(row,col) == false)
			++openSites;
		else
			return;

    	--row;
    	--col;

    	this.percArray[row][col] = true;

    	//is connected top	
    	if (row > 0 && isOpen(row+1-1,col+1)) {
    		if ( UF.connected(findCell(row-1,col), findCell(row,col)) == false )
    			UF.union(findCell(row-1,col), findCell(row,col));
    		if ( Full.connected(findCell(row-1,col), findCell(row,col)) == false )
    			Full.union(findCell(row-1,col), findCell(row,col));
    	}
    	
    	//is connected bottom
    	if (row < this.percArray.length - 1 && isOpen(row+1+1,col+1)) {
    		if ( UF.connected(findCell(row+1,col), findCell(row,col)) == false )
    			UF.union(findCell(row+1,col), findCell(row,col));
    		if ( Full.connected(findCell(row+1,col), findCell(row,col)) == false )
    			Full.union(findCell(row+1,col), findCell(row,col));
    	}
    	
    	//is connected left
    	if (col > 0  && isOpen(row+1,col+1-1)) {
    		if ( UF.connected(findCell(row,col-1), findCell(row,col)) == false)
    			UF.union(findCell(row,col-1), findCell(row,col));
    		if ( Full.connected(findCell(row,col-1), findCell(row,col)) == false)
    			Full.union(findCell(row,col-1), findCell(row,col));
    	}
    	
    	//is connected right
    	if (col < this.percArray[0].length - 1 && isOpen(row+1,col+1+1)) {
      		if ( UF.connected(findCell(row,col+1), findCell(row,col)) == false )
      			UF.union(findCell(row,col+1), findCell(row,col));
      		if ( Full.connected(findCell(row,col+1), findCell(row,col)) == false )
      			Full.union(findCell(row,col+1), findCell(row,col));
    	} 
    	
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	if (row < 1 || col < 1) throw new IllegalArgumentException("1 values of col and "
    			+ "row must be bigger than 1");	
    	if (row > percArray.length || col > percArray.length) throw new IllegalArgumentException("values of col and "
    			+ "row must be smaller than size " + percArray.length);	
    	--row;
    	--col;
    	
    	if (this.percArray[row][col] == true)
    		return true;
    	return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	if (row < 1 || col < 1) throw new IllegalArgumentException(" values of col and "
    			+ "row must be bigger than 1");	
    	if (row > percArray.length || col > percArray.length) throw new IllegalArgumentException("values of col and "
    			+ "row must be smaller than size " + percArray.length);	
    	
    	if (isOpen(row,col) == false)
    			return false;
    	
    	--row;
    	--col;

    	if (UF.connected(0, findCell(row, col)))
				return true;
    	return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	int n = this.percArray.length;
		if (percArray.length == 1){
			if (isOpen(1,1))
				return true;
			else
				return false;
		}
		return Full.find(0) == Full.find(n * n - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
    	//empty method
    	

    }
}
