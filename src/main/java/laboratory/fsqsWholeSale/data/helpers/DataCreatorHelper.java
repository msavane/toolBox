package laboratory.fsqsWholeSale.data.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataCreatorHelper {

    // function to generate a random string of length n
    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    public static long generateRandomNumber(int maxValue) {
        Random random = new Random();
        int count = random.nextInt(3) + 1; // Generates 1, 2, or 3 numbers
        StringBuilder numberStr = new StringBuilder();

        for (int i = 0; i < count; i++) {
            int num = random.nextInt(maxValue + 1); // Generates a number from 0 to maxValue
            numberStr.append(num); // Append to make a long
        }

        return Long.parseLong(numberStr.toString());
    }

    // function to generate a random string of length n
    static String getRandomCharacters(int n)
    {

        // chose a Character random from this String
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(Alphabet.length()
                    * Math.random());

            // add Character one by one in end of sb

            sb.append(Alphabet.charAt(index));
        }

        return sb.toString();
    }

}
