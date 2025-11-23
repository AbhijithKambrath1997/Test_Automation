package oops;
import org.testng.annotations.Test;

public class MethodOverLoadingPolymorphismTest {

    /**
     * MethodOverLoadingPolymorphism -> According to parameters used different method is used
     */

    @Test
    public void addNumbers() {
        int a = 12;
        int b = 13;
        int r1 = addNumbers(a, b);

        double x = 12.5;
        double y = 52.5;
        double z = 32.5;

        double r2 = addNumbers(x, y);
        double r3 = addNumbers(x, y, z);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);

    }

    public int addNumbers(int a, int b) {
        return a + b;
    }

    public double addNumbers(double a, double b) {
        return a + b;
    }

    public double addNumbers(double a, double b, double c) {
        return a + b + c;
    }
}

