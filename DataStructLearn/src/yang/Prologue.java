package yang;

public class Prologue {
    public static void main(String[] args) {

    }
    
    static public void pubbleSort(int[] nums, int lo, int hi) {
        while (!pubble(nums, lo, hi--))
            ;
    }

    static public boolean pubble(int[] nums, int lo, int hi) {
        boolean isSorted = true;
        while (++lo < hi) {
            if (nums[lo - 1] > nums[lo]) {
                isSorted = false;
                swap(nums, lo, lo - 1);
            }
        }
        return isSorted;
    }

    static public void swap(int[] nums, int i1, int i2) {
        nums[i1] += nums[i2];
        nums[i2] = nums[i1] - nums[i2];
        nums[i1] -= nums[i2];
    }

    static public int binaryOptSearch(int a, int[] nums, int lo, int hi) {
        int mi = 0;
        while (lo < hi) {
            mi = (lo + hi) / 2;
            if (a < nums[mi])
                hi = mi;
            else
                lo = mi + 1;
        }
        return lo - 1;
    }

    static public int binarySearch(int a, int[] nums, int lo, int hi) {
        int mi = 0;
        while (lo < hi) {
            mi = (lo + hi) / 2;
            if (a < nums[mi])
                hi = mi;
            if (nums[mi] < a)
                lo = mi + 1;
            if (nums[mi] == a)
                return mi;
        }
        return lo - 1;
    }

    static public int LCS2(String s1, String s2, int s1End, int s2End) {
        int[][] resultMatrix = new int[s1End + 1][s2End + 1];
        for (int i = 1; i < s1End + 1; i++) {
            for (int j = 1; j < s2End + 1; j++) {
                if (s2.charAt(j - 1) == s1.charAt(i - 1))
                    resultMatrix[i][j] = resultMatrix[i - 1][j - 1] + 1;
                else
                    resultMatrix[i][j] = resultMatrix[i - 1][j] > resultMatrix[i][j - 1] ? resultMatrix[i - 1][j]
                            : resultMatrix[i][j - 1];
            }
        }
        return resultMatrix[s1End][s2End];
    }

    static public int LCS1(String s1, String s2, int s1End, int s2End) {
        if (s1End == 0 || s2End == 0)
            return 0;
        if (s1.charAt(s1End - 1) == s2.charAt(s2End - 1)) {
            return LCS1(s1, s2, s1End - 1, s2End - 1) + 1;
        } else {
            int l1 = LCS1(s1, s2, s1End, s2End - 1);
            int l2 = LCS1(s1, s2, s1End - 1, s2End);
            return l1 > l2 ? l1 : l2;
        }
    }

    static public int[] maxTwo(int[] nums, int lo, int hi) {
        int[] result = new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE };
        if (hi - lo == 1) {
            result[0] = nums[lo];
            return result;
        }
        if (hi - lo == 2) {
            boolean isBigger = nums[hi - 1] > nums[lo];
            result[0] = isBigger ? nums[hi - 1] : nums[lo];
            result[1] = isBigger ? nums[lo] : nums[hi - 1];
            return result;
        }
        int divideIndex = lo + (hi - lo) / 2;
        int[] result1 = maxTwo(nums, lo, divideIndex);
        int[] result2 = maxTwo(nums, divideIndex, hi);
        boolean isBigger = result1[0] > result2[0];
        result[0] = isBigger ? result1[0] : result2[0];
        result[1] = isBigger ? (result1[1] > result2[0] ? result1[1] : result2[0])
                : (result2[1] > result1[0] ? result2[1] : result1[0]);
        return result;
    }
}


class Stack<T>{
    
    private Node top;
    private int size = 0;
    
    public void push(T value){
        Node node = new Node();
        node.value = value;
        node.prev = top;
        top = node;
        size++;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public T pop(){
        if(size > 0)size--;
        else return null;
        T temp = top.value;
        top = top.prev;
        return temp;
    }
    
    public int size(){
        return size;
    }
    
    private class Node{
        private T value;
        private Node prev;
    }
    
}
