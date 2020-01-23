import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private Point[] points;
	private static  int nrOfSegments; 
	//private LineSegment[] lineArr;
	private ArrayList<LineSegment> lineArr;
	
	   // finds all line segments containing 4 points
	   public BruteCollinearPoints(Point[] points3) {
		   if (points3 == null) throw new IllegalArgumentException("null input"); 
		   
		   this.points = new Point[points3.length];
		   System.arraycopy(points3, 0, this.points, 0, points.length);
		   
		   for (int i = 0; i < this.points.length; i++) {
			   if (this.points[i] == null)
				   throw new IllegalArgumentException("Null point at position " + i);
		   }
		   
		   Arrays.sort(this.points);
		   
		   for (int i = 0; i < this.points.length; i++) {			   
			   if (i > 0) {
				   if (points[i].compareTo(points[i-1]) == 0 )
					   throw new IllegalArgumentException("same points");
			   }
		   }
		   
		   nrOfSegments = 0;
		   lineArr = new ArrayList<LineSegment>();  
		   
		   combinations(points, points.length, 0, 0, lineArr, null, null);
  
	   }
	   
	   private static boolean checkSameLine(Point startP, Point endP, Point newP) {
		   if (startP.slopeTo(newP) == endP.slopeTo(newP)) {
			  	return true;
		   }
		   return false;
	   }
	   
	   
	   private static void combinations(Point[] arr, int len, int startPos, int nrPoints, 
			   ArrayList<LineSegment> lineArr, Point startP, Point endP) {
		   if(nrPoints == 4) {
			   //chose 2 most distinct points  
			   lineArr.add(new LineSegment(startP, endP));
			   ++nrOfSegments;
		   }
		   
		   else {
			   for (int i = startPos; i < len; i++) {
				   
				   //first recursive layer
				   if (nrPoints == 0) {
					   startP = arr[i];
					   combinations(arr, len , i + 1, nrPoints +1, lineArr, startP, endP);  	
				   }
				   
				   //second recursive layer
				   else if (nrPoints == 1) {
					   endP = arr[i];
					   combinations(arr, len , i + 1, nrPoints +1, lineArr, startP, endP);  	
				   }
				   
				   //third and fourth
				   else if(nrPoints >= 2) {
					   if (checkSameLine(startP, endP, arr[i]) == true) {
						   if (endP.compareTo(arr[i]) < 0)
							   endP = arr[i];
						   combinations(arr, len, i + 1, nrPoints +1, lineArr, startP, endP);  	
					   }	   
				   }
				    
				  			   
			   }
		   }
	   }
	   

	   // the number of line segments
	   public int numberOfSegments() {
		   return nrOfSegments;
	   }
	   
	   // the line segments
	   public LineSegment[] segments() {
		   LineSegment[] array = new LineSegment[nrOfSegments];
		   int i = 0;
		   for (LineSegment p: lineArr) {
			   array[i++] = p;
		   }
		   return array;
	   }
	   
	   public static void main(String [] args) {
		   
		  /* Point[] points = new Point[10];
		   points[0] = new Point(0,0);
		   points[1] = new Point(1,1);
		   points[2] = new Point(2,2);
		   points[3] = new Point(3,3);
		   points[4] = new Point(7,5);
		   points[5] = new Point(10,5);
		   points[6] = new Point(11,5);
		   points[7] = new Point(12,5);
		   points[8] = new Point(13,51);
		   points[9] = new Point(75,585);

		   BruteCollinearPoints col = new BruteCollinearPoints(points);*/
		   
		   In in = new In(args[0]);
		    int n = in.readInt();
		    Point[] points = new Point[n];
		    for (int i = 0; i < n; i++) {
		        int x = in.readInt();
		        int y = in.readInt();
		        points[i] = new Point(x, y);
		    }

		    // draw the points
		    StdDraw.enableDoubleBuffering();
		    StdDraw.setXscale(0, 32768);
		    StdDraw.setYscale(0, 32768);
		    for (Point p : points) {
		        p.draw();
		    }
		    StdDraw.show();

		    // print and draw the line segments
		    BruteCollinearPoints brute = new BruteCollinearPoints(points);
		    for (LineSegment segment : brute.segments()) {
		        StdOut.println(segment);
		        segment.draw();
		    }
		    StdDraw.show();
		    

	   }
}
