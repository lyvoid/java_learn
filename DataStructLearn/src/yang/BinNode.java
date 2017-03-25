package yang;

public class BinNode<K, V> {
    public K key;
    public V value;
    public BinNode<K, V> leftChild;
    public BinNode<K, V> rightChild;
    public BinNode<K, V> parent;

    public BinNode() {

    }

    public BinNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void insertLeft(K key, V value) {
        this.leftChild = new BinNode<K, V>(key, value);
    }

    public void insertRight(K key, V value) {
        this.rightChild = new BinNode<K, V>(key, value);
    }
    
    public void visit(){
        System.out.println(String.format("%s: %s", key, value));
    }
}
