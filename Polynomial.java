import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

class Polynomial {
    public double[] coeff;
    public int[] exponents;

    public Polynomial() {
        this.coeff = new double[] {0};
        this.exponents = new int[] {0};
    }

    public Polynomial(double coeff, int exponent) {
        this.coeff = new double[] {coeff};
        this.exponents = new int[] {exponent};
    }

    public Polynomial(double[] coeff, int[] exponents) {
        this.coeff = new double[coeff.length];
        this.exponents = new int[coeff.length];

        for (int i = 0; i < coeff.length; i++) {
            this.coeff[i] = coeff[i];
            this.exponents[i] = exponents[i]; 
        }

        Arrays.sort(this.exponents);
    }

    public Polynomial(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String str = scanner.nextLine();
            Polynomial p = Polynomial.parsePolynomial(str);
            this.coeff = p.coeff;
            this.exponents = p.exponents;
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("oopsie woopsie");
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(String fname) {
        try {
            FileWriter writer = new FileWriter(fname);
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean inArray(int[] array, int item) {
        for (int element: array)
            if (element == item)
                return true;
        return false;
    }
    
    private static int[] union(int[] a, int[] b) {
        int trim = 0, k = 0;
        for (int i: a) if (inArray(b, i)) trim++;

        int[] c = new int[a.length + b.length - trim];
        
        for (int i: a) if (!inArray(c, i)) c[k++] = i;
        for (int i: b) if (!inArray(c, i)) c[k++] = i;
        
        Arrays.sort(c);
        return c;
    }

    public double coefficientOf(int exponent) {
        for (int i = 0; i < exponents.length; i++)
            if (exponents[i] == exponent)
                return coeff[i];
        return 0;
    }

    public Polynomial[] terms() {
        Polynomial[] terms = new Polynomial[exponents.length];

        for (int i = 0; i < exponents.length; i++)
            terms[i] = new Polynomial(coeff[i], exponents[i]);

        return terms;
    }

    public Polynomial add(Polynomial that) {
        int[] exponents = union(this.exponents, that.exponents);
        double[] coeff = new double[exponents.length];

        for (int i = 0; i < exponents.length; i++)
            coeff[i] = this.coefficientOf(exponents[i]) + that.coefficientOf(exponents[i]);

        return new Polynomial(coeff, exponents);
    }

    public Polynomial multiply(Polynomial that) {
        if (that.exponents.length > 1) {
            Polynomial out = new Polynomial();

            for (Polynomial term: that.terms())
                out = out.add(this.multiply(term));

            return out;
        }

        double[] coeff = new double[this.coeff.length];
        int[] exponents = new int[this.exponents.length];

        for (int i = 0; i < this.exponents.length; i++) {
            coeff[i] = that.coeff[0] * this.coeff[i];
            exponents[i] = that.exponents[0] + this.exponents[i];
        }

        return new Polynomial(coeff, exponents);
    }

    public double evaluate(double x) {
        double value = 0;

        for (int i = 0; i < this.coeff.length; i++)
            value += coeff[i] * Math.pow(x, exponents[i]);

        return value;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    private static Polynomial parsePolynomialAux(String str) {
        if (str.contains("+")) {
            str = str.indexOf("+") == 0 ? "0.0" + str : str;
            Polynomial out = new Polynomial();

            for (String term: str.split("\\+"))
                out = out.add(Polynomial.parsePolynomialAux(term));

            return out;
        }

        if (!str.contains("x"))
            return new Polynomial(Double.parseDouble(str), 0);

        String[] info = str.split("x");

        if (str.indexOf("x") == 0)
            return new Polynomial(1.0, Integer.parseInt(info[1]));

        if (str.indexOf("x") == str.length() - 1)
            return new Polynomial(Double.parseDouble(info[0]), 1);

        return new Polynomial(Double.parseDouble(info[0]), Integer.parseInt(info[1]));
    }

    public static Polynomial parsePolynomial(String str) {
        str = str.replace("-x", "-1x").replace("-","+-");
        return Polynomial.parsePolynomialAux(str);
    }

    public String toString() {
        String s = "";
        int i;

        for (i = 0; i < this.coeff.length - 1; i++) {
            s += Double.toString(this.coeff[i]) + "x" + Integer.toString(this.exponents[i]) + "+";
        }

        s += Double.toString(this.coeff[i]) + "x" + Integer.toString(this.exponents[i]);

        return s;
    }
}
