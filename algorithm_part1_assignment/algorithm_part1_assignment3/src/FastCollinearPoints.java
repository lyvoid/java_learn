import java.util.Arrays;

/**
 * finds all line segments containing 4 or more points
 * 
 * @author YangLiu
 *
 */
public class FastCollinearPoints {
    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("points should't be null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("point in points should't be null.");
        }
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }
        Arrays.sort(this.points);
        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i + 1]) == 0)
                throw new IllegalArgumentException("point in points should't repeat");
        }
    }

    /**
     * 
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments().length;
    }

    /**
     * 
     * @return the line segments
     */
    public LineSegment[] segments() {
        int length = points.length;
        LineSegment[] maxLineSegment = new LineSegment[length * (length - 1)];
        // some useful parameter in loops
        int countOfSegment = 0;
        int countOfSameSlope;
        Point thatPoint;
        Point[] pointsBySlope;
        // get line segment
        for (int i = 0; i < length; i++) {
            pointsBySlope = new Point[length - i - 1];
            // get points never used
            for (int j = 0; j < points.length - i - 1; j++) {
                pointsBySlope[j] = points[i + j + 1];
            }
            // sort points by slope between points[i]
            Arrays.sort(pointsBySlope, points[i].slopeOrder());
            // check slope value, and find three adjacent points with same
            // value
            countOfSameSlope = 0;
            for (int j = 0; j < pointsBySlope.length - 1; j++) {
                if (points[i].slopeOrder().compare(pointsBySlope[j], pointsBySlope[j + 1]) == 0) {
                    countOfSameSlope++;
                }
                if (points[i].slopeOrder().compare(pointsBySlope[j], pointsBySlope[j + 1]) != 0
                        || j == pointsBySlope.length - 2) {
                    if (countOfSameSlope >= 2) {
                        // check whether in a boundary situation
                        thatPoint = (j == pointsBySlope.length - 2
                                && points[i].slopeOrder().compare(pointsBySlope[j], pointsBySlope[j + 1]) == 0)
                                        ? pointsBySlope[j + 1] : pointsBySlope[j];
                        if (checkDuplicate(points, i, thatPoint)) {
                            maxLineSegment[countOfSegment] = new LineSegment(points[i], thatPoint);
                            countOfSegment++;
                        }
                    }
                    countOfSameSlope = 0;
                }
            }
        }
        LineSegment[] segments = new LineSegment[countOfSegment];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = maxLineSegment[i];
        }
        return segments;
    }

    /**
     * 
     * @return is input points has been detected.
     */
    private static boolean checkDuplicate(Point[] points, int currentPosition, Point thatPoint) {
        for (int i = 0; i < currentPosition; i++) {
            if (points[currentPosition].slopeOrder().compare(thatPoint, points[i]) == 0)
                return false;
        }
        return true;
    }
}