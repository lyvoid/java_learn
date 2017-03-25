import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author YangLiu
 *
 */
public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] openState;
    private int sizeOfGrid;

    /**
     * create n-by-n grid, with all sites blocked
     * 
     * @param n
     *            the number of grid size
     */
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Illeagal Argument");
        sizeOfGrid = n;
        n = n * n + 2;
        grid = new WeightedQuickUnionUF(n);
        openState = new boolean[n];
        openState[0] = true;
        openState[n - 1] = true;
    }

    /**
     * open site (row, col) if it is not open already
     * 
     * @param row
     *            row of site in grid
     * @param col
     *            col of site in grid
     * 
     */
    public void open(int row, int col) {
        validate(row, col);
        int currentLocation = (col - 1) * sizeOfGrid + row;
        openState[currentLocation] = true;
        // if col is one or size of Grid ,then connect current site with virtual
        // site
        if (row == 1)
            grid.union(currentLocation, 0);
        if (row == sizeOfGrid)
            grid.union(currentLocation, openState.length - 1);

        // if state of adjacent sites is open ,then calls to union
        if (row - 1 > 0 && isOpen(row - 1, col))
            grid.union(currentLocation, currentLocation - 1);
        if (row < sizeOfGrid && isOpen(row + 1, col))
            grid.union(currentLocation, currentLocation + 1);
        if (col - 1 > 0 && isOpen(row, col - 1))
            grid.union(currentLocation, currentLocation - sizeOfGrid);
        if (col < sizeOfGrid && isOpen(row, col + 1))
            grid.union(currentLocation, currentLocation + sizeOfGrid);
    }

    /**
     * is site (row, col) open?
     * 
     * @param row
     *            row of site
     * @param col
     *            col of site
     * @return the state of site
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        if (row < 1 || col < 1 || row > sizeOfGrid || col > sizeOfGrid)
            return false;
        else
            return openState[(col - 1) * sizeOfGrid + row];
    }

    /**
     * is site (row, col) full?
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return grid.connected(0, (col - 1) * sizeOfGrid + row);
        return false;
    }

    /**
     * 
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        int number = 0;
        for (boolean b : openState) {
            if (b)
                number++;
        }
        return number - 2;
    }

    /**
     * does the system percolate?
     * 
     * @return system percolate
     */
    public boolean percolates() {
        return grid.connected(0, openState.length - 1);
    }

    // validate that p is a valid index
    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > sizeOfGrid || col > sizeOfGrid) {
            throw new IndexOutOfBoundsException(
                    "row:" + row + "and col:" + col + " is not between 1 and " + sizeOfGrid);
        }
    }

    /**
     * test
     * 
     * @param args
     */
    public static void main(String[] args) {
        Percolation pc = new Percolation(10);
        pc.open(1, 5);
        pc.open(2, 6);
        pc.open(2, 5);
        System.out.println(pc.isFull(2, 6));
    }
}
