package yang;

import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        int[] test = new int[] { 4, 4, 4, 4, 5, 3, 3 };
        // quickSort(test, 0, test.length);
        // for (int i : test) {
        // System.out.println(i);
        // }
        System.out.println(majEleCandidateByiter(test));
        System.out.println(selectMajEle(test));
    }

    static public void bucketSort(int[] nums) {
        int min = 0;
        int max = 10;
        int[] result = new int[nums.length];
        int[] counts = new int[max - min + 1];
        for (int i : nums) {
            counts[i - min]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        for (int i : nums) {
            result[--counts[i]] = i;
        }
        for (int i : result) {
            System.out.println(i);
        }
    }

    public static void quickSort(int[] nums, int lo, int hi) {
        if (hi - 1 <= lo)
            return;
        Random rd = new Random();
        swap(nums, lo, lo + rd.nextInt(hi - lo));
        int pivot = nums[lo];
        int mi = lo + 1;
        for (int i = mi; i < hi; i++) {
            if (nums[i] < pivot)
                swap(nums, mi++, i);
        }
        swap(nums, lo, mi - 1);
        quickSort(nums, lo, mi);
        quickSort(nums, mi, hi);
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static int selectMajEle(int[] nums) {
        int count = 0;
        int candidate = majEleCandidateByRecusion(nums, 0, nums.length);
        for (int i : nums) {
            if (i == candidate)
                count++;
        }
        if (count >= nums.length / 2)
            return candidate;
        return -1;
    }

    private static int majEleCandidateByRecusion(int[] nums, int lo, int hi) {
        if (lo == hi - 1) {
            return nums[lo];
        }
        int count = 0;
        for (int i = lo; i < hi; i++) {
            if (nums[i] == nums[lo]) {
                count++;
            }
            if (count == (i - lo + 1) / 2) {
                return majEleCandidateByRecusion(nums, i, hi);
            }
        }
        return nums[lo];
    }

    private static int majEleCandidateByiter(int[] nums) {
        int lo = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[lo] == nums[i])
                count++;
            if (count == (i - lo + 1) / 2) {
                lo = i;
                count = 0;
            }
        }
        return nums[lo];
    }

}
