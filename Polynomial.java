class Polynomial {
    public double[] coeff;

    public Polynomial() {
        this.coeff = new double[] {0};
    }

    public Polynomial(double[] coeff) {
        this.coeff = new double[coeff.length];
        for (int i = 0; i < coeff.length; i++)
            this.coeff[i] = coeff[i];
    }

    public int degree() {
        return coeff.length - 1;
    }

    public Polynomial add(Polynomial that) {
        int max = Math.max(this.coeff.length, that.coeff.length);
        int min = Math.min(this.coeff.length, that.coeff.length);

        double[] coeff = new double[max];

        for (int i = 0; i < max; i++) {
            if      (this.coeff.length <= i) coeff[i] = that.coeff[i];
            else if (that.coeff.length <= i) coeff[i] = this.coeff[i];
            else coeff[i] = this.coeff[i] + that.coeff[i];
        }

        return new Polynomial(coeff);
    }

    public double evaluate(double x) {
        double value = 0;

        for (int i = 0; i <= degree(); i++)
            value += coeff[i] * Math.pow(x, i);

        return value;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
}
