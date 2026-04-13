import java.util.Scanner;

public class Login {

    static Scanner sc = new Scanner(System.in);

    //declare variables as private in correlation with encapsulation
    //prevents inadvertent changes by outside classes
    //create a public method in class to call the username, password and phone number

    private String userName;
    private String password;
    private String cellPhoneNumber;
    private String userFirstName;
    private String userLastName;


    //using a setter method to assign values from the main class
    // encapsulation
    public void setUserDetails(String firstName, String lastName) {
        this.userFirstName = firstName;
        this.userLastName = lastName;
    }


    public String registerUser() {

        //username
        do {
            System.out.println("\nEnter your username: (Username must contain an underscore and be no more than five characters long)");
            userName = sc.nextLine();

            if (!checkUserName(userName)) {
                System.out.println("--Username is not correctly formatted; please ensure your username contains an underscore and is no longer than five characters in length--\n");
            } else {
                System.out.println("--Username successfully captured!--\n");
            }

        } while (!checkUserName(userName));

        // Password
        do {
            System.out.println("Enter your password: (The password must be at least 8 characters long, contain a capital letter, contain a number and a special character)");
            password = sc.nextLine();

            if (!checkPasswordComplexity(password)) {
                System.out.println("--password is not correctly formatted; Please ensure that the password contains at least eight characters , a capital letter, a number, and a special character--\n");
            } else {
                System.out.println("--Password successfully captured--\n");
            }

        } while (!checkPasswordComplexity(password));

        //cell phone number
        do {
            System.out.println("Enter your phone number: (must contain the international country code and be no more than 10 characters long) ");
            cellPhoneNumber = sc.nextLine();

            if (!checkCellPhoneNumber(cellPhoneNumber)) {
                System.out.println("--Cell phone number incorrectly formatted or does not contain international code--\n");
            } else {
                System.out.println("--phone number successfully added!--\n");
            }

        } while (!checkCellPhoneNumber(cellPhoneNumber));

        return "User " + userName +  " has been successfully registered!!\n";
    }


    public String getUserName() { return userName; }//since username is set up as private we set up an accessor method to retrieve value

    public String getPassword() { return password; } //accessor method to retrieve value

    public String getPhoneNumber() { return cellPhoneNumber; }


    public Boolean checkUserName (String userName) {
        return (userName.contains("_") && userName.length() <= 5);
    }

    public Boolean checkPasswordComplexity(String password){

        //using boolean to check if the conditions for the password have been met
        boolean foundUpperCase = false;
        boolean foundDigit = false;
        boolean foundSpecialChar = false;

        //check if the length requirement is met as it's the easiest to check first then continue the loop
        if (password.length() >= 8){

            for(int i = 0; i < password.length(); i++){ //this loop flips through each character in the password to test for each condition
                //for loop continues for as long as password length is to test for each condition
                char charToTest = password.charAt(i);

                if (Character.isUpperCase(charToTest)){
                    foundUpperCase = true;
                }

                if (Character.isDigit(charToTest)){
                    foundDigit = true;
                }
                //checks if there is a character that isn't a letter or a digit (therefore signifying a special character has been used)
                if (!Character.isLetterOrDigit(charToTest)){
                    foundSpecialChar = true;
                }
            }
        }

        return (password.length() >= 8 && foundUpperCase && foundDigit && foundSpecialChar);
    }

    public Boolean checkCellPhoneNumber(String cellPhoneNumber){
        //using regular expressions that accepts numbers that start with 27 or 0 and followed by 9 digits
        return cellPhoneNumber.matches("(\\+27|0)[0-9]{9}");
    }

    public boolean loginUser() {

        //checks if the username entered matches the registered username
        System.out.println("\nEnter your username: ");
        String enteredUserName = sc.nextLine();
        //checks if the password matches
        System.out.println("Enter your password: ");
        String enteredPassword = sc.nextLine();

        return enteredUserName.equals(userName) && enteredPassword.equals(password);
    }


    String returnLoginStatus(boolean loginUser) {

        if (loginUser) {
            return "\nWelcome " + userFirstName + " " + userLastName + ", it is great to see you again.";
        } else {
            return "\nUsername or password incorrect, please try again.";
        }
    }

}

