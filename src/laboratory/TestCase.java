package laboratory;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCase {

    private String tcName;
    private String tcEvent;

    private String tcEventVerb;
    private String userID;
    private String propertyName;
    private String value;
    private String dataType;
    private int numOfChar;
    public String testcaseName;


    public TestCase() {

        //System.out.println("In Test case default constructor");
    }

    public TestCase(String testcaseName){
        this.testcaseName = testcaseName;

    }

    public String getName() {
        return tcName;
    }

    public void setName(String name) {
        this.tcName = name;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String property) {
        this.propertyName = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getNumOfChar() {
        return numOfChar;
    }

    public void setNumOfChar(int numOfChar) {
        this.numOfChar = numOfChar;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getTcEvent() {
        return tcEvent;
    }

    public void setTcEvent(String tcEvent) {
        this.tcEvent = tcEvent;
    }

    public String getTcEventVerb() {
        return tcEventVerb;
    }

    public void setTcEventVerb(String tcEventVerb) {
        this.tcEventVerb = tcEventVerb;
    }

    public static String printDtoSummary(ArrayList<String> dto) {

        HashMap dtoProperties = new HashMap();

        String propertyName = null;
        String propertyValue;

        /*System.out.println("Include properties in request:");
        System.out.println("-----------------------------------------------------");*/

        for (int i = 0; i < dto.size()-1; i++) {


                propertyName = dto.get(i);
                propertyValue   = dto.get(i+1);

                dtoProperties.put(propertyName, propertyValue);

        System.out.println(" And <" + propertyName
                        + " : " + propertyValue + ">");
        i++;
            }
        // System.out.println("-----------------------------------------------------");
        System.out.println();
        return propertyName;
    }
    }





