package yang;

import java.util.Arrays;
import java.util.Vector;

/**
 * 
 * @author yang
 *
 * @param <T>
 */
public class MaxPriorQueue<T extends Comparable<T>> implements IPriorQueue<T> {

    public Vector<T> binHeap;
    private int size = 0;

    public MaxPriorQueue() {
        binHeap = new Vector<>();
    }

    public MaxPriorQueue(T[] valus) {
        binHeap = new Vector<>(Arrays.asList(valus));
        size = valus.length;
        for (int i = (size >> 1) - 1; i > -1; i--) {
            underPercolate(i);
        }
    }

    @Override
    public T getTop() {
        return binHeap.get(0);
    }

    @Override
    public void add(T data) {
        binHeap.addElement(data);
        upwardPercolate(binHeap.size() - 1);
        size++;
    }

    @Override
    public void remove(int pos) {
        binHeap.set(pos, binHeap.get(size - 1));
        binHeap.remove(size - 1);
        size--;
        underPercolate(pos);
    }

    private void upwardPercolate(int pos) {
        int father;
        T tmp = binHeap.get(pos);
        while (pos != 0 && (tmp.compareTo(binHeap.get(father = (((pos + 1) >> 1) - 1)))) > 0) {
            binHeap.set(pos, binHeap.get(father));
            // swap(pos, father);
            pos = father;
        }
        binHeap.set(pos, tmp);
    }

    private void underPercolate(int pos) {
        int child;
        T tmp = binHeap.get(pos);
        while ((child = solveDisorder(pos, tmp)) > 0) {
            pos = child;
        }
        binHeap.set(pos, tmp);
    }

    private int solveDisorder(int pos, T element) {
        boolean isDisorder = false;
        int lChild;
        // if does not have a left child
        if (!((lChild = ((pos + 1 << 1) - 1)) < size)) {
            return -1;
        }
        // if original node value less than its left child
        if (element.compareTo(binHeap.get(lChild)) < 0) {
            isDisorder = true;
        }
        int rChild = lChild + 1;
        // if has a right child and original node value less than right child
        if (rChild < size && element.compareTo(binHeap.get(rChild)) < 0) {
            // if left child is not large than original node or right child is large than left child
            if (!isDisorder || binHeap.get(rChild).compareTo(binHeap.get(lChild)) > 0) {
                // right child ascend
                binHeap.set(pos, binHeap.get(rChild));
                return rChild;
            }
        }
        // if left child is large than original node and right child is not large than original node
        if (isDisorder) {
            // left child ascend
            binHeap.set(pos, binHeap.get(lChild));
            return lChild;
        }
        return -1;
    }

    // private void swap(int pos1, int pos2) {
    // T tmp = binHeap.get(pos1);
    // binHeap.set(pos1, binHeap.get(pos2));
    // binHeap.set(pos2, tmp);
    // }

    @Override
    public int getSize() {
        return size;
    }
}
