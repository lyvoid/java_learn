package yang;

/**
 * 
 * @author yang
 *
 * @param <K>
 * @param <V>
 */
public class BinNode<K, V> {
    public K key;
    public V value;
    public BinNode<K, V> leftChild;
    public BinNode<K, V> rightChild;
    public BinNode<K, V> parent;
    public int height;

    public BinNode() {

    }

    public BinNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void insertLeft(K key, V value) {
        this.leftChild = new BinNode<K, V>(key, value);
        this.leftChild.parent = this;
    }

    public void insertRight(K key, V value) {
        this.rightChild = new BinNode<K, V>(key, value);
        this.rightChild.parent = this;
    }

    public void visit() {
        System.out.println(String.format("%s: %s", key, value));
    }

}
