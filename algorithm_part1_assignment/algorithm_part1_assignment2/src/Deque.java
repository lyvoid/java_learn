import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author YangLiu
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    private Node startNode;
    private Node endNode;
    private int length;

    /**
     * construct an empty deque
     */
    public Deque() {
        startNode = new Node();
        endNode = new Node();
        startNode.next = endNode;
        endNode.prev = startNode;
        length = 0;
    }

    /**
     * 
     * @return is the deque empty?
     */
    public boolean isEmpty() {
        if (length == 0)
            return true;
        return false;
    }

    /**
     * 
     * @return the number of items on the deque
     */
    public int size() {
        return length;
    }

    /**
     * add the item to the front Throw a java.lang.NullPointerException if item
     * is null
     * 
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("Attemps to add a null item.");
        else {
            Node temp = new Node();
            temp.item = item;
            temp.next = startNode.next;
            temp.prev = startNode;
            startNode.next.prev = temp;
            startNode.next = temp;
            length++;
        }
    }

    /**
     * add the item to the end Throw a java.lang.NullPointerException if item is
     * null
     * 
     * @param item
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Attemps to add a null item.");
        else {
            Node temp = new Node();
            temp.item = item;
            temp.prev = endNode.prev;
            temp.next = endNode;
            endNode.prev.next = temp;
            endNode.prev = temp;
            length++;
        }
    }

    /**
     * remove and return the item from the front throw a
     * java.util.NoSuchElementException if deque is empty
     * 
     * @return First item of deque
     */
    public Item removeFirst() {
        if (length == 0)
            throw new NoSuchElementException("Deque is empty.");
        Item temp = startNode.next.item;
        startNode.next = startNode.next.next;
        startNode.next.prev = startNode;
        length--;
        return temp;
    }

    /**
     * remove and return the item from the end throw a
     * java.util.NoSuchElementException if deque is empty
     * 
     * @return end item of deque
     */
    public Item removeLast() {
        if (length == 0)
            throw new NoSuchElementException("Deque is empty.");
        Item temp = endNode.prev.item;
        endNode.prev = endNode.prev.prev;
        endNode.prev.next = endNode;
        length--;
        return temp;
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node currentNode = startNode;

            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return currentNode.next != endNode;
            }

            @Override
            public Item next() {
                // TODO Auto-generated method stub
                if (!hasNext())
                    throw new NoSuchElementException("Doesn't have next element.");
                currentNode = currentNode.next;
                return currentNode.item;
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Not support remove function.");
            }

        };
    }

    /**
     * 
     * @author YangLiu
     *
     */
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("1.");
        deque.addLast("end-1");
        deque.addLast("end-2");
        deque.addLast("end-3");
        for (String string : deque) {
            System.out.println(string);
        }
    }

}