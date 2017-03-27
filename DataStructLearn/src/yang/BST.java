package yang;

import java.util.function.Function;

public class BST<K extends Comparable<K>, V> implements IBinTree<K, V> {
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
        // TODO Auto-generated method stub
        return false;
    }
    
    private BinNode<K, V> succeed(){
        return null;
    }

    public void midTravel(Function<BinNode<K, V>, Boolean> visit) {
        midTravelByIter(root.leftChild, visit);
    }

//    private void midTravel(BinNode<K, V> root, Function<BinNode<K, V>, Boolean> visit) {
//        if (root == null)
//            return;
//        if (root.leftChild != null)
//            midTravel(root.leftChild, visit);
//        visit.apply(root);
//        if (root.rightChild != null)
//            midTravel(root.rightChild, visit);
//    }

    private void midTravelByIter(BinNode<K, V> root, Function<BinNode<K, V>, Boolean> visit) {
        BinNode<K, V> currentNode = root;
        Stack<BinNode<K, V>> stack = new Stack<>();
        while (true){
            leftStack(currentNode, stack);
            if (stack.isEmpty())
                break;
            visit.apply((currentNode=stack.pop()));
            currentNode = currentNode.rightChild;
        }
    }
    
    private void leftStack(BinNode<K, V> node, Stack<BinNode<K, V>> stack){
        BinNode<K, V> currentNode = node;
        while (currentNode != null){
            stack.push(currentNode);
            currentNode = currentNode.leftChild;
        }
    }

}
