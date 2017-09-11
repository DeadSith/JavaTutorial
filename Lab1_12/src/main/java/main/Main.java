package main;

public class Main {
    protected class Rectangle {
        double hypotenuse, perimeter;

        Rectangle() {
            hypotenuse = 0;
            perimeter = 0;
        }

        Rectangle(double hypotenuse, double perimeter) {
            this.hypotenuse = hypotenuse;
            this.perimeter = perimeter;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            Rectangle r = (Rectangle) obj;
            return (Math.abs(this.hypotenuse - r.hypotenuse) < 0.1 && Math.abs(this.perimeter - r.perimeter) < 0.1);
        }
    }

    protected class Circle {
        double R, D, L, S;

        Circle() {
            R = 0;
            D = 0;
            L = 0;
            S = 0;
        }

        Circle(double r, double d, double l, double s) {
            R = r;
            D = d;
            L = l;
            S = s;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            Circle c = (Circle) obj;
            return (Math.abs(this.R - c.R) < 0.1 && Math.abs(this.D - c.D) < 0.1 &&
                    Math.abs(this.L - c.L) < 0.1 && Math.abs(this.S - c.S) < 0.1);
        }
    }

    protected class SumTuple {
        int k, sum;

        SumTuple() {
            k = 0;
            sum = 0;
        }

        SumTuple(int k, int sum) {
            this.k = k;
            this.sum = sum;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            SumTuple s = (SumTuple) obj;
            return (this.k == s.k && this.sum == s.sum);
        }
    }

    public static void main(String[] args) {
        // write your code here
    }

    /**
     * @param a first side of triangle
     * @param b second side of triangle
     * @return hypotenuse and perimeter of triangle
     */
    Rectangle beginTask(double a, double b) {
        if (a <= 0.0 || b <= 0.0)
            throw new IllegalArgumentException();
        Rectangle r = new Rectangle();
        r.hypotenuse = Math.sqrt(a * a + b * b);
        r.perimeter = a + b + r.hypotenuse;
        return r;
    }

    /**
     * @param number three-digit number to be reversed
     * @return reversed number
     */
    int intTask(int number) {
        if (Math.abs(number) < 100 || Math.abs(number) > 999)
            throw new IllegalArgumentException();
        int result = number % 10 * 100;
        number /= 10;
        result += number % 10 * 10;
        number /= 10;
        result += number;
        return result;
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return a, b, c > 0
     */
    boolean booleanTask(int a, int b, int c) {
        return (a > 0 && b > 0 && c > 0);
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return smallest of a,b,c
     */
    int ifTask(int a, int b, int c) {
        if (a < b) {
            if (a < c)
                return a;
            else return c;
        }
        if (b < c)
            return b;
        return c;
    }

    /**
     * @param index index of given value(1 for radius, 2 for diameter, 3 for length, 4 for perimeter)
     * @param value
     * @return Circle object with calculated radius, diameter, length and perimeter
     */
    Circle caseTask(int index, double value) {
        if (value < 0)
            throw new IllegalArgumentException();
        Circle c = new Circle();
        switch (index) {
            case 1:
                c.R = value;
                break;
            case 2:
                c.R = value / 2.0;
                break;
            case 3:
                c.R = value / 6.28;
                break;
            case 4:
                c.R = Math.sqrt(value / 3.14);
                break;
            default:
                throw new IllegalArgumentException();
        }
        c.D = 2 * c.R;
        c.L = 2 * 3.14 * c.R;
        c.S = 3.14 * c.R * c.R;
        return c;
    }

    /**
     * @param n upper bound
     * @return product from 1.1 up to {@code n}
     */
    double forTask(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        double r = 1.0;
        double m = 1.1;
        for (int i = 1; i < n; i++) {
            r *= m;
            m += 0.1;
        }
        return r;
    }

    /**
     * @param n number to calculate max sum 1+2+..+k which is smaller than it
     * @return max sum and values of biggest element of sum
     */
    SumTuple whileTask(int n) {
        if (n < 2)
            throw new IllegalArgumentException();
        int s = 0, k = 1;
        while (s + k <= n) {
            s += k;
            k++;
        }
        SumTuple t = new SumTuple();
        t.k = k - 1;
        t.sum = s;
        return t;
    }

    /**
     * @param array
     * @return biggest element of array on odd position
     */
    double arrayTask(double[] array) {
        double biggest = array[0];
        for (int i = 2; i < array.length; i += 2) {
            if (array[i] > biggest)
                biggest = array[i];
        }
        return biggest;
    }

    /**
     * @param array array to swap lower left quarter with upper right
     * @return swapped array
     */
    int[][] matrixTask(int[][] array) {
        int[][] clone = new int[array.length][array[0].length];
        int halfRowCount = array.length / 2;
        int halfColumnCount = array[0].length / 2;
        for (int i = 0; i < halfRowCount; i++) {
            for (int j = 0; j < halfColumnCount; j++) {
                clone[i][j] = array[i][j];
                clone[halfRowCount + i][j] = array[i][halfColumnCount + j];
                clone[halfRowCount + i][halfColumnCount + j] = array[halfRowCount + i][halfColumnCount + j];
                clone[i][halfColumnCount + j] = array[halfRowCount + i][j];
            }
        }
        return clone;
    }
}
