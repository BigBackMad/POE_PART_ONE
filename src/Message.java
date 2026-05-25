import java.nio.file.Files;
import java.util.Random;
import java.util.*;
import java.util.regex.Matcher;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

public class Message {

    private String messageID;
    private String recipientCell;
    static int messageCount = 0;
    private String message;

    public void generateMessageCount() {
        messageCount++; // Increment by 1 for every instance
    }

    public static String generateMessageID(){

        Random random = new Random(); //pseudorandom number generator to generate unique identifier for every message
        StringBuilder idBuilder = new StringBuilder(); // StringBuilder creates a memory buffer and is mutable - whereas normal String cannot be changed once created


        //Loop 10 times to create a 10 digit random ID
        for(int i = 0; i < 10; i++){

            int digit = random.nextInt(10);
            idBuilder.append(digit); //append() method "glues" digit to the end of current buffer
        }

        return idBuilder.toString(); //toString() to convert StringBuilder buffer into a fixed String object
    }

    public void setMessageID(String ID){

        this.messageID = ID;

    }

    public String checkMessageID() {        //changed from Boolean to String for it to work with unit testing

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
        String regex = "(\\+27|0)[0-9]{9}"; //(same regex used for part 1)

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
        //  trim() ensures theres no trailing spaces that causes errors
        String[] words = this.message.trim().split("\\s+");

        // Identify the first and last words from the array
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        firstWord = firstWord.replaceAll("[^a-zA-Z]", ""); //replaceAll() finds the special characters in messages because it is NOT a letter and removes it.
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        // Combine all elements with colons
        // using .toUpperCase() as required
        String messageHash = idPrefix + ":" + messageCount + ":" +
                (firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    public String SentMessage(int userChoice) {
        //   switch statement to evaluate user's choice
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
        // sequence is - ID, Hash, Recipient, Message
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

    /**
     * ATTRIBUTION:
     * 1.  Baeldung "Introduction to JSON-Java (org.json)"
     * 2.  BeginnersBook "Convert JSON Array to ArrayList in Java"
     * 3.  Farrell's "Java Programming" Chapter 11
     */

    public void storeMessage() {

        try { //method interacts with nonvolatile storage (the hard drive) "risky" compared to internal RAM
            // use try{} so that program doesnt crash if systems fials


            //DATA ENCAPSULATION: Creating a JSONObject to store unordered key-value pairs
            JSONObject jo = new JSONObject();

            //The put() method automatically handles wrapping String values in double quotes
            jo.put("messageID", this.messageID);
            jo.put("messageHash", this.createMessageHash());
            jo.put("recipient", this.recipientCell);
            jo.put("payload", this.message);

            //FReferencing a file on nonvolatile storage
            File file = new File("stored_messages.json");

            JSONArray arr;

            // If file already has messages (defensive programming)
            if (file.exists() && file.length() > 0) {

               // perform a bulk transfer of the JSON data from nonvolatile storage into a String
                // allows it to break text into its component JSON objects so you can add a new message to the list
                String content = new String(Files.readAllBytes(file.toPath()));

                arr = new JSONArray(content);

            } else {

                // First message/clear message then initialise new container
                arr = new JSONArray();
            }

            // Add new message
            arr.put(jo);

            // Rewrite file with updated array
            //  use overwrite mode (false) because we are writing the entire updated array.
            FileWriter writer = new FileWriter(file);

            // Use toString(4) to add a 4-space indent to make JSON human-readable
            writer.write(arr.toString(4));

            writer.close(); //if doesnt close then it can cause data to be lost

            System.out.println("JSON storage successful.");

        } catch (Exception e) {

            System.out.println("Failed to write JSON: " + e.getMessage()); //prevents program crashes
        }
    }

    public String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send."; // i f message is less than or equal to 250 it displays message
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size."; //else the limit has been exceeded and this message displays
        }
    }



}
