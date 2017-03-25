import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.MinPQ;

/**
 * 
 * @author YangLiu
 *
 */
public class Solver {

    private MinPQ<Node> pq;
    private Node firstNode;
    private Node solveNode;
    private boolean isSolvable = true;

    /*
     * constructor of Solver find a solution to the initial board (using the A*
     * algorithm)
     */
    public Solver(Board initial) {
        firstNode = new Node();
        firstNode.board = initial;
        pq = new MinPQ<>(firstNode.getComparator());
        pq.insert(firstNode);

        if (firstNode.board.isGoal()) {
            solveNode = firstNode;
        } else {
            // for twin board
            Node firstNodeTwin = new Node();
            firstNodeTwin.board = initial.twin();
            MinPQ<Node> pqTwin = new MinPQ<>(firstNode.getComparator());
            pqTwin.insert(firstNodeTwin);
            // search the solution
            boolean flag = false;
            do {
                Node currentNode = pq.min();
                Node currentNodeTwin = pqTwin.min();
                pq.delMin();
                pqTwin.delMin();
                for (Board board : currentNode.board.neighbors()) {
                    if (currentNode != firstNode && board.equals(currentNode.prev.board))
                        continue;
                    if (board.isGoal()) {
                        flag = true;
                        solveNode = new Node(currentNode, board, currentNode.step + 1);
                        break;
                    }
                    pq.insert(new Node(currentNode, board, currentNode.step + 1));
                }
                for (Board board : currentNodeTwin.board.neighbors()) {
                    if (currentNodeTwin != firstNodeTwin && board.equals(currentNodeTwin.prev.board))
                        continue;
                    if (board.isGoal()) {
                        flag = true;
                        solveNode = new Node(currentNodeTwin, board, currentNodeTwin.step + 1);
                        isSolvable = false;
                        break;
                    }
                    pqTwin.insert(new Node(currentNodeTwin, board, currentNodeTwin.step + 1));
                }
            } while (!flag);
        }
        // clear up the result
        Node currentNode = solveNode;
        while (currentNode.prev != null) {
            currentNode.prev.next = currentNode;
            currentNode = currentNode.prev;
        }
    }

    /**
     * node in game tree and priority queue
     * 
     * @author YangLiu
     *
     */
    private class Node {
        public Node prev;
        public Board board;
        public Node next;
        public int step = 0;

        public Comparator<Node> getComparator() {
            return new Comparator<Node>() {

                @Override
                public int compare(Node o1, Node o2) {
                    // TODO Auto-generated method stub
                    return o1.board.manhattan() + o1.step - o2.board.manhattan() - o2.step;
                }
            };
        }

        public Node() {
            // TODO Auto-generated constructor stub
        }

        public Node(Node prev, Board board, int step) {
            this.prev = prev;
            this.board = board;
            this.step = step;
        }
    }

    /**
     * 
     * @return is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * 
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable())
            return -1;
        else
            return solveNode.step;
    }

    /**
     * 
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                // TODO Auto-generated method stub
                return new Iterator<Board>() {
                    Node currentNode = new Node();
                    {
                        currentNode.next = firstNode;
                    }

                    @Override
                    public Board next() {
                        // TODO Auto-generated method stub
                        if (!hasNext())
                            throw new NoSuchElementException();
                        currentNode = currentNode.next;
                        return currentNode.board;
                    }

                    @Override
                    public boolean hasNext() {
                        // TODO Auto-generated method stub
                        return currentNode.next != null;
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
    }
}