import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;

public class Report {

    public static void populateStoredMessages(String[] messageIDs, String[] messageHashes, String[] recipientCells, String[] storedMessages) {

        try {

            File file = new File("stored_messages.json");

            if (!file.exists()) return;

            // Perform a bulk transfer of the JSON data from nonvolatile storage into a String 
            String content = new String(Files.readAllBytes(file.toPath()));
            String trimmed = content.trim();

            // Guard before parsing 
            if (trimmed.isEmpty() || !trimmed.startsWith("[")) return;

            JSONArray arr = new JSONArray(trimmed);

            for (int i = 0; i < arr.length() && i < storedMessages.length; i++) {

                JSONObject obj = arr.getJSONObject(i);

                messageIDs[i] = obj.getString("messageID");
                messageHashes[i] = obj.getString("messageHash");
                recipientCells[i] = obj.getString("recipient");
                storedMessages[i] = obj.getString("payload");
            }

        } catch (Exception e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
    }

    public static void displayContacts(String currentUser, String[] recipients) {
        System.out.println("\n--- MESSAGE CONTACT REPORT ---");

        // To track if we actually found any data to show 
        boolean dataFound = false;

        for (int i = 0; i < recipients.length; i++) {

            //Only display slots that actually contain a phone number 
            if (recipients[i] != null) {
                System.out.println("Sender: " + currentUser + " | Recipient: " + recipients[i]);
                dataFound = true;
            }
        }

        if (!dataFound) {
            System.out.println("No stored message history found.");
        }
    }


    // b. Display the longest stored message 
    public static String displayLongestMessage(String[] storedMessages) {

        String longest = "";

        for (String msg : storedMessages) { // Enhanced for loop: iterates over every message in the array
            if (msg != null && msg.length() > longest.length()) {
                longest = msg;
            }
        }

        System.out.println("Longest Message Found: " + longest);
        return longest;
    }

    public static String searchByID(String searchKey, String[] ids, String[] recipients, String[] payloads) {

        for (int i = 0; i < ids.length; i++) {

            if (ids[i] != null && ids[i].equals(searchKey)) {

                // Build result instead of printing 
                String result = "--- MATCH FOUND ---\n" + "Recipient: " + recipients[i] + "\n" + "Message: " + payloads[i];

                return result; // return when found 
            }
        }

        return "Message ID does not exist.";
    }


    public static String searchByRecipient(String searchKey, String[] recipients, String[] messages) {

        // StringBuilder used to accumulate multiple results without creating new String objects each time
        StringBuilder results = new StringBuilder();

        for (int i = 0; i < recipients.length; i++) {

            // skip empty/unfilled slots in the array
            if (recipients[i] != null) {

                // Case-insensitive comparison so "+27831234567" matches regardless of formatting
                if (recipients[i].equalsIgnoreCase(searchKey)) {

                    // Append the matching message to results with a divider
                    results.append("Message: ").append(messages[i]).append("\n--------------------------------------\n");
                }
            }
        }

        if (results.length() == 0) {
            return "No messages found for recipient: " + searchKey;
        }

        return "--- SEARCH RESULTS FOR: " + searchKey + " ---\n" + results.toString();
    }

    public static String deleteByHash(String targetHash, String[] hashes, String[] messages, String[] ids, String[] recipients) {

        for (int i = 0; i < hashes.length; i++) {

            if (hashes[i] != null && hashes[i].equals(targetHash)) {

                String deletedMessage = messages[i];

                // Clear in-memory arrays 
                hashes[i] = null;
                messages[i] = null;
                ids[i] = null;
                recipients[i] = null;

                // Rewrite the JSON file without the deleted message 
                try {
                    JSONArray updatedArr = new JSONArray();

                    for (int j = 0; j < hashes.length; j++) {
                        if (hashes[j] != null) {
                            JSONObject jo = new JSONObject();
                            jo.put("messageID", ids[j]);
                            jo.put("messageHash", hashes[j]);
                            jo.put("recipient", recipients[j]);
                            jo.put("payload", messages[j]);
                            updatedArr.put(jo);
                        }
                    }

                    FileWriter writer = new FileWriter(new File("stored_messages.json"));
                    writer.write(updatedArr.toString(4));
                    writer.close();

                } catch (Exception e) {
                    System.out.println("Error updating JSON after delete: " + e.getMessage());
                }

                return "Message: \"" + deletedMessage + "\" successfully deleted.";
            }
        }

        return "Error: Message Hash '" + targetHash + "' not found.";
    }

    public static String displayFullReport(String[] ids, String[] hashes, String[] recipients, String[] messages) {

        // StringBuilder builds the report without creating new String objects
        StringBuilder report = new StringBuilder();

        report.append("\n-------------------------------------------\n");
        report.append("      FULL STORED MESSAGES REPORT         \n");
        report.append("---------------------------------------------\n");

        boolean recordsFound = false;

        for (int i = 0; i < ids.length; i++) {

            // Skip empty array slots — only process messages that actually exist
            if (ids[i] != null) {

                report.append("Message ID:    ").append(ids[i]).append("\n");
                report.append("Message Hash:  ").append(hashes[i]).append("\n");
                report.append("Recipient:     ").append(recipients[i]).append("\n");
                report.append("Message:       ").append(messages[i]).append("\n");
                report.append("------------------------------------------\n");

                recordsFound = true;
            }
        }

        // check if no valid slots were found
        if (!recordsFound) {
            return "NO DATA FOUND: The stored message history is empty.";
        }

        report.append("---- END OF REPORT ----\n\n");

        // Convert StringBuilder to a String for returning and printing
        return report.toString();
    }



} 