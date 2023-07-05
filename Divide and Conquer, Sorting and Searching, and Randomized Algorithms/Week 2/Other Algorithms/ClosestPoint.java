package Week2;

import java.util.Arrays;
import java.util.Comparator;

// A divide and conquer program in Java to find the findClosest pair the produce the smallest distance from a given set of points and also .

// A structure to represent a Point in 2D plane
class Point {
    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // The main function that finds the smallest distance
    // This method mainly uses closestUtil()
    public static double findClosest(Point[] P, int n, Point[] closestPair) {
        Arrays.sort(P, 0, n, new PointXComparator());

        return closestUtil(P, 0, n, closestPair);
    }

    // A recursive function to find the smallest distance.
    // The array P contains all points sorted according to x coordinate
    public static double closestUtil(Point[] P,int start,int end, Point[] closestPair)
    {
        int pointCount = end - start;

        // If there are 2 or 3 points, then use brute force
        if (pointCount <= 3) {
            return bruteForce(P, end, closestPair);
        }

        // Find the middle point
        int mid = start + pointCount / 2;
        Point midPoint = P[mid];

        /*
        * Consider the vertical line passing through the middle point
        * calculate the smallest distance dl on left of middle point and dr on right side
        */
        double dl = closestUtil(P, start, mid, closestPair);
        double dr = closestUtil(P, mid, end, closestPair);

        // Find the smaller of two distances
        double d = Math.min(dl, dr);

        // Build an array strip[] that contains points close (closer than d) to the line passing through the middle point
        Point[] strip = new Point[end];
        int j = 0; // Pointer on the strip array to fill it
        for (int i = 0; i < end; i++)
        {
            if (Math.abs(P[i].x - midPoint.x) < d)
                strip[j++] = P[i];
        }

        double minDistance = d;
        Point[] closestSplitPair = new Point[2];
        double d_split = stripClosest(strip, j, d,closestSplitPair);

        /*
        * Update the minDistance if the distance from stripClosest is the smallest
        * And also update the findClosest pair with the findClosest split pair
        */
        if (d_split < minDistance)
        {
            minDistance = d_split;
            closestPair = Arrays.copyOf(closestSplitPair,2);
        }

        return minDistance;
    }

    // A utility function to find the distance between two points
    public static double distance(Point p1, Point p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // A Brute Force method to return the smallest distance between two points in P[] of size n
    public static double bruteForce(Point[] P, int n, Point[] closestPair) {
        double minDistance = Double.MAX_VALUE;
        double dist = 0;

        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                dist = distance(P[i], P[j]);
                if (dist < minDistance)
                {
                    minDistance = dist;
                    closestPair[0] = P[i];
                    closestPair[1] = P[j];
                }
            }
        }
        return minDistance;
    }

    /*
    * A utility function to find the distance between the findClosest points of strip of given size.
    * All points in strip[] are sorted according to y coordinate. They all have an upper bound on minimum distance as d.
    * Note that this method has Order of growth of O(n) as the inner loop runs at most 6 times
     */
    public static double stripClosest(Point[] strip, int size, double d, Point[] closestSplitPair)
    {
        double minDistance = d; // Initialize the minimum distance as d

        Arrays.sort(strip, 0, size, new PointYComparator());

        // Pick all points one by one and try the next points till the difference between y coordinates is smaller than d.
        // This is a proven fact that this loop runs at most 6 times
        for (int i = 0; i < size; i++)
        {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < minDistance; j++)
            {
                if (distance(strip[i], strip[j]) < minDistance)
                {
                    minDistance = distance(strip[i], strip[j]);
                    closestSplitPair[0] = strip[i];
                    closestSplitPair[1] = strip[j];
                }
            }
        }

        return minDistance;
    }


}

// A structure to represent a Point in 2D plane
class PointXComparator implements Comparator<Point> {

    // Needed to sort array of points according to X coordinate
    @Override
    public int compare(Point pointA, Point pointB) {
        return Integer.compare(pointA.x, pointB.x);
    }

}

class PointYComparator implements Comparator<Point> {

    // Needed to sort array of points according to Y coordinate
    @Override
    public int compare(Point pointA, Point pointB) {
        return Integer.compare(pointA.y, pointB.y);
    }

}

public class ClosestPoint {

    public static void main(String[] args) {
        Point[] P = new Point[]{
                new Point(2, 3),
                new Point(12, 30),
                new Point(2, 4),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)

        };

        Point[] closestPair = new Point[2];

        System.out.println("The smallest distance: " + Point.findClosest(P, P.length,closestPair));
        System.out.println("Closest pair: (" + closestPair[0].x + ", " + closestPair[0].y + "), " +
                                         "(" + closestPair[1].x + ", " + closestPair[1].y + ")");
    }

}
