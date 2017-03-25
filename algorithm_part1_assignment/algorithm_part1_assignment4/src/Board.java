import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 
 * @author YangLiu
 *
 */
public class Board {

    private final int[][] boardValues;
    private final int dimension;
    private int blankX;
    private int blankY;

    /**
     * construct a board from an n-by-n array of blocks (where blocks[i][j] =
     * block in row i, column j)
     * 
     * @param blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        boardValues = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boardValues[i][j] = blocks[i][j];
                if (boardValues[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }

        }
    }

    /**
     * 
     * @return board dimension n
     */
    public int dimension() {
        return dimension;
    }

    /**
     * 
     * @return number of blocks out of place
     */
    public int hamming() {
        int hamm = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardValues[i][j] == 0)
                    continue;
                if (boardValues[i][j] != i * dimension + j + 1)
                    hamm++;
            }
        }
        return hamm;
    }

    /**
     * 
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manh = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = boardValues[i][j];
                if (value == 0)
                    continue;
                manh += abs(getCorrectX(value) - i);
                manh += abs(getCorrectY(value) - j);
            }
        }
        return manh;
    }

    /**
     * 
     * @param value
     * @return correct x position of value in the board,x is between 0-7
     */
    private int getCorrectX(int value) {
        return (value - 1) / dimension;
    }

    /**
     * 
     * @param value
     * @return correct y position of value in the board, y is between 0-7
     */
    private int getCorrectY(int value) {
        return (value - 1) % dimension;
    }

    /**
     * 
     * @param a
     * @return absolute value of a
     */
    private static int abs(int a) {
        return a > 0 ? a : -a;
    }

    /**
     * 
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * 
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int x1, y1, x2, y2;
        x1 = blankX<dimension-1?blankX+1:blankX-1;
        x2 = x1;
        y1 = blankY;
        y2 = y1<dimension-1?y1+1:y1-1;
        return exch(x1, y1, x2, y2);
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return exchange the value of position(x1,y1) and (x2,y2)
     */
    private Board exch(int x1, int y1, int x2, int y2) {
        int[][] newBoardValues = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == x1 && j == y1)
                    newBoardValues[i][j] = boardValues[x2][y2];
                else if (i == x2 && j == y2)
                    newBoardValues[i][j] = boardValues[x1][y1];
                else
                    newBoardValues[i][j] = boardValues[i][j];
            }
        }
        return new Board(newBoardValues);
    }

    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;
        Board that = (Board) y;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                if (this.boardValues[i][j] != that.boardValues[i][j])
                    return false;
        }
        return true;
    }

    /**
     * 
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            private Board[] neighbors = new Board[4];
            private int length;
            {
                length = 0;
                if (blankX - 1 >= 0) {
                    neighbors[length] = exch(blankX, blankY, blankX - 1, blankY);
                    length++;
                }
                if (blankX + 1 < dimension) {
                    neighbors[length] = exch(blankX, blankY, blankX + 1, blankY);
                    length++;
                }
                if (blankY - 1 >= 0) {
                    neighbors[length] = exch(blankX, blankY, blankX, blankY - 1);
                    length++;
                }
                if (blankY + 1 < dimension) {
                    neighbors[length] = exch(blankX, blankY, blankX, blankY + 1);
                    length++;
                }
            }

            @Override
            public Iterator<Board> iterator() {
                // TODO Auto-generated method stub
                return new Iterator<Board>() {

                    int current = -1;

                    @Override
                    public Board next() {
                        // TODO Auto-generated method stub
                        if (!hasNext())
                            throw new NoSuchElementException();
                        current++;
                        return neighbors[current];
                    }

                    @Override
                    public boolean hasNext() {
                        // TODO Auto-generated method stub
                        if (current == 3 || neighbors[current + 1] == null)
                            return false;
                        return true;
                    }
                };
            }
        };
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%s", dimension));
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format(" %s", boardValues[i][j]));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }
}