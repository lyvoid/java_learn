package agn1_1;

import java.util.Scanner;

/**
 * calculate number count in a sorted sequence between two numbers
 * @author yang
 *
 */
public class Main {
    public static void main(String[] args) {
        // handle input information
        Scanner in = new Scanner(System.in);
        int numCount = in.nextInt();
        int problemCount = in.nextInt();
        int[] nums = new int[numCount];
        for (int i = 0; i < numCount; i++) {
            nums[i] = in.nextInt();
        }
        int[][] problems = new int[problemCount][2];
        for (int i = 0; i < problemCount; i++) {
            problems[i][0] = in.nextInt();
            problems[i][1] = in.nextInt();
        }
        in.close();

        // solve problems
        for (int i = 0; i < problemCount; i++) {
            System.out.println(1 + get_max_index(problems[i][1], nums, 0, numCount)
                    - get_min_index(problems[i][0], nums, -1, numCount-1));
        }

    }

    public static int get_min_index(int a, int[] nums, int lo, int hi) {
        if (hi - lo == 1)
            return hi;
        int mi = (lo + hi) / 2;
        if (nums[mi] < a) {
            lo = mi;
        } else
            hi = mi;
        return get_min_index(a, nums, lo, hi);
    }

    public static int get_max_index(int a, int[] nums, int lo, int hi) {
        if (hi - lo == 1)
            return lo;
        int mi = (lo + hi) / 2;
        if (a < nums[mi]) {
            hi = mi;
        } else
            lo = mi;
        return get_max_index(a, nums, lo, hi);
    }
}
