import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;// root of the KDT

    /**
     * construct an empty set of points
     */
    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 
     * @return number of points in the set
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point insert to tree can't be null.");
        root = insert(root, p, Point2D.X_ORDER);
    }

    private Node insert(Node node, Point2D p, Comparator<Point2D> c) {
        if (node == null)
            return new Node(p, c, 1);
        c = node.comparator.equals(Point2D.X_ORDER) ? Point2D.Y_ORDER : Point2D.X_ORDER;
        if (node.comparator.compare(node.point, p) < 0)
            node.left = insert(node.left, p, c);
        else if (node.comparator.compare(node.point, p) < 0)
            node.right = insert(node.right, p, c);
        else {
            if (!node.point.equals(p))
                node.right = insert(node.right, p, c);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * 
     * @param p
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        Node currentNode = root;
        while (currentNode != null) {
            int cmp = currentNode.comparator.compare(currentNode.point, p);
            if (cmp < 0)
                currentNode = currentNode.left;
            else if (cmp > 0)
                currentNode = currentNode.right;
            else if (currentNode.point.equals(p))
                return true;
            else
                currentNode = currentNode.right;
        }
        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.setScale(0, 1);
        draw(null, root, 0);
    }

    private void draw(Node father, Node node, int boundary) {
        double x0, y0, x1, y1;
        if (node == null)
            return;
        if (father == null) {
            x0 = node.point.x();
            x1 = x0;
            y0 = 0;
            y1 = 1;
        } else if (node.comparator.equals(Point2D.X_ORDER)) {
            x0 = node.point.x();
            x1 = x0;
            y0 = father.point.y();
            y1 = boundary;
        } else {
            y0 = node.point.y();
            y1 = y0;
            x0 = father.point.x();
            x1 = boundary;
        }
        StdDraw.line(x0, y0, x1, y1);
        draw(node, node.left, 1);
        draw(node, node.right, 0);
    }

    /**
     * 
     * @param rect
     * @return all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points = new SET<>();
        detect(root, rect, points);
        return new Iterable<Point2D>() {

            @Override
            public Iterator<Point2D> iterator() {
                // TODO Auto-generated method stub
                return points.iterator();
            }
        };
    }

    private void detect(Node node, RectHV rect, SET<Point2D> points) {
        if (node == null)
            return;
        if (rect.contains(node.point))
            points.add(node.point);
        RectHV detectRectRight;
        RectHV detectRectLeft;
        if (node.comparator.equals(Point2D.X_ORDER)) {
            detectRectRight = new RectHV(0, 0, node.point.x(), 1);
            detectRectLeft = new RectHV(node.point.x(), 0, 1, 1);
        } else {
            detectRectRight = new RectHV(0, 0, 1, node.point.y());
            detectRectLeft = new RectHV(0, node.point.y(), 1, 1);
        }
        if (rect.intersects(detectRectLeft)) {
            detect(node.left, rect, points);
        }
        if (rect.intersects(detectRectRight)) {
            detect(node.right, rect, points);
        }
    }

    /**
     * 
     * @param p
     * @return a nearest neighbor in the set to point p; null if the set is
     *         empty
     */
    public Point2D nearest(Point2D p) {
        if (root == null)
            return null;
        Point2D nearest = root.point;
        return find(root, p, nearest);
    }

    private Point2D find(Node node, Point2D p, Point2D nearestPoint) {
        if (node == null)
            return nearestPoint;
        if (p.distanceTo(node.point) < p.distanceTo(nearestPoint))
            nearestPoint = node.point;
        RectHV detectRectRight;
        RectHV detectRectLeft;
        if (node.comparator.equals(Point2D.X_ORDER)) {
            detectRectRight = new RectHV(0, 0, node.point.x(), 1);
            detectRectLeft = new RectHV(node.point.x(), 0, 1, 1);
        } else {
            detectRectRight = new RectHV(0, 0, 1, node.point.y());
            detectRectLeft = new RectHV(0, node.point.y(), 1, 1);
        }
        double disSquare = p.distanceTo(nearestPoint) * p.distanceTo(nearestPoint);
        if (detectRectLeft.distanceSquaredTo(p) < disSquare) {
            nearestPoint = find(node.left, p, nearestPoint);
        }
        if (detectRectRight.distanceSquaredTo(p) < disSquare) {
            nearestPoint = find(node.right, p, nearestPoint);
        }
        return nearestPoint;
    }

    private class Node {
        public Node left;
        public Node right;
        public Point2D point;
        public int size;
        public final Comparator<Point2D> comparator;

        public Node(Point2D point2d, Comparator<Point2D> comparator, int size) {
            this.point = point2d;
            this.size = size;
            this.comparator = comparator;
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.574219, 0.718750));
        kdTree.insert(new Point2D(0.574209, 0.718750));
        kdTree.insert(new Point2D(0.574208, 0.718750));
        System.out.println(kdTree.size());
    } // unit testing of the methods (optional)
}
