package data_factory;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserInfo {
    /* Type of user data expected in this class are:
     * getName: returns random names (full, first, or last)
     * getCustomDate: returns dates on demand (specifyFormat)
     * getDateOfBirth: returns dates based on age and separator (age, separator)
     * getEmailAddress: returns random Email address
     * printDtoSummary: takes a long string that is separated by commas and returns a list
     */
    public static void main(String[] args) {

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

        switch (typeOfName) {
            case "full":
                firstName = dch.getRandomCharacters(numOfChar);
                lastName = dch.getRandomCharacters(numOfChar);
                name = (firstName + " " + lastName);
                break;
            case "first":
                name = (firstName + DataCreatorHelper.getRandomCharacters(numOfChar));
                break;
            case "last":
                name = (lastName + DataCreatorHelper.getRandomCharacters(numOfChar));
                break;
            default:
                System.out.println("please pick a type of name between: full, first, or last");
                break;
        }
        return name;
    }

    private static String getCustomDate(String typeOfDate) {
        /*
         * Other patterns: dd/MM/yy HH:mm:ss
         *                 dd/MM/yyy
         */
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        switch (typeOfDate) {
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

    private static String getEmail() {
        DataCreatorHelper dch = new DataCreatorHelper();
        String userName = DataCreatorHelper.getRandomCharacters(4);
        String domain = "@random" + DataCreatorHelper.getRandomCharacters(2) + ".com";

        return (userName + domain).toLowerCase();
    }

    private static String getDateOfBirth(int age, String typeOfSeparator) {
        DateFormat day = new SimpleDateFormat("dd");
        DateFormat month = new SimpleDateFormat("MM");
        int currentYear = Integer.parseInt(getCustomDate("yyyy"));
        //current year - age
        int dob = currentYear - age;
        Calendar calobj = Calendar.getInstance();
        String dateOfBirthDay = (day.format(calobj.getTime()));
        String dateOfBirthMonth = (month.format(calobj.getTime()));
        return dateOfBirthDay + typeOfSeparator + dateOfBirthMonth + typeOfSeparator + dob;
    }

    public static String  DtoSummary(ArrayList<String> dto) {

        ArrayList<String> dtoNameOfProperty = new ArrayList<>();
        ArrayList<String> dtoValueOfProperty = new ArrayList<>();

        Map<String, String> dtoProperties = new HashMap();

        String propertyName;
        String propertyValue;

        System.out.println("Include properties in request:");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < dto.size()-1; i++) {

            for (int j = 0; j < dto.size()-1; j++) {

                propertyName = dto.get(i);
                propertyValue = dto.get(i + 1);
                dtoNameOfProperty.add(propertyName);
                dtoValueOfProperty.add(i, propertyValue);
                dtoProperties.put(propertyName, propertyValue);

            }
        }
        dtoProperties.forEach((k, v) ->
                System.out.println("AND --> " + k + ", < " + v + " > "));

        /*System.out.println(i + " AND --> [" + propertyName
                        + " : " + propertyValue + "]");*/

        System.out.println("-----------------------------------------------------");
        return null;

    }
}