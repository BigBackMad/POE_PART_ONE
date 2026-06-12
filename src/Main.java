import java.util.Scanner;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;


public class Main {

    //Constants
    private static final int MAX_MESSAGES = 100;

    // DECLARATIONS: Parallel arrays
    static String[] sentMessages = new String[MAX_MESSAGES];
    static String[] disregardedMessages = new String[MAX_MESSAGES];
    static String[] storedMessages = new String[MAX_MESSAGES];
    static String[] messageIDs = new String[MAX_MESSAGES];
    static String[] messageHashes = new String[MAX_MESSAGES];
    static String[] recipientCells = new String[MAX_MESSAGES];

    //track the next available index
    static int sentCount = 0;
    static int disregardCount = 0;
    static int storedCount = 0;

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
                System.out.println("4 - Stored Messages Report");
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

                            System.out.println(userMessage.checkMessageID());

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
                            while (!userMessage.checkMessageLength(messageText).equals("Message ready to send.")) {

                                System.out.println(userMessage.checkMessageLength(messageText));
                                sc.nextLine();
                                System.out.print("Please re-enter your message >> ");
                                messageText = sc.nextLine(); // Alter the loop control variable to avoid infinite loop

                            }

                            // passing the string to your object
                            userMessage.setMessageContent(messageText);
                            System.out.println(userMessage.checkMessageLength(messageText)); // Displays that the message is ready to send
                            System.out.println();

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

                                sentMessages[sentCount] = messageText;
                                messageIDs[sentCount] = ID;
                                messageHashes[sentCount] = userMessage.createMessageHash();
                                recipientCells[sentCount] = recipient;

                                sentCount++;

                                userMessage.generateMessageCount();
                                System.out.println("\n--- MESSAGE DETAILS ---");

                                // We call the method and print its return value
                                System.out.println(userMessage.printMessages());

                                System.out.println("----------------------------\n");
                            }

                            if (userChoice == 2) {

                                //Store the text currently held in the 'messageText' variable
                                disregardedMessages[disregardCount] = messageText;

                                //INCREMENTING: Move the 'disregardCount' forward
                                disregardCount++;

                                // indefinite loop for validation (Defensive Programming)
                                System.out.print("Action required >> ");
                                int deleteConfirm = sc.nextInt();

                                while (deleteConfirm != 0) {
                                    System.out.println("Invalid input. You must press 0 to confirm deletion.");
                                    System.out.print("Action required >> ");
                                    deleteConfirm = sc.nextInt();
                                }

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

                    case 4:
                        Report.populateStoredMessages(messageIDs, messageHashes, recipientCells, storedMessages);

                        System.out.println("\n--- STORED MESSAGES REPORT ---");
                        System.out.println("a. Display All Senders and Recipients");
                        System.out.println("b. Display Longest Stored Message");
                        System.out.println("c. Search by Message ID");
                        System.out.println("d. Search by Recipient");
                        System.out.println("e. Delete Message by Hash");
                        System.out.println("f. Display Full Report");
                        System.out.print("Select a report task (a-f) >> ");

                        String subChoice = sc.nextLine().toLowerCase();

                        switch (subChoice) {
                            case "a":
                                Report.displayContacts(userLogin.getUserFirstName() + " " + userLogin.getUserLastName(), recipientCells);
                                break;
                            case "b":
                                Report.displayLongestMessage(storedMessages);
                                break;
                            case "c":
                                System.out.print("Enter ID: ");
                                System.out.println(Report.searchByID(sc.nextLine(), messageIDs, recipientCells, storedMessages));
                                break;
                            case "d":
                                System.out.print("Enter Recipient: ");
                                System.out.println(Report.searchByRecipient(sc.nextLine(), recipientCells, storedMessages));
                                break;
                            case "e":
                                System.out.print("Enter Hash: ");
                                System.out.println(Report.deleteByHash(sc.nextLine(), messageHashes, storedMessages, messageIDs, recipientCells));
                                break;
                            case "f":
                                System.out.println(Report.displayFullReport(messageIDs, messageHashes, recipientCells, storedMessages));
                                break;
                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                        break;


                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }


}

