package lab07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

public class Graham {
	
	private Stack<Point> stack = null;
	
	public boolean isLeft(Point a, Point b, Point p) {
		return (a.x * b.y + a.y * p.x + b.x * p.y - a.x * p.y - b.x * a.y - b.y * p.x) < 0;
	}
	
	public void sort(LinkedList<Point> points, final Point pointR) {
		Collections.sort(points, new Comparator<Point>() {
			public int compare(Point pointA, Point pointB) {
				if (!isLeft(pointA, pointB, pointR)) {
					return -1;
				} else if(isLeft(pointA, pointB, pointR)) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}
	
	public Point findReferencePoint(LinkedList<Point> points) {
		Point referencePoint = points.getFirst();
		for (Point point : points) {
			if (point.y < referencePoint.y) {
				referencePoint = point;
			} else if (point.y == referencePoint.y && point.x < referencePoint.x) {
				referencePoint = point;
			}
		}
		return referencePoint;
	}
	
	public Stack<Point> graham(LinkedList<Point> points) {
		Point referencePoint = this.findReferencePoint(points);
		points.remove(referencePoint);
		
		this.sort(points, referencePoint);

		points.addFirst(referencePoint);
		
		this.stack = new Stack<Point>();
		
		stack.push(referencePoint);
		stack.push(points.get(1));
		stack.push(points.get(2));
		for (int i = 3; i < points.size(); i++) {
			Point pointA = points.get(i);
			Point pointB = stack.get(stack.size() - 1);
			Point pointR = stack.get(stack.size() - 2);
			while(!this.isLeft(pointA, pointB, pointR)){
				stack.pop();
				pointB = stack.get(stack.size() - 1);
				pointR = stack.get(stack.size() - 2);
			}
			stack.push(points.get(i));
		}
		return stack;
	}
	
	public LinkedList<Point> getPoints(String filePath) throws NumberFormatException, IOException {
		LinkedList<Point> points =  new LinkedList<Point>();
		BufferedReader reader = null;

		String line;
		String[] entry;
		reader = new BufferedReader(new FileReader(filePath));
		while ((line = reader.readLine()) != null) {
			entry = line.split(",");
			entry[0] = entry[0].trim();
			entry[1] = entry[1].trim();
			points.add(new Point(Double.valueOf(entry[0]), Double.valueOf(entry[1])));
		}
		reader.close();
			
		return points;
	}
	
	public void print() {
		Point p = null;
		if (this.stack != null) {
			while (!this.stack.empty()) {
				p = stack.pop();
				
				System.out.println(p.x + " " + p.y);
			}
		}
	}
}