import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportTest {

    private String[] ids = new String[8];
    private String[] recipients = new String[8];
    private String[] sentMessages = new String[8];
    private String[] storedMessages = new String[8];
    private String[] hashes = new String[8];

    @BeforeEach //JUnit annotation that runs the annotated method before each individual test, used to reset/reinitialise test data
    public void setup() {

        // Message 1 - Sent 
        ids[0] = "M1";
        recipients[0] = "+27834557896";
        sentMessages[0] = "Did you get the cake?";

        // Message 2 - Stored (DELETE) 
        ids[1] = "M2";
        recipients[1] = "+27838884567";
        storedMessages[1] = "Where are you? You are late! I have asked you to be on time.";
        hashes[1] = "HASH2";

        // Message 3 - Disregard 
        ids[2] = "M3";
        recipients[2] = "+27834484567";
        storedMessages[2] = "Yohoooo, I am at your gate.";

        // Message 4 - Sent (SEARCH BY ID) 
        ids[3] = "0838884567";
        recipients[3] = "0838884567";
        sentMessages[3] = "It is dinner time !";

        // Message 5 - Stored 
        ids[4] = "M5";
        recipients[4] = "+27838884567";
        storedMessages[4] = "Ok, I am leaving without you.";
    }

    @Test
    public void testSentMessagesArray() {
        assertEquals("Did you get the cake?", sentMessages[0]);
        assertEquals("It is dinner time !", sentMessages[3]);
    }

    // 1. Longest message 
    @Test
    public void testLongestMessage() {

        String result = Report.displayLongestMessage(storedMessages);
        assertEquals("Where are you? You are late! I have asked you to be on time.", result);

    }

    // 2. Search by ID 
    @Test
    public void testSearchByID() {

        String result = Report.searchByID("0838884567", ids, recipients, sentMessages);
        assertTrue(result.contains("It is dinner time !"));
    }

    // 3. Search by recipient 
    @Test
    public void testSearchByRecipient() {

        String result = Report.searchByRecipient("+27838884567", recipients, storedMessages);
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    // 4. Delete by hash 
    @Test
    public void testDeleteByHash() {

        String result = Report.deleteByHash("HASH2", hashes, storedMessages, ids, recipients);

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
        assertNull(hashes[1]);
        assertNull(storedMessages[1]);
        assertNull(ids[1]);
        assertNull(recipients[1]);
    }

    @Test
    public void testFullReport() {
        String result = Report.displayFullReport(ids, hashes, recipients, sentMessages);

        assertTrue(result.contains("FULL STORED MESSAGES REPORT"), "Report must contain the correct title.");

        assertTrue(result.contains("M1"), "Report should contain ID for Message 1.");
        assertTrue(result.contains("+27834557896"), "Report should contain Recipient for Message 1.");
        assertTrue(result.contains("Did you get the cake?"), "Report should contain the Message 1 text.");

        assertTrue(result.contains("0838884567"), "Report should contain the ID/Recipient for Message 4.");
        assertTrue(result.contains("It is dinner time !"), "Report should contain the Message 4 text.");
    }

} 