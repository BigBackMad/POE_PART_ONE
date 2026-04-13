import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {


    @Test
    public void testCheckUsernameTrue(){

        Login login = new Login();
        String username = login.getUserName();
        username = "kyl_1";
        String expected = "--Username successfully captured!--\n";
        String actual = login.registerUser();
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckUsernameFalse(){

        Login login = new Login();
        String expected = "kyle!!!!!!!";
        String actual = login.getUserName();
        assertEquals(expected, "Username is not correctly formatted", actual);
    }

    @Test
    public void testCheckPasswordComplexityTrue(){

        Login login = new Login();
        String expected = "Ch&&sec@ke99!";
        String actual = login.getPassword();
        assertEquals(expected, "Password is correctly formatted", actual);
    }

    @Test
    public void testCheckPasswordComplexityFalse(){

        Login login = new Login();
        String expected = "password";
        String actual = login.getPassword();
        assertEquals(expected, "Password is not correctly formatted", actual);
    }

    @Test
    public void testCheckCellPhoneNumberTrue(){

        Login login = new Login();
        String expected = "+27838968976";
        String actual = login.getPhoneNumber();
        assertEquals(expected, "Phone number is correct", actual);
    }

    @Test
    public void testCheckCellPhoneNumberFalse(){
        Login login = new Login();
        String expected = "08966553";
        String actual = login.getPhoneNumber();
        assertEquals(expected, "Phone number is not correct", actual);
    }



}
