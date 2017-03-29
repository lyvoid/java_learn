package yang;

/**
 * 
 * @author yang
 *
 * @param <K>
 * @param <V>
 */
public class AVLTree<K extends Comparable<K>, V> extends BST<K, V> {

    public AVLTree() {
        root = new BinNode<>();
        addVirtualNode(root);
    }

    @Override
    protected void insertAt(BinNode<K, V> node, K key, V value) {
        if (node == root) {
            node.leftChild.key = key;
            node.leftChild.value = value;
            node.leftChild.height = 1;
            addVirtualNode(node.leftChild);
            return;
        }
        BinNode<K, V> currentNode;
        if (key.compareTo(node.key) < 0) {
            currentNode = (node.leftChild = new BinNode<>(key, value));
            addVirtualNode(currentNode);
            currentNode.parent = node;
        } else if (key.compareTo(node.key) > 0) {
            currentNode = (node.rightChild = new BinNode<>(key, value));
            addVirtualNode(currentNode);
            currentNode.parent = node;
        } else {
            (currentNode = node).value = value;
        }
        for (; (currentNode = currentNode.parent) != root;) {
            if (!AVLBalanced(currentNode)) {
                rotateAt(tallerChild(tallerChild(currentNode)));
                break;
            } else {
                updateHeight(currentNode);
            }
        }
    }

    protected void addVirtualNode(BinNode<K, V> node) {
        node.height = 1;
        node.leftChild = new BinNode<>();
        node.leftChild.parent = node;
        node.rightChild = new BinNode<>();
        node.rightChild.parent = node;
    }

    protected BinNode<K, V> tallerChild(BinNode<K, V> node) {
        return node.leftChild.height >= node.rightChild.height ? node.leftChild : node.rightChild;
    }

    @Override
    protected void remove(BinNode<K, V> node) {
        if (node.leftChild.key == null) {
            replaceParent(node, node = node.rightChild);
        } else if (node.rightChild.key == null)
            replaceParent(node, node = node.leftChild);
        else {
            exchange(node, succeed(node));
            remove(succeed(node));
            return;
        }
        while ((node = node.parent) != root) {
            if (!AVLBalanced(node)) {
                rotateAt(node);
            }
            updateHeight(node);
        }
    }

    /**
     * 3+4 reconstitution
     * 
     * @param a
     * @param b
     * @param c
     * @param t1
     * @param t2
     * @param t3
     * @param t4
     * @return
     */
    protected BinNode<K, V> connect34(BinNode<K, V> a, BinNode<K, V> b, BinNode<K, V> c, BinNode<K, V> t1,
            BinNode<K, V> t2, BinNode<K, V> t3, BinNode<K, V> t4, BinNode<K, V> ancester, boolean isLeft) {
        b.parent = ancester;
        if (isLeft)
            ancester.leftChild = b;
        else
            ancester.rightChild = b;
        a.leftChild = t1;
        t1.parent = a;
        a.rightChild = t2;
        t2.parent = a;
        updateHeight(a);
        c.leftChild = t3;
        t3.parent = c;
        c.rightChild = t4;
        t4.parent = c;
        updateHeight(c);
        a.parent = b;
        c.parent = b;
        b.leftChild = a;
        b.rightChild = c;
        updateHeight(b);
        return b;
    }

    protected BinNode<K, V> rotateAt(BinNode<K, V> v) {
        BinNode<K, V> father = v.parent;
        BinNode<K, V> grandfather = father.parent;
        if (isLeftChild(father)) {
            if (isLeftChild(v)) {
                return connect34(v, father, grandfather, v.leftChild, v.rightChild, father.rightChild,
                        grandfather.rightChild, grandfather.parent, isLeftChild(grandfather));
            } else {
                return connect34(father, v, grandfather, father.leftChild, v.leftChild, v.rightChild,
                        grandfather.leftChild, grandfather.parent, isLeftChild(grandfather));
            }
        } else {
            if (isLeftChild(v)) {
                return connect34(grandfather, v, father, grandfather.leftChild, v.leftChild, v.rightChild,
                        father.rightChild, grandfather.parent, isLeftChild(grandfather));
            } else {
                return connect34(grandfather, father, v, grandfather.leftChild, father.leftChild, v.leftChild,
                        v.rightChild, grandfather.parent, isLeftChild(grandfather));
            }
        }
    }

    protected int height(BinNode<K, V> node) {
        return (node.height = (node.leftChild.height > node.rightChild.height ? node.leftChild.height
                : node.rightChild.height) + 1);
    }

    protected void updateHeight(BinNode<K, V> node) {
        while (node.key != null) {
            node.height = height(node);
            node = node.parent;
        }
    }

    private boolean AVLBalanced(BinNode<K, V> node) {
        if (Math.abs(node.leftChild.height - node.rightChild.height) > 1)
            return false;
        return true;
    }

}
