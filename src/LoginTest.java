import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {


    @Test
    public void testCheckUsername(){

        Login login = new Login();
        String username = login.getUserName();

        username ="kyl_1";
        assertTrue(login.checkUserName("kyl_1"));

        username ="kyle!!!!!!!";
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity(){

        Login login = new Login();
        String password = login.getPassword();

        password = "Ch&&sec@ke99!";
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));

        password = "password";
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber(){

        Login login = new Login();
        String phoneNumber = login.getPhoneNumber();

        phoneNumber = "+27838968976";
        assertTrue(login.checkCellPhoneNumber("+27838968976"));

        phoneNumber = "08966553";
        assertFalse(login.checkCellPhoneNumber("08966553"));


    }


}
