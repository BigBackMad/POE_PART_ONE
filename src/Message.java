import java.util.Random;
import java.util.*;

public class Message {



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

    public void setMessageId(String id, String ){


    }


    public Boolean checkMessageID() {


    }




}
