package oops;

import org.testng.annotations.Test;

public class EncapsulationTest {

    /**
     * Hiding the fields and use getter and setters to access the fields
     */

    public static class User {
        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    @Test
    public void en() {
        User user = new User();
        user.setName("ABC");
        user.setPassword("PQR");

        System.out.println(user.getName());
        System.out.println(user.getPassword());
    }

}



