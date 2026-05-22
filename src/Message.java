import java.util.Random;
import java.util.*;
import java.util.regex.Matcher;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.File;

public class Message {

    private String messageID;
    private String recipientCell;
    static int messageCount = 0;
    private String message;

    public static String generateMessageID(){

        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder();

        //Loop 10 times to create a 10 digit random ID
        for(int i = 0; i < 10; i++){

            int digit = random.nextInt(10);
            idBuilder.append(digit);
        }

        return idBuilder.toString();
    }

    public void setMessageID(String ID){

        this.messageID = ID;

    }

    public Boolean checkMessageID() {

        // Condition: ensures ID is not more than 10 characters
        if (this.messageID != null && this.messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }

    }

    public void setRecipientCell(String recipient) {
        this.recipientCell = recipient; // 'this' refers to the class field [16, 17]
    }

    public void setMessageContent(String messageText) {
        this.message = messageText;
    }


    public String checkRecipientCell() {
        // Reuse your regex string from Part 1 here
        String regex = "^\\+\\d{1,9}$"; // Example: starts with + and total length <= 10

        if (this.recipientCell != null && this.recipientCell.matches(regex)) {
            return "Cell phone number successfully captured."; // PoE Success String [7]
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                    "an international code. Please correct the number and try again."; // PoE Failure String [7]
        }
    }

    public String createMessageHash() {
        // 1. Get the first 2 digits of the Message ID
        String idPrefix = this.messageID.substring(0, 2);

        // 2. Split the message into individual words using a regex for spaces
        // The trim() ensures we don't have leading/trailing spaces causing errors
        String[] words = this.message.trim().split("\\s+");

        // 3. Identify the first and last words from the array
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // 4. Combine all elements with colons
        // We use .toUpperCase() on the words as required by the PoE
        String messageHash = idPrefix + ":" + messageCount + ":" +
                (firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    public String SentMessage(int userChoice) {
        // We use a switch statement to evaluate the user's choice [5, 6]
        switch (userChoice) {
            case 1:
                // POE Success String for "Send"
                return "Message successfully sent.";
            case 2:
                // POE String for "Disregard"
                return "Press 0 to delete the message.";
            case 3:
                // POE String for "Store"
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    public String printMessages() {
        // We build the report using the PoE's required sequence: ID, Hash, Recipient, Message
        String report = "Message ID: " + this.messageID +
                "\nMessage Hash: " + this.createMessageHash() +
                "\nRecipient: " + this.recipientCell +
                "\nMessage: " + this.message; // Use the variable 'message', not the setter method

        return report;
    }

    public int returnTotalMessages() {
        // Simply returns the current value of your static counter
        return messageCount;
    }

    public void storeMessage() {
        // Logic for JSON serialization and file writing goes here
        try {
            // 1. Create the JSON Object and 'put' your fields into it
            JSONObject jo = new JSONObject();
            jo.put("messageID", this.messageID);
            jo.put("messageHash", this.createMessageHash());
            jo.put("recipient", this.recipientCell);
            jo.put("payload", this.message);

            // 2. Write the object to a file
            // The '4' in toString(4) adds indentation for readability
            FileWriter file = new FileWriter("stored_messages.json", true);
            file.write(jo.toString(4) + "\n");
            file.close();

            System.out.println("JSON storage successful.");
        } catch (Exception e) {
            System.out.println("Failed to write JSON: " + e.getMessage());
        }
    }



}
