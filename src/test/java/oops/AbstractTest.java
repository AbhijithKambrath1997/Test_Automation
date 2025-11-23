package oops;
import org.testng.annotations.Test;

public class AbstractTest {

    /**
     * Hiding implementation details of subclass by abstract-class
     * Abstract class can have abstract and concrete method
     * Concrete-method -> Method in Abstract class
     * Abstract-method -> SubClass method called in Abstract Class and can be called when Abstract class is called
     *
     */

    public abstract static class AbstractClass {

        public void deposit() {
            System.out.println("This is deposit");
        }

        public abstract void balance();
    }

    public static class subClass extends AbstractClass {

        public void balance() {
            System.out.println("This is balance");
        }
    }


    @Test
    public void abstractTest() {
        AbstractClass abstractClass = new subClass();
        abstractClass.deposit();
        abstractClass.balance();
    }
}
