import edu.princeton.cs.algs4.StdDraw;

public class Test {
    public static void main(String[] args) {
//        StdDraw.setScale(0, 3);
//        StdDraw.line(1, 1, 2, 2);
//        StdDraw.line(1, 0, 2, 2);
        Test aTest = new Test();
        ex(aTest);
        System.out.println(aTest.a);
    }
    
    int a = 0;
    
    public static void ex(Test a) {
        //a = new Test();
        a.a = 1;
    }
}
