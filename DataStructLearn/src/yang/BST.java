package yang;

import java.util.Iterator;
import java.util.function.Function;

/**
 * 
 * @author yang
 *
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>, V> implements IBinTree<K, V>, Iterable<BinNode<K, V>> {
    private BinNode<K, V> root;
    private BinNode<K, V> hot;

    public BST() {
        root = new BinNode<>();
    }

    @Override
    public V search(K key) {
        BinNode<K, V> node = searchNode(key);
        if (node == null)
            return null;
        else
            return node.value;
    }

    /**
     * 
     * @param key
     * @return return node if key input find in the tree,else return null
     */
    private BinNode<K, V> searchNode(K key) {
        hot = root;
        BinNode<K, V> currentNode = root.leftChild;
        while (currentNode != null) {
            if (key.compareTo(currentNode.key) == 0)
                return currentNode;
            hot = currentNode;
            if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }
        return null;
    }

    @Override
    public boolean insert(K key, V value) {
        if (key == null)
            return false;
        if (searchNode(key) == null) {
            insertAt(hot, key, value);
            return true;
        }
        return false;
    }

    private void insertAt(BinNode<K, V> node, K key, V value) {
        if (node == root) {
            node.leftChild = new BinNode<>(key, value);
            node.leftChild.parent = root;
            return;
        }
        if (key.compareTo(node.key) < 0) {
            node.leftChild = new BinNode<>(key, value);
            node.leftChild.parent = node;
        } else if (key.compareTo(node.key) > 0) {
            node.rightChild = new BinNode<>(key, value);
            node.rightChild.parent = node;
        } else {
            node.value = value;
        }
    }

    @Override
    public boolean remove(K key) {
        BinNode<K, V> node = searchNode(key);
        // if key is not in the tree
        if (node == null)
            return false;
        remove(node);
        return true;
    }
    
    private void remove(BinNode<K, V> node){
        if (node.leftChild == null) {
            // if node only have a right child(include null)
            replaceParent(node, node.rightChild);
        } else if (node.rightChild == null) {
            // if node only have a left child
            replaceParent(node, node.leftChild);
        } else {
            // if node have both right and left child
            BinNode<K, V> successor = succeed(node);
            exchange(node, successor);
            remove(successor);
        }
    }

    private void exchange(BinNode<K, V> node1, BinNode<K, V> node2) {
        K keyTmp = node1.key;
        V valueTmp = node1.value;
        node1.key = node2.key;
        node1.value = node2.value;
        node2.key = keyTmp;
        node2.value = valueTmp;
    }

    /**
     * replace parent node by its child node
     * 
     * @param parent
     * @param child
     */
    private void replaceParent(BinNode<K, V> parent, BinNode<K, V> child) {
        if (parent.parent.leftChild == parent) {
            parent.parent.leftChild = child;
        } else if (parent.parent.rightChild == parent) {
            parent.parent.rightChild = child;
        }
        if (child != null)
            child.parent = parent.parent;
    }

    /**
     * 
     * @param node
     * @return immediate successor of node
     */
    private BinNode<K, V> succeed(BinNode<K, V> node) {
        node = node.rightChild;
        while (node.leftChild != null)
            node = node.leftChild;
        return node;

    }

    public void midTravel(Function<BinNode<K, V>, Boolean> visit) {
        midTravelByIter(root.leftChild, visit);
    }

    // private void midTravel(BinNode<K, V> root, Function<BinNode<K, V>,
    // Boolean> visit) {
    // if (root == null)
    // return;
    // if (root.leftChild != null)
    // midTravel(root.leftChild, visit);
    // visit.apply(root);
    // if (root.rightChild != null)
    // midTravel(root.rightChild, visit);
    // }

    private void midTravelByIter(BinNode<K, V> root, Function<BinNode<K, V>, Boolean> visit) {
        BinNode<K, V> currentNode = root;
        Stack<BinNode<K, V>> stack = new Stack<>();
        while (true) {
            leftStack(currentNode, stack);
            if (stack.isEmpty())
                break;
            visit.apply((currentNode = stack.pop()));
            currentNode = currentNode.rightChild;
        }
    }

    private void leftStack(BinNode<K, V> node, Stack<BinNode<K, V>> stack) {
        BinNode<K, V> currentNode = node;
        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.leftChild;
        }
    }

    @Override
    public Iterator<BinNode<K, V>> iterator() {
        return new Iterator<BinNode<K,V>>() {

            Stack<BinNode<K, V>> stack = new Stack<>();
            BinNode<K, V> currentNode = root.leftChild;
            {
                leftStack(currentNode, stack);
            }
            
            @Override
            public boolean hasNext() {
                if (stack.isEmpty())
                    return false;
                return true;
            }

            @Override
            public BinNode<K, V> next() {
                BinNode<K, V> node = stack.pop();
                leftStack(node.rightChild, stack);
                return node;
            }
        };
    }
    
    

}
