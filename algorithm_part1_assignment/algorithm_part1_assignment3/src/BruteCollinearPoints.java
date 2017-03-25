import java.util.Arrays;

/**
 * 
 * @author YangLiu
 *
 */
public class BruteCollinearPoints {
    private Point[] points;

    /**
     * finds all line segments containing 4 points
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
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
     * the line segments
     * 
     * @return
     */
    public LineSegment[] segments() {
        int pointNumber = points.length;
        LineSegment[] maxLineSegment = new LineSegment[pointNumber * (pointNumber - 1)];
        int numberOfSegment = 0;
        for (int p = 0; p < pointNumber - 3; p++) {
            for (int q = p + 1; q < pointNumber; q++) {
                for (int r = q + 1; r < pointNumber; r++) {
                    for (int s = r + 1; s < pointNumber; s++) {
                        if (points[p].slopeOrder().compare(points[q], points[r]) == 0)
                            if (points[p].slopeOrder().compare(points[q], points[s]) == 0) {
                                maxLineSegment[numberOfSegment] = new LineSegment(points[p], points[s]);
                                numberOfSegment++;
                            }
                    }
                }
            }
        }
        LineSegment[] segments = new LineSegment[numberOfSegment];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = maxLineSegment[i];
        }
        return segments;
    }
}