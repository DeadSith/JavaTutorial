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
        return new Object[][]{{-1.0, 2.0}, {-2.0, 1.0}};
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "negativeBeginProvider")
    public void negativeBeginTest(double a, double b) {
        m.beginTask(a, b);
    }

    @Test
    public void intTest() {
        assertEquals(m.intTask(-984), -489);
        assertEquals(m.intTask(329), 923);
        assertEquals(m.intTask(100), 1);
    }

    @DataProvider
    public Object[][] negativeIntProvider() {
        return new Object[][]{{10}, {1000}};
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "negativeIntProvider")
    public void negativeIntTest(int n) {
        m.intTask(n);
    }

    @Test
    public void booleanTest() {
        assertEquals(m.booleanTask(1, 2, 3), true);
        assertEquals(m.booleanTask(-1, 2, 3), false);
        assertEquals(m.booleanTask(1, -2, 3), false);
        assertEquals(m.booleanTask(1, 2, 0), false);
    }

    @Test
    public void ifTest() {
        assertEquals(m.ifTask(2, 2, 2), 2);
        assertEquals(m.ifTask(-6, -4, -12), -12);
        assertEquals(m.ifTask(3, 1, -1), -1);
        assertEquals(m.ifTask(1, 4, 2), 1);
        assertEquals(m.ifTask(2, -17, 9), -17);
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

    @Test
    public void caseTest() {
        assertEquals(m.caseTask(1, 3.0), m.new Circle(3.0, 6.0, 18.84, 28.26));
        assertEquals(m.caseTask(2, 6.0), m.new Circle(3.0, 6.0, 18.84, 28.26));
        assertEquals(m.caseTask(3, 18.84), m.new Circle(3.0, 6.0, 18.84, 28.26));
        assertEquals(m.caseTask(4, 28.26), m.new Circle(3.0, 6.0, 18.84, 28.26));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void negativeForTest() {
        m.forTask(0);
    }

    @Test
    public void forTest() {
        assertEquals(m.forTask(9), 17.643, 0.1);
        assertEquals(m.forTask(10), 33.522, 0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void negativeWhileTest() {
        m.whileTask(1);
    }

    @Test
    public void whileTest() {
        assertEquals(m.whileTask(20), m.new SumTuple(5, 15));
        assertEquals(m.whileTask(40), m.new SumTuple(8, 36));
    }

    @Test
    public void arrayTest() {
        assertEquals(m.arrayTask(new double[]{1.0, 4.0, -7.0, 14, -19}), 1.0);
        assertEquals(m.arrayTask(new double[]{1.0, 4.0, 7.0, 14, 2}), 7.0);
        assertEquals(m.arrayTask(new double[]{-10, 4.0, -7.0, 14, -2}), -2.0);
    }

    @Test
    public void matrixTest() {
        int size = 10;
        int iterations = 1000;
        int matrix[][] = new int[size][size];
        java.util.Random random = new java.util.Random();
        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    matrix[i][j] = random.nextInt();
            int r[][] = m.matrixTask(matrix);
            assertEquals(r[size / 2][0], matrix[0][size / 2]);
            assertEquals(r[0][size - 1], matrix[size / 2][size / 2 - 1]);
            assertEquals(r[2][size - 3], matrix[size / 2 + 2][size / 2 - 3]);
        }

    }
}