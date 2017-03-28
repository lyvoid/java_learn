package client;

import java.util.function.Function;

import yang.BST;
import yang.BinNode;

public class Test {
    public static void main(String[] args) {
        BST<Integer, String> test = new BST<>();
        test.insert(1, "128");
        test.insert(2, "123");
        test.insert(3, "123");
        test.insert(5, "123");
        test.insert(6, "123");
        test.remove(5);
//        test.midTravel(new Function<BinNode<Integer,String>, Boolean>() {
//            
//            @Override
//            public Boolean apply(BinNode<Integer, String> t) {
//                System.out.println();
//                return true;
//            }
//        });
        for (BinNode<Integer, String> binNode : test) {
            System.out.println(String.format("%s: %s", binNode.key, binNode.value));
        }
    }
}
