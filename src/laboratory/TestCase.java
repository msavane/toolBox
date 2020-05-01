package laboratory;

public class TestCase {

    private String name;
    private String tcEvent;

    private String tcEventVerb;
    private String userID;
    private String property;
    private String value;
    private String dataType;
    private int numOfChar;


    public TestCase() {

        //System.out.println("In Test case default constructor");
    }

    public TestCase(String name) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
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

}





