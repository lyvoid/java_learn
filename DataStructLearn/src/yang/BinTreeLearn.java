package yang;

import java.util.LinkedList;
import java.util.Queue;
/**
 * 
 * @author yang
 *
 * @param <T>
 */
public class BinTreeLearn<T> {

    public static void main(String[] args) {
        BinTreeLearn<Integer> r = new BinTreeLearn<Integer>(-1);
        BinTreeLearn<Integer> currentNode = r;
        for (int i = 0; i < 3; i++) {
            (new BinTreeLearn<Integer>(i)).insertAsLC((currentNode));
            (new BinTreeLearn<Integer>(i*10)).insertAsRC((currentNode));
            currentNode = currentNode.lChild;
        }
        r.levelTravel();
    }

    public T data;
    public BinTreeLearn<T> parent;
    public BinTreeLearn<T> lChild;
    public BinTreeLearn<T> rChild;

    public BinTreeLearn(T data) {
        this.data = data;
    }

    public void insertAsLC(BinTreeLearn<T> p) {
        p.lChild = this;
        parent = p;
    }

    public void insertAsRC(BinTreeLearn<T> p) {
        p.rChild = this;
        parent = p;
    }

    public int size() {
        int size = 1;
        if (lChild != null)
            size += lChild.size();
        if (rChild != null)
            size += rChild.size();
        return size;
    }

    public void prevTravel() {
        System.out.println(this.data);
        if (lChild != null)
            lChild.prevTravel();
        if (rChild != null)
            rChild.prevTravel();
    }

    public void depthTravelByIter() {
        Stack<BinTreeLearn<T>> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            BinTreeLearn<T> currentNode = stack.pop();
            System.out.println(currentNode.data);
            if (currentNode.rChild != null)
                stack.push(currentNode.rChild);
            if (currentNode.lChild != null)
                stack.push(currentNode.lChild);
        }
    }
    
    public void prevTraveByIter(){
        Stack<BinTreeLearn<T>> stack = new Stack<>();
        visitAloneLeft(this, stack);
        while (!stack.isEmpty()) {
            visitAloneLeft(stack.pop(), stack);
        }
    }
    

    static public <M> void visitAloneLeft(BinTreeLearn<M> node, Stack<BinTreeLearn<M>> stack) {
        System.out.println(node.data);
        BinTreeLearn<M> leftNode = node;
        while (leftNode.lChild != null) {
            if (leftNode.rChild != null)
                stack.push(leftNode.rChild);
            leftNode = leftNode.lChild;
            System.out.println(leftNode.data);
        }
    }

    public void postTravel() {
        if (lChild != null)
            lChild.postTravel();
        if (rChild != null)
            rChild.postTravel();
        System.out.println(this.data);
    }
    
    public void midTravelIter(){
        BinTreeLearn<T> currentNode = this;
        Stack<BinTreeLearn<T>> stack = new Stack<>();
        do {
            leftStack(currentNode, stack);
            if (stack.isEmpty()) break;
            currentNode = stack.pop();
            System.out.println(currentNode.data);
            currentNode = currentNode.rChild;
        } while (true);
    }
    
    public static <M> void leftStack(BinTreeLearn<M> node, Stack<BinTreeLearn<M>> stack){
        BinTreeLearn<M> left = node;
        while (left != null){
            stack.push(left);
            left = left.lChild;
        }
    }
    
    public void levelTravel(){
        Queue<BinTreeLearn<T>> queue = new LinkedList<>();
        queue.offer(this);
        while(true){
            BinTreeLearn<T> currentNode = queue.poll();
            System.out.println(currentNode.data);
            if (currentNode.lChild != null) queue.offer(currentNode.lChild);
            if (currentNode.rChild != null) queue.offer(currentNode.rChild);
            if (queue.isEmpty()) break;
        }
    }

    public void midTravel() {
        if (lChild != null)
            lChild.midTravel();
        System.out.println(this.data);
        if (rChild != null)
            rChild.midTravel();
    }

}
