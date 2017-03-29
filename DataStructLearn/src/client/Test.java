package client;

import yang.AVLTree;
import yang.BinNode;

public class Test {
    public static void main(String[] args) {
        AVLTree<Integer, String> test = new AVLTree<>();
        test.insert(1, "128");
        test.insert(2, "123");
        test.insert(3, "123");
        test.insert(5, "123");
        test.insert(6, "123");
        test.insert(7, "123");
        test.insert(8, "123");
        test.insert(9, "123");
        test.insert(10, "123");
        test.remove(10);
        // test.midTravel(new Function<BinNode<Integer,String>, Boolean>() {
        //
        // @Override
        // public Boolean apply(BinNode<Integer, String> t) {
        // System.out.println();
        // return true;
        // }
        // });
        for (BinNode<Integer, String> binNode : test) {
            System.out.println(String.format("%s: %s height:%s", binNode.key, binNode.value, binNode.height));
        }
    }
}
