package client;

import java.util.function.Function;

import yang.BinNode;
import yang.BinTree;

public class Test {
    public static void main(String[] args) {
        BinTree<Integer, String> test = new BinTree<>();
        test.insert(1, "128");
        test.insert(2, "123");
        test.insert(3, "123");
        test.insert(4, "123");
        test.insert(4, "123");
        test.remove(4);
        test.levelTravel(new Function<BinNode<Integer,String>, Boolean>() {
            @Override
            public Boolean apply(BinNode<Integer, String> t) {
                // TODO Auto-generated method stub
                System.out.println(String.format("%s: %s", t.key, t.value));
                return true;
            }
        });
    }
}
