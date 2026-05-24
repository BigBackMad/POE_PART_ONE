import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MessageTest {

    Message testMessage = new Message();

    @Test
    public void testCheckMessageLengthSuccess() {
        // Arrange: Use a valid message 
        String validContent = "Hi Mike, can you join us for dinner tonight?";
        String expected = "Message ready to send.";

        // Act: Call the method 
        String actual = testMessage.checkMessageLength(validContent);

        // Assert: Verify the actual result matches the expected String 
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckMessageLengthFailure() {
        // Create a string that is over 250 characters
        // using String.repeat() 
        String longContent = "a".repeat(251);

        // Must include the exact number of excess characters (X) 
        String expected = "Message exceeds 250 characters by 1; please reduce the size.";

        // Act 
        String actual = testMessage.checkMessageLength(longContent);

        // Assert 
        assertEquals(expected, actual, "The failure message should correctly calculate the excess characters.");
    }

    @Test
    public void testCheckRecipientCellSuccess() {
        // Arrange: Use Mike's valid number 
        testMessage.setRecipientCell("+27718693002");
        String expected = "Cell phone number successfully captured.";

        // Act 
        String actual = testMessage.checkRecipientCell();

        // Assert 
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckRecipientCellFailure() {
        // Arrange: Use Keegan's invalid number 
        testMessage.setRecipientCell("08575975889");
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        // Act 
        String actual = testMessage.checkRecipientCell();

        // Assert 
        assertEquals(expected, actual);
    }

    @Test
    public void testMessageIDCreated() {
        // arrange
        String fixedID = "1234567890";
        testMessage.setMessageID(fixedID);

        // call
        String actualResult = testMessage.checkMessageID();

        // ASSERT: Verify the result matches
        // expected string is: "Message ID generated: <Message ID>"
        String expectedResult = "Message ID generated: " + fixedID;

        assertEquals(expectedResult, actualResult,
                "The ID status message should match required format.");
    }


    @Test
    public void testCreateMessageHashMike() {

        testMessage.setMessageID("0012345678");
        testMessage.setMessageContent("Hi Mike, can you join us for dinner tonight?");

        Message.messageCount = 0;

        String actualHash = testMessage.createMessageHash();

        // Expected: ID(00) + : + Count(0) + : + FIRSTLAST(HITONIGHT) 
        assertEquals("00:0:HITONIGHT", actualHash, "The message hash must follow the ID:Count:FIRSTLAST format in all caps.");
    }

    @Test
    public void testSentMessageMike() {
        // Mike's data "Select Send" (Option 1) 
        String actual = testMessage.SentMessage(1);
        assertEquals("Message successfully sent.", actual, "'Send' option failed.");
    }

    @Test
    public void testSentMessageKeegan() {
        // Keegan's data "Select Discard" (Option 2) 
        String actual = testMessage.SentMessage(2);
        assertEquals("Press 0 to delete the message.", actual, "'Discard' option failed.");
    }

    @Test
    public void testSentMessageStatus() {
        // 1. Test 'Send' Option 
        String expectedSend = "Message successfully sent.";
        assertEquals(expectedSend, testMessage.SentMessage(1),
                "Status for option 1");

        // 2. Test 'Disregard' Option 
        String expectedDiscard = "Press 0 to delete the message.";
        assertEquals(expectedDiscard, testMessage.SentMessage(2),
                "Status");

        // 3. Test 'Store' Option 
        String expectedStore = "Message successfully stored.";
        assertEquals(expectedStore, testMessage.SentMessage(3),
                "Status");
    }

} 