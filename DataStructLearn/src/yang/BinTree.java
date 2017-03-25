package yang;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

public class BinTree<K, V> implements IBinTree<K, V> {

    private BinNode<K, V> root;
    public int size;

    public BinTree() {
        size = 0;
    }

    @Override
    public V search(K key) {
        return searchNode(key).value;
    }

    @Override
    public boolean insert(K key, V value) {
        Queue<BinNode<K, V>> queue = new LinkedList<>();
        BinNode<K, V> currentNode;
        if (root != null)
            queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            if (currentNode.leftChild == null) {
                currentNode.leftChild = new BinNode<>(key, value);
                currentNode.leftChild.parent = currentNode;
                return true;
            }
            if (currentNode.rightChild == null) {
                currentNode.rightChild = new BinNode<>(key, value);
                currentNode.rightChild.parent = currentNode;
                return true;
            }
            queue.offer(currentNode.leftChild);
            queue.offer(currentNode.rightChild);
        }
        root = new BinNode<>(key, value);
        return true;
    }

    @Override
    public boolean remove(K key) {
        if (removeAt(searchNode(key)) == null)return false;
        return true;
    }

    private BinNode<K, V> removeAt(BinNode<K, V> node) {
        if (node == null)
            return null;
        if (node.leftChild == null) {
            moveUp(node, node.rightChild);
            return node.rightChild;
        }
        if (node.rightChild == null) {
            moveUp(node, node.leftChild);
            return node.leftChild;
        }
        BinNode<K, V> succ = succ(node);
        swap(node, succ);
        removeAt(succ);
        return succ;
    }

    private BinNode<K, V> succ(BinNode<K, V> node) {
        node = node.rightChild;
        while (node.leftChild != null){
            node = node.leftChild;
        }
        return node;
    }

    private void swap(BinNode<K, V> node1, BinNode<K, V> node2) {
        V tmpV = node1.value;
        K tmpK = node1.key;
        node1.value = node2.value;
        node1.key = node2.key;
        node2.value = tmpV;
        node2.key = tmpK;
    }

    private void moveUp(BinNode<K, V> parent, BinNode<K, V> node) {
        if (parent.parent != null){
            if (parent.parent.leftChild == parent){
                parent.parent.leftChild = node;
            }
            if (parent.parent.rightChild == parent){
                parent.parent.rightChild = node;
            }
        }else{
                root = node;
        }
        if (node != null && node!=root){
            node.parent = parent.parent;
            node.leftChild = parent.leftChild;
            node.rightChild = parent.rightChild;
        }  
    }

    private BinNode<K, V> searchNode(K key) {
        return levelTravel(new Function<BinNode<K, V>, Boolean>() {

            @Override
            public Boolean apply(BinNode<K, V> t) {
                if (t.key.equals(key))
                    return false;
                return true;
            }
        });
    }

    /**
     * level travel by function visit that receive a BinNode input and return a
     * Boolean if return false then travel will be break and return current node
     * 
     * @param visit
     * @return null for normal traveling, or return a BinNode from break point
     */
    public BinNode<K, V> levelTravel(Function<BinNode<K, V>, Boolean> visit) {
        Queue<BinNode<K, V>> queue = new LinkedList<>();
        BinNode<K, V> currentNode;
        if (root != null)
            queue.offer(root);
        while (!queue.isEmpty()) {
            if (!visit.apply((currentNode = queue.poll())))
                return currentNode;
            if (currentNode.leftChild != null)
                queue.offer(currentNode.leftChild);
            if (currentNode.rightChild != null)
                queue.offer(currentNode.rightChild);
        }
        return null;
    }
}
