import java.io.File;
import java.util.Arrays;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial(new File("p.txt"));
        Polynomial q = new Polynomial(new File("q.txt"));
        p.multiply(q).saveToFile("product.txt");
    }
}
