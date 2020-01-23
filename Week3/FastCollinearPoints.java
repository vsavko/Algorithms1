import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
	private Point[] points;
	private Point[] points2;
	private int nrOfSegments; 
	private ArrayList<pointSlope> lineArr;
	private LineSegment[] lineArray;
	
	private class pointSlope implements Comparable<pointSlope>{
		
		public pointSlope(Point pointStart, Point pointEnd, double slope) {
			this.pointStart = pointStart;
			this.pointEnd = pointEnd;
			this.slope = slope;
		}
		
		public Point pointStart;
		public Point pointEnd;
		public double slope;

	    public Comparator<pointSlope> slopeOrder() {
	        return (o1, o2) -> Double.compare(o1.slope, o2.slope);
	    }
	    
	    @Override
	    public int compareTo(pointSlope p) {
	    	if (this.slope > p.slope)
	    		return 1;
	    	if (this.slope < p.slope)
	    		return -1;
	    	if (this.slope == p.slope) {
	    		if (this.pointEnd.compareTo(p.pointEnd) < 0)
					return -1;
	    		if (this.pointEnd.compareTo(p.pointEnd) > 0)
					return 1;
	    	}
	    	return 0;		
	    }
		
		   public String toString() {
		        return "start " + this.pointStart + " End " + this.pointEnd + " slope " + this.slope;
		    }
		
	}
	
		
	 // finds all line segments containing 4 or more points
	 public FastCollinearPoints(Point[] points3)   {
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
				   if (this.points[i].compareTo(this.points[i-1]) == 0 ) {
					   throw new IllegalArgumentException("same points");
				   }
			   }
		   }
		 
		 
		 Point[] points2 = new Point[this.points.length];
		 System.arraycopy(points, 0, points2, 0, points.length);
			
		 /**iterate through n
		  * find slopes
		  * sort by slopes
		  * find collinear
		  */
		 
		 Point endP;
		 lineArr = new ArrayList<pointSlope>();

		 //will it preserve stability?
		 for (Point p: this.points) {
			 Arrays.sort(points2, p.slopeOrder());
			 int count = 1;
			 endP = null;
			 boolean continueWithLine = false;
			 
			 for (int i = 0; i < points2.length; i++) {	
				 boolean allreadyExists = false;
				 double pSlope = p.slopeTo(points2[i]);

				 //check if such line doesnt exist allready
					/*for (pointSlope l : lineArr) {
						//if (l.pointStart.slopeTo(p) == l.slope && l.pointStart.slopeTo(points2[i]) == l.slope) {
						if (l.slope == pSlope) {
							if (l.pointStart.slopeTo(p) == l.slope) {
							count = 1;
							allreadyExists = true;
							break;
							}
						}
					}*/
					
				 if (allreadyExists) continue;

				 if (i >= 1) { // 
					double pSlope2 = p.slopeTo(points2[i-1]);
					if (pSlope == pSlope2) {
						++count;
						
						if (count == 2) { 
							if (points2[i].compareTo(points2[i-1]) > 0)
								endP = points2[i];
							else
								endP = points2[i-1];
						}
						else {
							if (points2[i].compareTo(endP) > 0) {
								endP = points2[i];
							}
						}
							
					}
					else {
						count = 1;
						continueWithLine = false;
					}	
				 }
				 if (count >= 3) {
					 if (!continueWithLine) {
						 ++nrOfSegments; 
						 lineArr.add(new pointSlope(p, endP, pSlope));
						 continueWithLine = true;
					 }
					 else {
						 lineArr.set(nrOfSegments-1, new pointSlope(p, endP, pSlope));
					 }
				 } 
			 }			 
		 }
		 
		 calcSegments();
	 }
	 
	   
	   private void calcSegments() {
		   pointSlope[] array = new pointSlope[nrOfSegments];
		   
		   int i = 0;
		   for (pointSlope p: lineArr) {
			   if (p.pointStart.compareTo(p.pointEnd) < 0 )
				   array[i++] = new pointSlope(p.pointStart, p.pointEnd, p.slope);
			   else
				   array[i++] = new pointSlope(p.pointEnd,p.pointStart, p.slope);
		   }
		    
		   Arrays.sort(array);
		   
		   LineSegment[] array2 = new LineSegment[i];
		   
		   int z = 0;
		   //add to new array if slope is different from last one, or if  slope is the same and end point is different
		   for(int j = 0; j < i; j++) {
			   if (j == 0) {
				   array2[z++] = new LineSegment(array[j].pointStart, array[j].pointEnd); 
			   }
			   else {
				   if ( (array[j].slope != array[j-1].slope) || (array[j].pointEnd != array[j-1].pointEnd) ){
					   array2[z++] = new LineSegment(array[j].pointStart, array[j].pointEnd); 
				   }
			   }
		   }
		   
		   LineSegment[] array3 = new LineSegment[z];
		   System.arraycopy(array2, 0, array3, 0, z);
		   
		   lineArray = array3;
		   nrOfSegments = z;
		   
	   }
	   
		  // the number of line segments
	   public int numberOfSegments() {
		   return nrOfSegments;
	   }
	   
	   // the line segments
	   public LineSegment[] segments()  {
		   LineSegment[] lineArrayCopy = new LineSegment[lineArray.length];
		   System.arraycopy(lineArray, 0, lineArrayCopy, 0, lineArray.length);
		   return lineArrayCopy;
	   }
	

	public static void main(String[] args) {
		   /*Point[] points = new Point[14];
		   points[4] = new Point(0,0);
		   points[5] = new Point(0,1);
		   points[6] = new Point(0,2);
		   points[7] = new Point(0,3);
		   points[10] = new Point(0,4);
		   points[11] = new Point(0,5);
		   points[12] = new Point(0,8);
		   points[0] = new Point(7,5);
		   points[1] = new Point(10,5);
		   points[13] = new Point(0,1000);
		   points[2] = new Point(11,5);
		   points[3] = new Point(12,5);
		   points[8] = new Point(13,51);
		   points[9] = new Point(75,585);

		   FastCollinearPoints col = new FastCollinearPoints(points);
		   
		   LineSegment[] arr = col.segments();
		   
		   System.out.println();
		   
		   for(LineSegment l : arr) {
			   System.out.println(l);
		   }*/
		
		// read the n points from a file
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
		   

	}

}
