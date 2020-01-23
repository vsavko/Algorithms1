import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private int minMoves = -1;
	private MinPQ<BoardPriority> priorityQ = new MinPQ<>();	
	private MinPQ<BoardPriority> priorityQTwin = new MinPQ<>();	
	private boolean isSolvable = false;
	private BoardPriority solutionBP = null;


	private class BoardPriority implements Comparable<BoardPriority>, Iterable<Board> {
		private Board board;
		private int moves, manhattan;
		private BoardPriority previousBoardPriority;
		
		
		private BoardPriority(Board board, int moves, BoardPriority previousBoardPriority ) {
			this.board = board;
			this.moves = moves;
			this.manhattan = this.board.manhattan();
			this.previousBoardPriority = previousBoardPriority;
		}

		public Board getBoard() {
			return board;
		}
		
		public BoardPriority getPreviousBoard() {
			return previousBoardPriority;
		}

		public int getMoves() {
			return moves;
		}

		@Override
		public int compareTo(BoardPriority that) {
			if (this.moves + this.manhattan < that.moves + that.manhattan)
				return -1;
			if (this.moves + this.manhattan > that.moves+ that.manhattan)
				return 1;
			return 0;
		}

		@Override
		public Iterator<Board> iterator() {
			return new priorityBoardIterator();
		}
	}
	
	private class priorityBoardIterator implements Iterator<Board>{
		private Stack<BoardPriority> solutionStack = new Stack<>();
		BoardPriority currentBoard = solutionBP;
		Board board = null;

		public priorityBoardIterator() {
			while(currentBoard != null) {
				solutionStack.push(currentBoard);
				currentBoard = currentBoard.previousBoardPriority;
			}
		}

		@Override
		public boolean hasNext() {	
			return (solutionStack.size() > 0);
		}

		@Override
		public Board next() {
			board = solutionStack.pop().getBoard();
;			return board;
		}
		
	}
	
	
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null ) throw new IllegalArgumentException("Board is null!");
		
		priorityQ.insert(new BoardPriority(initial,0, null));
		priorityQTwin.insert(new BoardPriority(initial.twin(),0, null ));
		
		while(priorityQ.size() > 0 && priorityQTwin.size() > 0) {
			
			if ((minMoves = checkboard(priorityQ)) != -1) {
				isSolvable = true;
				break;
			}
			
			if (checkboard(priorityQTwin) != -1) {
				isSolvable = false;
				solutionBP = null;
				break;
			}
		}
		
		priorityQ = null;
		priorityQTwin = null;
		
	}
	
	private int checkboard(MinPQ<BoardPriority> priorityQ ) {
		
		
		BoardPriority currentBoardPriority = priorityQ.delMin();
		Board currentBoard, parentBoard = null;
		int currentSteps;
		
		currentBoard = currentBoardPriority.getBoard();
		if (currentBoardPriority.getPreviousBoard() != null )
			parentBoard= currentBoardPriority.getPreviousBoard().getBoard();
		currentSteps = currentBoardPriority.getMoves();
		
		
		if (currentBoard.isGoal()) {
			solutionBP = currentBoardPriority;
			return currentSteps;
		}
		
		for (Board key: currentBoard.neighbors() ) {
			if (parentBoard == null || !parentBoard.equals(key)) //critical optimization
				priorityQ.insert(new BoardPriority(key,currentSteps +1, currentBoardPriority));
		}
		
		return -1;
	}
	
	

	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return isSolvable;
	}

	// min number of moves to solve initial board
	public int moves() {
		return minMoves;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		return solutionBP;	
	}

	// test client (see below)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] tiles = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            tiles[i][j] = in.readInt();
	    Board initial = new Board(tiles);

	    // solve the puzzle
	    Solver solver = new Solver(initial);
	    Solver solver2 = new Solver(initial);
	    
	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);


	    }

	}

}
