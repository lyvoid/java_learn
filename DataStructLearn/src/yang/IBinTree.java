package yang;

public interface IBinTree<k, v> {

    /**
     * search the value of key in a binary tree, return the value of first node
     * found if can not find the key, return null
     * 
     * @param key
     *            of node
     * @return value of key
     */
    public v search(k key);

    /**
     * 
     * @param key
     * @param value
     * @return whether the node have not been replace by new node
     */
    public boolean insert(k key, v value);

    /**
     * remove a node from the tree by key. if the key not exist before, return false
     * else return true
     * 
     * @param key
     * @return whether the key exist before
     */
    public boolean remove(k key);

}
