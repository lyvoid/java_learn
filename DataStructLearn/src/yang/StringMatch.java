package yang;

public class StringMatch {

    public static void main(String[] args) {
        String s = "000000000001";
        String t = "00001";
        System.out.println(bruteMatch(t, s, 0, s.length()));
        System.out.println(KMPMatch(t, s, 0, s.length()));
    }

    public static int KMPMatch(String t, String s, int start, int end) {
        int[] table = KMPTable(t);
        for (int i : table) {
            System.out.println(String.format("-%s", i));
        }
        for (int i = start, j = 0; i < end - start;) {
            if (j < 0 || t.charAt(j) == s.charAt(i)) {
                i++;
                j++;
            } else {
                j = table[j];
            }
            if (j == t.length())
                return i - t.length();
        }
        return -1;
    }

    private static int[] KMPTable(String t) {
        int size = t.length();
        int[] table = new int[size];
        table[0] = -1;
        int j = 0;
        int k = -1;
        while (j < size - 1) {
            if (k < 0 || t.charAt(j) == t.charAt(k)) {
                table[++j] = ++k;
            } else {
                k = table[k];
            }
        }
        int count = 0;
        for (int i = size - 1; i > 0; i--) {
            if (t.charAt(i) == t.charAt(i - 1)){
                count++;
            }
            if (i == 1 || !(t.charAt(i) == t.charAt(i - 1))) {
                if (table[i + count - 1] > i - 1)
                    table[i + count - 1] = i - 1;
                count = 0;
            }
        }
        return table;
    }

    public static int bruteMatch(String t, String s, int start, int end) {
        for (int i = start; i < end - t.length() + 1; i++) {
            for (int j = 0; j < t.length(); j++) {
                if (t.charAt(j) != s.charAt(i + j))
                    break;
                if (j == t.length() - 1)
                    return i;
            }
        }
        return -1;
    }
}
