import java.util.Arrays;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial(new double[] {1, -1}, new int[] {0,1});
        Polynomial q = new Polynomial(new double[] {1, 1}, new int[] {0,1});
        Polynomial r = q.multiply(p);

        System.out.println(p);
        System.out.println(q);
        System.out.println(r);
    }
}
