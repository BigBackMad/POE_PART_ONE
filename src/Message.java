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

    public void generateMessageCount() {
        messageCount++; // Increment by 1 for every instance created
    }

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

    public String checkMessageID() {

        // ennsures ID is not more than 10 characters
        if (this.messageID != null && this.messageID.length() <= 10) {
            return "Message ID generated: " + this.messageID;
        } else {
            return "Message ID creation failed.";
        }

    }

    public void setRecipientCell(String recipient) {
        this.recipientCell = recipient; // 'this' refers to the class field
    }

    public void setMessageContent(String messageText) {
        this.message = messageText;
    }


    public String checkRecipientCell() {
        String regex = "(\\+27|0)[0-9]{9}"; // Eg: starts with + and total length <= 10

        if (this.recipientCell != null && this.recipientCell.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                    "an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        // Get the first 2 digits of the Message ID
        String idPrefix = this.messageID.substring(0, 2);

        // Split the message into individual words using a regex for spaces
        // The trim() ensures we don't have leading/trailing spaces causing errors
        String[] words = this.message.trim().split("\\s+");

        // 3. Identify the first and last words from the array
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        firstWord = firstWord.replaceAll("[^a-zA-Z]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        // Combine all elements with colons
        // using .toUpperCase() on the words as required by the PoE
        String messageHash = idPrefix + ":" + messageCount + ":" +
                (firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    public String SentMessage(int userChoice) {
        // use a switch statement to evaluate the user's choice
        switch (userChoice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    public String printMessages() {
        // sequence: ID, Hash, Recipient, Message
        String report = "Message ID: " + this.messageID +
                "\nMessage Hash: " + this.createMessageHash() +
                "\nRecipient: " + this.recipientCell +
                "\nMessage: " + this.message; // Use the variable 'message', not the setter method

        return report;
    }

    public static int returnTotalMessages() {
        //  returns the current value of your static counter
        return messageCount;
    }

    public void storeMessage() {
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

    public String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }



}
