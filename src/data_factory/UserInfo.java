package data_factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class UserInfo {
    /* Type of user data expected in this class are:
     * getName: returns random names (full, first, or last)
     * getCustomDate: returns dates on demand (specifyFormat)
     * getDateOfBirth: returns dates based on age and separator (age, separator)
     * getEmailAddress: returns random Email address
     * printDtoSummary: takes a long string that is separated by commas and returns a list
     */
    public static void main(String args[]) {

        /*String myName = getName("full", 4);
        System.out.println("New name is: " + myName);*/
        /*String myDate = getCustomDate("dd-mm-yyyy");
        System.out.println("New date is: "+ myDate);*/
        /*String myEmailAddress = getEmail();
        System.out.println("New email is: "+ myEmailAddress);*/
        /*String myDateOfBirth = getDateOfBirth(37,"/");
        System.out.println("New DOB is: "+ myDateOfBirth);*/
        /*String dto = ("Russia:Moscow,Germany:Berlin,England:London,France:Paris,Italy:Rome");
        String printPropertiesValues = printDtoSummary(dto);*/
    }

    private static String getName(String typeOfName, int numOfChar) {
        String firstName = "First-";
        String lastName = "Last-";
        String name = null;
        DataCreatorHelper dch = new DataCreatorHelper();

        if (typeOfName.equals("full")) {
            firstName = dch.getRandomCharacters(numOfChar);
            lastName = dch.getRandomCharacters(numOfChar);
            name = (firstName + " " + lastName);
        } else if (typeOfName.equals("first")) {
            name = (firstName + dch.getRandomCharacters(numOfChar));
        } else if (typeOfName.equals("last")) {
            name = (lastName + dch.getRandomCharacters(numOfChar));
        } else {
            System.out.println("please pick a type of name between: full, first, or last");
        }
        return name;
    }

    private static String getCustomDate(String typeOfDate){
        /*
         * Other patterns: dd/MM/yy HH:mm:ss
         *                 dd/MM/yyy
         */
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        switch(typeOfDate) {
            case "dd":
                // returns today's day
                df = new SimpleDateFormat("dd");
                break;
            case "mm":
                // returns current month
                df = new SimpleDateFormat("mm");
                break;
            case "yy":
                //returns current year [format: yy]
                df = new SimpleDateFormat("yy");
                break;
            case "yyyy":
                //code block
                df = new SimpleDateFormat("yyyy");
                break;
            case "dd/mm/yy":
                df = new SimpleDateFormat("dd/mm/yy");
                break;
            case "dd-mm-yy":
                df = new SimpleDateFormat("dd-mm-yy");
                break;
            case "dd-mm-yyyy":
                df = new SimpleDateFormat("dd-mm-yyyy");
                break;
            default:
                new SimpleDateFormat("dd/MM/yyyy");
                //System.out.println(df.format(calobj.getTime()));
        }

        Calendar calobj = Calendar.getInstance();
        typeOfDate = (df.format(calobj.getTime()));


        return typeOfDate;
    }

    private static String getEmail(){
        DataCreatorHelper dch = new DataCreatorHelper();
        String userName = dch.getRandomCharacters(4);
        String domain = "@random" + dch.getRandomCharacters(2) +".com";
        String emailAddress = (userName+domain).toLowerCase();

        return emailAddress;
    }

    private static String getDateOfBirth(int age, String typeOfSeparator){
        DateFormat day = new SimpleDateFormat("dd");
        DateFormat month = new SimpleDateFormat("MM");
        int currentYear  = Integer.parseInt(getCustomDate("yyyy"));
        //current year - age
        int dob = currentYear - age;
        Calendar calobj = Calendar.getInstance();
        String dateOfBirthDay  = (day.format(calobj.getTime()));
        String dateOfBirthMonth  = (month.format(calobj.getTime()));
        String dateOfBirth  = dateOfBirthDay+typeOfSeparator+dateOfBirthMonth+typeOfSeparator+dob;
        return dateOfBirth;
    }

    private static String printDtoSummary(String dto){
        ArrayList aList= new ArrayList(Arrays.asList(dto.split(",",-1)));
        System.out.println("-----------------------------------------------------");
        System.out.println("The data list contains:");
        for(int i=0;i<aList.size();i++)
        {
            System.out.println(i + " --> "+aList.get(i));

        }
        System.out.println("-----------------------------------------------------");
        return null;
    }
}