import edu.princeton.cs.algs4.StdIn;
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rdq = new RandomizedQueue<>();
        for(int i=0;i<9;i++)
            rdq.enqueue(StdIn.readLine());
        int times = Integer.parseInt(args[0]);
        for(int i=0;i<times;i++){
            System.out.println(rdq.dequeue());
        }
    }
}
