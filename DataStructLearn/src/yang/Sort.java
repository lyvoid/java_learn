package yang;

public class Sort {
    public static void main(String[] args) {
        int[] test = new int[]{1,2,2,2,4,4,4,5,3,3,3,3};
        bucketSort(test);
    }

    static public void bucketSort(int[] nums) {
        int min = 0;
        int max = 10;
        int[] result = new int[nums.length];
        int[] counts = new int[max - min + 1];
        for (int i : nums) {
            counts[i - min] ++;
        }
        for (int i=1; i<counts.length;i++){
            counts[i] += counts[i-1];
        }
        for (int i : nums) {
            result[--counts[i]] = i;
        }
        for (int i : result) {
            System.out.println(i);
        }
    }
}
