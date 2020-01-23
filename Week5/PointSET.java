import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET {
	private SET<Point2D> RB_BST;

		   public PointSET(){
			   RB_BST = new SET<>();
		   }
		   
		   public boolean isEmpty(){
			  return (RB_BST.size() == 0);
		   }
		   
		   public int size() {
			   return RB_BST.size();
		   }
		   
		   public void insert(Point2D p) {
			   if (p == null) throw new IllegalArgumentException("Null 2d point entered!");
			   RB_BST.add(p);
		   }
		   
		   // does the set contain point p? 
		   public boolean contains(Point2D p) {
			   if (p == null) throw new IllegalArgumentException("Null 2d point entered!");
			   return  RB_BST.contains(p);
		   }
		   
		   // draw all points to standard draw 
		   public void draw() {
			   for(Point2D key: RB_BST) {
				   key.draw();
			   }
		   }
		   
		   // all points that are inside the rectangle (or on the boundary) 
		   public Iterable<Point2D> range(RectHV rect)  {
			   if (rect == null) throw new IllegalArgumentException("Null rectangle point entered!");
			   ArrayList<Point2D> arr = new ArrayList<>(); 
			   for (Point2D key: RB_BST) {
				   if (rect.contains(key)) {
					   arr.add(key);
				   }
			   }
			   return arr;
		   }
		   
		   // a nearest neighbor in the set to point p; null if the set is empty 
		   public Point2D nearest(Point2D p)  {
			   if (p == null) throw new IllegalArgumentException("Null 2d point entered!");
			   double minLen = 2.0;
			   Point2D returnPoint = null;
			   for (Point2D key: RB_BST) {
				   if (key.distanceTo(p) < minLen) {
					   minLen = key.distanceTo(p);
					   returnPoint = key;
				   }
			   }
			   return returnPoint;
		   }



	public static void main(String[] args) {
		PointSET points = new PointSET();
		
		StdDraw.setPenColor(StdDraw.RED);
		RectHV rectHV = new RectHV(0,0,0.3,0.3);
		rectHV.draw();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		
		points.insert(new Point2D(1.0, 0.0));
		System.out.println(points.nearest(new Point2D(1.0, 1.0)));
		
		points.insert(new Point2D(0.5,0.5));
		points.insert(new Point2D(0.3,0.3));
		points.insert(new Point2D(0.2,0.32));
		points.insert(new Point2D(0.1,0.15));
		points.insert(new Point2D(0.2,0.22));
		
//		set.isEmpty()  ==>  true
//        set.insert((1.0, 0.0))
//        set.nearest((1.0, 1.0))
		
		
		
		
		System.out.println(points.toString());
		
		points.draw();
		System.out.println(points.nearest(new Point2D(0.0,0.0)));

		for (Point2D key: points.range(rectHV)) {
			System.out.println(key.x() + " " + key.y() );
		}

	}


}
