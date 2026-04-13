import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {


    @Test
    public void testCheckUsernameTrue(){

        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUsernameFalse(){

        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexityTrue(){

        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityFalse(){

        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));

    }

    @Test
    public void testCheckCellPhoneNumberTrue(){

        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberFalse(){
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test



}
