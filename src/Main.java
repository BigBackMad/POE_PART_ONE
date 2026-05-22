import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //test class to run tests
        LoginTest test = new LoginTest();

        //creates an obj of the login class
        Login userLogin = new Login();

        // Step 1: Register user
        String registrationDetails = userLogin.registerUser();
        System.out.println(registrationDetails);

        //loops until value changes to true and user logs in successfully
        boolean userloggedIn = false;

        while (!userloggedIn) {
            boolean status = userLogin.loginUser();
            System.out.println(userLogin.returnLoginStatus(status)); //Step 2: Login - ask for username and password
            // Step 3: returns as true or false

            if (status) {                           // exits the loop if the login was successful
                userloggedIn = true;
            }
        }

        if (userloggedIn) {
            System.out.println("Welcome to QuickChat.");

            boolean running = true;
            while (running) {
                // 1. Display Menu [3]
                System.out.println("\nMain Menu:");
                System.out.println("1 - Send Messages");
                System.out.println("2 - Show recently sent messages");
                System.out.println("3 - Quit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        // 2. Define message quantity
                        System.out.print("How many messages would you like to send? ");
                        int numMessages = sc.nextInt();
                        sc.nextLine();

                        // 3. For loop to capture the set number of messages
                        for (int i = 0; i < numMessages; i++) {

                            Message userMessage = new Message(); // Create object of your new class

                            String ID = userMessage.generateMessageID();
                            userMessage.setMessageID(ID);

                            if (userMessage.checkMessageID()){
                                System.out.println("Message ID Generated: " + ID);
                            }

                            System.out.print("Enter recipient number >> ");
                            String recipient = sc.nextLine();
                            userMessage.setRecipientCell(recipient);

                            System.out.print("Enter message text >> ");
                            String messageText = sc.nextLine();
                            userMessage.setMessageContent(messageText);

                            // 1. Display the Send/Disregard/Store Menu
                            System.out.println("1 - Send Message");
                            System.out.println("2 - Disregard Message");
                            System.out.println("3 - Store Message");
                            System.out.print("Choose an option >> ");

                            int userChoice = sc.nextInt();
                            sc.nextLine(); // Consume newline

                            // 2. Call the method and print the result
                            String status = userMessage.SentMessage(userChoice);
                            System.out.println(status);

                            // Logic: Generate ID, Get Recipient, Get Message, Create Hash
                            // (Call your checkMessageID and checkRecipientCell methods here)
                        }

                        // 4. Display total after the for loop
                        // System.out.println("Total messages sent: " + msg.returnTotalMessagess());
                        break;

                    case 2:
                        System.out.println("Coming Soon.");
                        break;

                    case 3:
                        System.out.println("Goodbye!");
                        running = false; // Exit Part 2 loop
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }
}