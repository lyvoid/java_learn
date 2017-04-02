package agn1_3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Light[] lights = new Light[n];
        for (int i = 0; i < n; i++) {
            lights[i] = new Light(in.nextInt(), in.nextInt());
        }
        sortx(lights, 0, n);
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (lights[i].y < lights[j].y)
                    count++;
            }
        }
        System.out.println(count);
    }

    private static void sortx(Light[] lights, int lo, int hi) {
        if (hi - lo < 2)
            return;
        int mi = lo + 1;
        Light pivot = lights[lo];
        int end = hi;
        for (; mi < end;) {
            if (pivot.x < lights[mi].x){
                swap(lights, mi, --end);
            }else{
                mi++;
            }
        }
        swap(lights, lo, mi-1);
        sortx(lights, lo, mi);
        sortx(lights, mi, hi);
    }

    private static void swap(Light[] lights, int i, int j) {
        Light tmp = lights[i];
        lights[i] = lights[j];
        lights[j] = tmp;
    }
}

class Light {
    int x;
    int y;

    public Light(int x, int y) {
        this.x = x;
        this.y = y;
    }
}