package client;

import yang.MaxPriorQueue;

public class Test {
    public static void main(String[] args) {
        Integer[] test = new Integer[]{1 ,2 ,1, 100,201,1,200,202};
        MaxPriorQueue<Integer> maxPriorQueue = new MaxPriorQueue<>(test);
        for (Integer integer : maxPriorQueue.binHeap) {
            System.out.println(integer);
        }
    }
}
