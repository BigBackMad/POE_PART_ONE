import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {


    @Test
    public void testCheckUsername(){

        Login login = new Login();

        assertTrue(login.checkUserName("kyl_1"));
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity(){

        Login login = new Login();

        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber(){

        Login login = new Login();

        assertTrue(login.checkCellPhoneNumber("+27838968976"));
        assertFalse(login.checkCellPhoneNumber("08966553"));


    }


}
