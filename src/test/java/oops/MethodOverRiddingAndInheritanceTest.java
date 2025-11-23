package oops;
import org.testng.annotations.Test;

public class MethodOverRiddingAndInheritanceTest {


    /**
     * Inheritance -> Child get non-private properties parent
     * MethodOverRidingPolymorphism -> Child override parents same method
     */

    static class Car {

        public void start() {
            System.out.println("Car Start");
        }

        public void stop() {
            System.out.println("Car stop");
        }

        public static void refuel() {
            System.out.println("Car stop");
        }
    }


    //If we give override, will call child method
    static class Audi extends Car implements Vehicle1 {
        @Override
        public void start() {
            System.out.println("Audi Start");
        }


        @Override
        public void go() {
            System.out.println("Audi go");
        }

        @Override
        public void on() {
            System.out.println("Audi Vehicle Start");
        }
    }

    //If we give without override, will call child method since it have its own impl
    static class BMW extends Car implements Vehicle {
        public void start() {
            System.out.println("BMW Start");
        }

        @Override
        public void on() {
            System.out.println("BMW on");
        }

    }

    //If we give override with super, will call parent method
    static class Benz extends Car {
        @Override
        public void start() {
            super.start();
        }
    }

    //If there is no impl in child, will call parent method
    static class Toyota extends Car {
        //No method
    }


    @Test
    public void run() {
        Audi audi = new Audi();
        audi.start();
        audi.stop();
        Audi.refuel();
        audi.on();
        audi.go();

        BMW bmw = new BMW();
        bmw.start();
        bmw.stop();
        bmw.on();

        Car car1 = new BMW();
        car1.start();
        car1.stop();

        Car car2 = new Audi();
        car2.start();
        car2.stop();

        Car car3 = new Toyota();
        car3.start();
        car3.stop();

        Car car4 = new Benz();
        car4.start();
        car4.stop();
        Car.refuel();
    }

    //Interface to Class
    interface Vehicle {
        void on();
    }

    //Interface to Interface
    interface Vehicle1 extends Vehicle {
        void go();
    }


}

