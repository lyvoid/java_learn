package yang;

public class StringMatch {

    public static void main(String[] args) {
        String s = "123456789p43r5ui03q42b9485jmj0cvf294u5rmweoaufjioaweps;uitrbm03vq498wi54u09dxqiirpjkwfdosa hjt0b oei";
        String t = "s;uitrbm03vq43-8w";
        System.out.println(bruteMatch(t, s, 0, s.length()));
    }

    public static int KMPMatch(String t, String s, int start, int end) {
        return 0;
    }
    
    private static int[] KMPTable(String t){
        return null;
    }

    public static int bruteMatch(String t, String s, int start, int end) {
        for (int i = start; i < end - t.length(); i++) {
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
