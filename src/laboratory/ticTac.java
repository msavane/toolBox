package laboratory;

import java.text.ParseException;

public class ticTac {

    static String pion = "ox";
     static int index;


    public static void main(String[] args) throws InterruptedException, ParseException {


        System.out.println(pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay()));
        System.out.println(pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay()));
        System.out.println(pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay())+ "   "+pion.charAt(randomLetterToPlay()));

    }

    private  static int randomLetterToPlay(){

        for (int i = 0; i < 1; i++) {


            index = (int) (pion.length()
                    * Math.random());

        }
        return index;
    }
    }
