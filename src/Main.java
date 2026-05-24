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
            System.out.println();
            System.out.println("Welcome to QuickChat.");

            boolean running = true;
            while (running) {
                // 1. Display Menu
                System.out.println("\nMain Menu:");
                System.out.println("1 - Send Messages");
                System.out.println("2 - Show recently sent messages");
                System.out.println("3 - Quit");
                System.out.print("\nEnter choice: ");
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

                            while(!userMessage.checkRecipientCell().equals("Cell phone number successfully captured.")){

                                System.out.println(userMessage.checkRecipientCell());
                                System.out.println("PLease re-enter the number >> ");
                                userMessage.setRecipientCell(sc.nextLine());

                            }

                            System.out.println(userMessage.checkRecipientCell());

                            System.out.print("Please enter your message (max 250 characters) >> ");
                            String messageText = sc.nextLine(); // Initialize the loop control variable

                            //The Validation Loop: Tests the condition before allowing progress
                            while (messageText.length() > 250) {
                                int excess = messageText.length() - 250; // Calculate X for the error message

                                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");

                                System.out.print("Please re-enter your message >> ");
                                messageText = sc.nextLine(); // Alter the loop control variable to avoid infinite loop
                            }

                            // passing the string to your object
                            userMessage.setMessageContent(messageText);
                            System.out.println("Message ready to send."); //

                            // Displaying the Send/Disregard/Store Menu
                            System.out.println("1 - Send Message");
                            System.out.println("2 - Disregard Message");
                            System.out.println("3 - Store Message");
                            System.out.print("Choose an option >> ");

                            int userChoice = sc.nextInt();
                            sc.nextLine();

                            // Calls  method and print  result
                            String status = userMessage.SentMessage(userChoice);
                            System.out.println();
                            System.out.println(status);

                            if (userChoice == 1) {

                                userMessage.generateMessageCount();
                                System.out.println("\n--- MESSAGE DETAILS ---");

                                // We call the method and print its return value
                                System.out.println(userMessage.printMessages());

                                System.out.println("----------------------------\n");
                            }

                            if (userChoice == 3) {
                                userMessage.storeMessage();
                            }


                        }

                        // Display total after the for loop
                        System.out.println("\n=================================");
                        System.out.println("TOTAL MESSAGES SENT: " + Message.returnTotalMessages());
                        System.out.println("=================================");

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