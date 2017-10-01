package main;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MainTest {
    Main m;

    @BeforeTest
    void setup() {
        m = new Main();
    }

    @DataProvider
    public Object[][] beginProvider() {
        return new Object[][]{{3.0, 4.0, m.new Rectangle(5.0, 12.0)},
                {1.0, 1.0, m.new Rectangle(Math.sqrt(2), 2 + Math.sqrt(2))}};
    }

    @Test(dataProvider = "beginProvider")
    public void beginTest(double a, double b, Main.Rectangle r) {
        assertEquals(m.beginTask(a, b), r);
    }

    @DataProvider
    public Object[][] negativeBeginProvider() {
        return new Object[][]{{-1.0, 2.0}, {-2.0, 1.0}, {0.0, 1.0}};
    }

    @Test(expectedExceptions = AssertionError.class, dataProvider = "negativeBeginProvider")
    public void negativeBeginTest(double a, double b) {
        m.beginTask(a, b);
    }

    @DataProvider
    public Object[][] intProvider() {
        return new Object[][]{{-984, -489}, {329, 923}, {100, 1}};
    }

    @Test(dataProvider = "intProvider")
    public void intTest(int a, int b) {
        assertEquals(m.intTask(a), b);
    }

    @DataProvider
    public Object[][] negativeIntProvider() {
        return new Object[][]{{10}, {1000}};
    }

    @Test(expectedExceptions = AssertionError.class, dataProvider = "negativeIntProvider")
    public void negativeIntTest(int n) {
        m.intTask(n);
    }

    @DataProvider
    public Object[][] booleanProvider() {
        return new Object[][]{{1, 2, 3, true}, {-1, 2, 3, false}, {1, -2, 3, false}, {1, 2, 0, false}};
    }

    @Test(dataProvider = "booleanProvider")
    public void booleanTest(int a, int b, int c, boolean check) {
        assertEquals(m.booleanTask(a, b, c), check);
    }

    @DataProvider
    public Object[][] ifProvider() {
        return new Object[][]{{2, 2, 2, 2}, {-6, -4, -12, -12}, {3, 1, -1, -1}, {1, 4, 2, 1}, {2, -17, 9, -17}};
    }

    @Test(dataProvider = "ifProvider")
    public void ifTest(int a, int b, int c, int check) {
        assertEquals(m.ifTask(a, b, c), check);
    }

    @DataProvider
    public Object[][] negativeCaseProvider() {
        return new Object[][]{{1, -0.1},
                {7, 1.0}};
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "negativeCaseProvider")
    public void negativeCaseTest(int index, double value) {
        m.caseTask(index, value);
    }

    @DataProvider
    public Object[][] caseProvider() {
        return new Object[][]{{1, 3.0, m.new Circle(3.0, 6.0, 18.84, 28.26)}, {2, 6.0, m.new Circle(3.0, 6.0, 18.84, 28.26)},
                {3, 18.84, m.new Circle(3.0, 6.0, 18.84, 28.26)}, {4, 28.26, m.new Circle(3.0, 6.0, 18.84, 28.26)}};
    }

    @Test(dataProvider = "caseProvider")
    public void caseTest(int index, double value, Main.Circle check) {
        assertEquals(m.caseTask(index, value), check);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void negativeForTest() {
        m.forTask(0);
    }

    @Test
    public void forTest() {
        assertEquals(m.forTask(9), 17.6432256, 0.00001);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void negativeWhileTest() {
        m.whileTask(1);
    }

    @Test
    public void whileTest() {
        assertEquals(m.whileTask(40), m.new SumTuple(8, 36));
    }

    @DataProvider
    public Object[][] arrayProvider() {
        return new Object[][]{{new double[]{1.0, 4.0, -7.0, 14, -19}, 1.0},
                {new double[]{1.0, 4.0, 7.0, 14, 2}, 7.0},
                {new double[]{-10, 4.0, -7.0, 14, -2}, -2.0}};
    }

    @Test(dataProvider = "arrayProvider")
    public void arrayTest(double[] array, double check) {
        assertEquals(m.arrayTask(array), check);
    }

    @Test
    public void matrixTest() {
        assertEquals(m.matrixTask(new int[][]{
                {1, 2, 3, -1, -2, -3},
                {4, 5, 6, -4, -5, 6},
                {7, 8, 9, -7, -8, -9},
                {11, 12, 13, -11, -12, -13},
                {14, 15, 16, -14, -15, -16},
                {17, 18, 19, -17, -18, -19}})[0],
                new int[]{1, 2, 3, 11, 12, 13});
    }
}