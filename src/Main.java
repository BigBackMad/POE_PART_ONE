import java.util.Scanner;

public class Main {
     static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //test class to run tests
        LoginTest test = new LoginTest();

        //creates an object of the login class
        Login userLogin = new Login();

        System.out.println("Please enter your first name");
        String firstName = sc.nextLine();
        System.out.println("Please enter your last name");
        String lastName = sc.nextLine();

        userLogin.setUserDetails(firstName, lastName);

        // Registering user
        String registrationDetails = userLogin.registerUser();
        System.out.println(registrationDetails);

        //loops until value changes to true and user logs in successfully
        boolean userloggedIn = false;

        while (!userloggedIn) {
            boolean status = userLogin.loginUser();
            System.out.println(userLogin.returnLoginStatus(status)); //Login part - asks for username and password
            // returns as true or false

            if (status) {                           // exits the loop if the login was successful
                userloggedIn = true;
            }
        }
    }
}