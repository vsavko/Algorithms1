import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private int size;
	private KDPoint root;
	
	private class KDPoint{
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private KDPoint lb;        // the left/bottom subtree
		   private KDPoint rt;        // the right/top subtree

		public KDPoint(Point2D p, RectHV rect, KDPoint lb, KDPoint rt) {
			super();
			this.p = p;
			this.rect = rect;
			this.lb = lb;
			this.rt = rt;
		}

		public Point2D getP() {
			return p;
		}
		public RectHV getRect() {
			return rect;
		}
		public KDPoint getLb() {
			return lb;
		}
		public KDPoint getRt() {
			return rt;
		}
	}
	
	// construct an empty set of points 
	 public KdTree() {
		 this.size = 0;
		 root = null;
	 }
	 
	 // is the set empty? 
	   public boolean isEmpty() {
		   return (size == 0);
	   }
	   
	// number of points in the set 
	   public int size() {
		   return size;
	   }
	   
	// add the point to the set (if it is not already in the set)
	   public void insert(Point2D p) {
		   if (p == null) throw new IllegalArgumentException("Null 2d point entered!");
		   if (contains(p)) return;
		   int level = 1;
		   boolean isLeft = true;
		   KDPoint tempRoot = this.root, previous = null;
		   double xmin=0, xmax=1, ymin=0, ymax=1;
		   while(tempRoot != null) {
			   previous = tempRoot;
			   if (level % 2 == 1) { //odd, check the left/right side of the node
				   if (p.x() <= tempRoot.getP().x()) {
					   xmax = tempRoot.getP().x();
					   tempRoot = tempRoot.getLb();
					   isLeft = true;
				   }
				   else {
					   xmin = tempRoot.getP().x();
					   tempRoot = tempRoot.getRt();
					   isLeft = false;
				   }
			   }
			   else { //even, check the top/bottom side of the node
				   if (p.y() <= tempRoot.getP().y()) {
					   ymax = tempRoot.getP().y();
					   tempRoot = tempRoot.getLb();
					   isLeft = true;
				   }
				   else {
					   ymin = tempRoot.getP().y();
					   tempRoot = tempRoot.getRt();
					   isLeft = false;
				   }
			   }
			   ++level;
		   }
		   //System.out.println(xmin + " " +ymin + " " + xmax + " " + ymax);
		   RectHV rect = new RectHV(xmin,ymin,xmax,ymax);
		   tempRoot = new KDPoint(p, rect, null, null);
		   if (size == 0) root = tempRoot;
		   else {
			   if (isLeft)
				   previous.lb = tempRoot;
			   else
				   previous.rt = tempRoot;
		   }  
		   ++size;
	   }
	   
	   // does the set contain point p? 
	   public boolean contains(Point2D p) {
		   if (p == null) throw new IllegalArgumentException("Null 2d point entered!");
		   int level = 1;
		   KDPoint tempRoot = this.root;
		   while(tempRoot != null) {
			   if (tempRoot.getP().equals(p)) return true;
			   if (level % 2 == 1) { //odd, check the left/right side of the node
				   if (p.x() <= tempRoot.getP().x()) tempRoot = tempRoot.getLb();
				   else tempRoot = tempRoot.getRt();
			   }
			   else { //even, check the top/bottom side of the node
				   if (p.y() <= tempRoot.getP().y())  tempRoot = tempRoot.getLb();
				   else tempRoot = tempRoot.getRt();
				   }
			   ++level;
		   }
		   return false;
	   }
	   
	// draw all points to standard draw 
	   public void draw() {
		   
		   draw(this.root, true);
	   }
	   
	   private void draw(KDPoint root, boolean isHorizontal) {
		   if (root != null ) {
				draw(root.getLb(), !isHorizontal);
				StdDraw.setPenRadius(0.01);
				StdDraw.setPenColor(StdDraw.BLACK);
				root.getP().draw();
				StdDraw.setPenRadius(0.005);
				if (isHorizontal) {
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.line(root.getP().x(), root.getRect().ymin(), root.getP().x(), root.getRect().ymax());
				}
				else {
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.line(root.getRect().xmin(), root.getP().y(), root.getRect().xmax(), root.getP().y());
				}
				draw(root.getRt(), !isHorizontal);
		   }
	   }
	   
	   // all points that are inside the rectangle (or on the boundary) 
	   public Iterable<Point2D> range(RectHV rect){
		   if (rect == null) throw new IllegalArgumentException("Null rectangle entered!");
		   ArrayList<Point2D> pointsInside = new ArrayList<>();
		   getInsidePoints(this.root, rect, pointsInside);
		   return pointsInside;
	   }
	   
	   private void getInsidePoints(KDPoint root, RectHV rect, ArrayList<Point2D> returnList) {
		   if (root != null) {
			   if (rect.contains(root.getP()) ) returnList.add(root.getP());  
			   if (root.lb != null && rect.intersects(root.lb.getRect())) 
				   getInsidePoints(root.lb, rect, returnList);
			   if (root.rt != null && rect.intersects(root.rt.getRect()))
				   getInsidePoints(root.rt, rect, returnList);
		   }
	   }
	   
	   // a nearest neighbor in the set to point p; null if the set is empty 
	   public Point2D nearest(Point2D searchedPoint) {
		   if (searchedPoint == null ) throw new IllegalArgumentException("Null 2d point entered!");
		   if (root == null) return null;
		   Point2D nearest = null;
		   nearest = nearestRecursive(root, searchedPoint, root.getP());
		   return nearest;
	   }
	   
	   private Point2D nearestRecursive(KDPoint root, Point2D searchedPoint, Point2D smallest) {
		   Point2D returnPoint = smallest, tempL = null, tempR = null, temp = null;
		   double distance = smallest.distanceTo(searchedPoint);
		   KDPoint rootToCheckFirst, rootToCheckSecond;
		   if (root.getP().distanceTo(searchedPoint) < distance) {
			   returnPoint = root.getP();
			   distance = root.getP().distanceTo(searchedPoint);
		   }

		   //check if point is contained in the left/bottom or top/right rectangle  
		   //search the rectangle containing it first
		   if (root.lb == null && root.rt != null ) {
			    rootToCheckFirst = root.lb;
		  		rootToCheckSecond = root.rt;
		   }
		   else if (root.lb != null && root.rt == null ) {
			    rootToCheckFirst = root.rt;
		   		rootToCheckSecond = root.lb; 
		   }
		   else if (root.lb != null && root.rt != null && 
				   root.lb.getRect().distanceSquaredTo(searchedPoint) < root.rt.getRect().distanceSquaredTo(searchedPoint) ) {
			   rootToCheckFirst = root.lb;
			   rootToCheckSecond = root.rt;
		   }
		   else  {
			   rootToCheckFirst = root.rt;
			   rootToCheckSecond = root.lb; 
		   }	
		   
		   //check if distance from searched point to smallest
		   //is smaller than from searched point to rectangle	
		   
		   if (rootToCheckFirst != null && rootToCheckFirst.getRect().distanceTo(searchedPoint) < distance) {
			   tempL = nearestRecursive (rootToCheckFirst, searchedPoint, returnPoint);
		   } 
		   if (tempL != null)
		   
		   if (tempL != null && tempL.distanceTo(searchedPoint) < distance) {
			   distance = tempL.distanceTo(searchedPoint);
		   }

		   if (rootToCheckSecond != null && rootToCheckSecond.getRect().distanceTo(searchedPoint) < distance) {
			   tempR = nearestRecursive (rootToCheckSecond, searchedPoint, returnPoint);
		   }
		   
		   if (tempL == null && tempR != null)
			   temp = tempR;
		   else if(tempL != null && tempR == null)
			   temp = tempL;
		   else if(tempL != null && tempR != null) {
			   if (tempL.distanceSquaredTo(searchedPoint) < tempR.distanceSquaredTo(searchedPoint))
				   temp = tempL;
			   else
				   temp = tempR;
		   }
		   
		   if (temp != null &&  temp.distanceTo(searchedPoint) <= distance)
			   returnPoint = temp;
		   
		   return returnPoint; 
	   }



	public static void main(String[] args) {
		KdTree points = new KdTree();
	
		points.insert(new Point2D(0.7,0.2));
		points.insert(new Point2D(0.5,0.4));
		points.insert(new Point2D(0.2,0.3));
		points.insert(new Point2D(0.4,0.7));
		points.insert(new Point2D(0.9,0.6));

		
		System.out.println(points.size());
		
		points.draw();
		
		StdDraw.setPenRadius(0.015);
		Point2D testNearest = new Point2D(0.74,0.35);
		testNearest.draw();
		
		
		
		System.out.println("Nearest to " + points.nearest(testNearest));
		
		/*
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.005);
		RectHV rectHV = new RectHV(0,0,0.31,0.31);
		rectHV.draw();
		
		for (Point2D key: points.range(rectHV)) {
			System.out.println(key.x() + " " + key.y() );
		}*/

}
}
