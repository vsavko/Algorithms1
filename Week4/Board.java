import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	final private char[][] board;
	private int manhattanDist = 0;

	// create a board from an n-by-n array of tiles,
	// where tiles[n][n] = tile at (n, n)
	public Board(int[][] tiles) {
		
		if (tiles == null) throw new IllegalArgumentException("Board is null!");
		
		int n = tiles.length;
		int numOfTiles = (int) Math.pow(n, 2);
		board = new char[n][n];
		
		arrCopy2(board,tiles);



		for (int i = 0; i < numOfTiles; i++) {
			int number = board[i / n][i % n];
			if (number == 0) {
				continue;
			}
			int vertDist = Math.abs((number - 1) / n - i / n);
			int HorDist = Math.abs((number - 1) % n - i % n);
			manhattanDist += vertDist + HorDist;
		}

	}

	// string representation of this board
	public String toString() {
		int n = board.length;
		String outStr;
		outStr = Integer.toString(n) + "\n";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				outStr += Integer.toString(board[i][j]) + " ";
			}
			outStr += "\n";
		}
		return outStr;
	}

	// board dimension n
	public int dimension() {
		int n = board.length;
		return n;
	}

	// number of tiles out of place
	public int hamming() {
		int n = board.length;
		int hammingDist = 0;
		int numOfTiles = (int) Math.pow(n, 2);
		for (int i = 0; i < numOfTiles; i++) {
			if (numOfTiles > 1 && board[i / n][i % n] != (i + 1)) {
				if (board[i / n][i % n] != 0)
					++hammingDist;
			}
		}
		return hammingDist;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		return manhattanDist;
	}

	// is this board the goal board?
	public boolean isGoal() {
		if (manhattanDist == 0)
			return true;
		return false;
	}
	
	private boolean compArray(char [][] that) {
		for (int i = 0; i < that.length; i++) {
			for (int j = 0 ; j < that[1].length; j++) {
				if (board[i][j] != that[i][j]) {
						return false;
				}
			}
		}
		return true;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == null || !(y instanceof Board)) return false;
		Board board2 = (Board) y;
		if (board2.dimension() == this.dimension() && this.compArray(board2.board)) {
			return true;
		}
		return false;
	}

	private static void arrCopy(char[][] arrFrom, int[][] arrTo) {
		for (int i = 0; i < arrFrom.length; i++) {
			for (int j = 0; j < arrFrom[i].length; j++) {
				arrTo[i][j] = (int)arrFrom[i][j];
			}
		}
	}
	
	private static void arrCopy2(char[][] arrTo, int[][] arrFrom) {
		for (int i = 0; i < arrFrom.length; i++) {
			for (int j = 0; j < arrFrom[i].length; j++) {
				arrTo[i][j] = (char)arrFrom[i][j];
			}
		}
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		int n = board.length;
		ArrayList<Board> neighbours = new ArrayList<>();
		if (neighbours.size() == 0 && n > 0 && n > 0) {
			// create neighbours
			int[][] neighbourArr;
			int zeroLocation = 0; 
			int numOfTiles = (int) Math.pow(n, 2);
			
			for (int i = 0; i < numOfTiles; i++) {
				int number = board[i / n][i % n];
				if (number == 0) {
					zeroLocation = i;
					break;
				}
			}

			// up neighbour
			if (zeroLocation / n > 0) {
				neighbourArr = new int[n][n];
				arrCopy(board, neighbourArr);
				neighbourArr[zeroLocation / n][zeroLocation
						% n] = board[zeroLocation / n - 1][zeroLocation % n];
				neighbourArr[zeroLocation / n - 1][zeroLocation % n] = 0;
				neighbours.add(new Board(neighbourArr));
			}

			// left
			if (zeroLocation % n > 0) {
				neighbourArr = new int[n][n];
				arrCopy(board, neighbourArr);
				neighbourArr[zeroLocation / n][zeroLocation
						% n] = board[zeroLocation / n][zeroLocation % n - 1];
				neighbourArr[zeroLocation / n][zeroLocation % n - 1] = 0;
				neighbours.add(new Board(neighbourArr));
			}

			// right
			if (zeroLocation % n + 1 < n) {
				neighbourArr = new int[n][n];
				arrCopy(board, neighbourArr);
				neighbourArr[zeroLocation / n][zeroLocation
						% n] = board[zeroLocation / n][zeroLocation % n + 1];
				neighbourArr[zeroLocation / n][zeroLocation % n + 1] = 0;
				neighbours.add(new Board(neighbourArr));
			}

			// bottom
			if (zeroLocation / n + 1 < n) {
				neighbourArr = new int[n][n];
				arrCopy(board, neighbourArr);
				neighbourArr[zeroLocation / n][zeroLocation
						% n] = board[zeroLocation / n + 1][zeroLocation % n];
				neighbourArr[zeroLocation / n + 1][zeroLocation % n] = 0;
				neighbours.add(new Board(neighbourArr));
			}

		}
		return neighbours;
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		int n = board.length;
		Board twinBoard;
		int[][] twinArr;
		twinArr = new int[n][n];
		arrCopy(board, twinArr);
		if (n > 1 && n > 1) {
			int temp;
			int i;
			int firstNum = -1, SecondNum = -1;
			int numOfTiles = (int) Math.pow(n, 2);
			for (i = 0; i < numOfTiles; i++) {
				if (firstNum != -1 && twinArr[i / n][i % n] != 0) {
					SecondNum = i;
					break;
				}

				if (firstNum == -1 && twinArr[i / n][i % n] != 0) {
					firstNum = i;
				}
			}

			temp = twinArr[firstNum / n][firstNum % n];
			twinArr[firstNum / n][firstNum % n] = twinArr[SecondNum / n][SecondNum % n];
			twinArr[SecondNum / n][SecondNum % n] = temp;
		}

		twinBoard = new Board(twinArr);
		return twinBoard;
	}

	private static void randomizeTiles(int[][] tiles, int n, int numOfTiles) {
		for (int i = 0; i < numOfTiles; i++) {
			// random xchange with this and previous numbers
			int rand = (int) (Math.random() * (i + 1));
			int rowNumNum = rand / n;
			int colNumNum = rand % n;
			tiles[i / n][i % n] = i;
			int temp = tiles[rowNumNum][colNumNum];
			tiles[rowNumNum][colNumNum] = i;
			tiles[i / n][i % n] = temp;
		}
	}

	// unit testing (not graded)
	public static void main(String[] args) {
		int n = 3;
		int numOfTiles = (int) Math.pow(n, 2);
		int[][] tiles1 = new int[n][n];
		randomizeTiles(tiles1, n,  numOfTiles);
		int[][] tiles2 = new int[n][n];
		randomizeTiles(tiles2, n,  numOfTiles);

		Board board1 = new Board(tiles1);
		Board board2 = new Board(tiles2);
		
		//system.out.println(board1.equals(board1));

		/// System.out.println(Arrays.deepToString(tiles1));
		/*System.out.println("Board1 \n" + board1.toString());
		System.out.println("hamming " + board1.hamming());
		System.out.println("manhattan " + board1.manhattan());
		System.out.println("twin " + board1.twin().toString());

		System.out.println(board1.equals(board1));
		System.out.println(board1.equals(board2));*/

		System.out.println("Neighbours test");
		for (Board key : board1.neighbors()) {
			System.out.println(key.toString());
		}
		System.out.println("Neighbours test");
		//System.out.println("hamming " + board1.hamming());
		
		for (Board key : board1.neighbors()) {
			System.out.println(key.toString());
		}
		
		System.out.println("Neighbours test2222");
		//System.out.println("hamming " + board1.hamming());
		
		for (Board key : board1.neighbors()) {
			System.out.println(key.toString());
		}

	}

}
