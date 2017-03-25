import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author YangLiu
 *
 */
public class PercolationStats {
    private double[] thresholds;
    private double mean;
    private double stddev;
    private double tempConfidence;

    /**
     * perform trials independent experiments on an n-by-n grid
     * 
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException("Illeagal Argument");
        thresholds = new double[trials];
        Percolation perc;
        int row, col;
        int time;
        for (int i = 0; i < trials; i++) {
            time = 0;
            perc = new Percolation(n);

            while (!perc.percolates()) {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    time++;
                }
            }
            thresholds[i] = (float) time / n / n;
        }
        mean = mean();
        stddev = stddev();
        tempConfidence = 1.96 * stddev / Math.sqrt(trials);
    }

    /**
     * 
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * 
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * 
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean - tempConfidence;
    }

    /**
     * 
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean + tempConfidence;
    }

    /**
     * test client (described below)
     * 
     * @param args
     */
    public static void main(String[] args) {
        PercolationStats pcl = new PercolationStats(100, 1000);
        System.out.println(pcl.tempConfidence);
    }
}
