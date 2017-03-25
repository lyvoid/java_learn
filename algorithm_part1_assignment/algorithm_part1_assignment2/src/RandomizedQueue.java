import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 
 * @author YangLiu
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int length = 0;
    private Node startNode;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        startNode = new Node();
        startNode.next = new Node();
    }

    /**
     * 
     * @return is the queue empty?
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * 
     * @return return the number of items on the queue
     */
    public int size() {
        return length;
    }

    /**
     * add the item
     * 
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Attemps to add a null item.");
        else {
            Node temp = new Node();
            temp.item = item;
            temp.next = startNode.next;
            startNode.next = temp;
            length++;
        }
    }

    /**
     * 
     * @return remove and return a random item
     */
    public Item dequeue() {
        if (length == 0)
            throw new NoSuchElementException("Queue is empty.");
        Random random = new Random();
        int location = random.nextInt(length);
        Node currentNode = startNode;
        for (int i = 0; i < location; i++) {
            currentNode = currentNode.next;    
        }
        Node temp = currentNode.next;
        currentNode.next = currentNode.next.next;
        length--;
        return temp.item;
    }

    /**
     * 
     * @return return (but do not remove) a random item
     */
    public Item sample() {
        if (length == 0)
            throw new NoSuchElementException("Queue is empty.");
        Random random = new Random();
        int location = random.nextInt(length);
        Node currentItem = startNode;
        for (int i = 0; i < location; i++) {
            currentItem = currentItem.next;
        }
        return currentItem.item;
    }

    /**
     * @return return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int remain = length;
            private Item[] items;
            {
                Node temp = startNode;
                @SuppressWarnings("unchecked")
                Item[] items = (Item[]) new Object[length];
                for (int i = 0; i < length; i++) {
                    temp = temp.next;
                    items[i] = temp.item;
                }
                this.items = items;
            }

            @Override
            public boolean hasNext() {
                return remain != 0;
            }

            @Override
            public Item next() {
                // TODO Auto-generated method stub
                if (!hasNext())
                    throw new NoSuchElementException("Doesn't have next element.");
                Random random = new Random();
                int outputLocation = random.nextInt(remain);
                Item outputItem = items[outputLocation];
                remain--;
                if (hasNext()) {
                    items[outputLocation] = items[remain];
                }
                return outputItem;
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Not support remove function.");
            }
        };
    } //

    /**
     * 
     * @author YangLiu
     *
     */
    private class Node {
        Item item;
        Node next;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rdq = new RandomizedQueue<>();
        rdq.enqueue("s1");
        rdq.enqueue("s2");
        rdq.enqueue("s3");
        rdq.enqueue("s4");
        rdq.enqueue("s5");
        for (String string : rdq) {
            System.out.println(string);
        }
    }
}