import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * 
 * @author YangLiu
 *
 */
public class PointSET {
    private TreeSet<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * 
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * 
     * @return number of points in the set
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * 
     * @param p
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D point2d : points) {
            point2d.draw();
        }
    }

    /**
     * 
     * @param rect
     * @return all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        return new Iterable<Point2D>() {

            @Override
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {
                    int cursor = 0;
                    int count = 0;
                    Point2D[] pointsInsideRect = new Point2D[points.size()];
                    {
                        for (Point2D point2d : points) {
                            if (rect.contains(point2d)) {
                                pointsInsideRect[count] = point2d;
                                count++;
                            }
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return cursor < count;
                    }

                    @Override
                    public Point2D next() {
                        // TODO Auto-generated method stub
                        if (!hasNext())
                            throw new NoSuchElementException();
                        else {
                            cursor++;
                            return pointsInsideRect[cursor - 1];
                        }
                    }
                };
            }
        };
    }

    /**
     * 
     * @param p
     * @return a nearest neighbor in the set to point p; null if the set is
     *         empty
     */
    public Point2D nearest(Point2D p) {
        double minDistence = Double.MAX_VALUE;
        double currentDistence;
        Point2D nearestPoint = null;
        for (Point2D point2d : points) {
            currentDistence = p.distanceTo(point2d);
            if (currentDistence < minDistence) {
                minDistence = currentDistence;
                nearestPoint = point2d;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
    }
}