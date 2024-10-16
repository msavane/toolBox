package laboratory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestCaseContract extends TestCase {

    ArrayList<String>  tcProperties          = new ArrayList<>();

    TestCase tc = new TestCase();

    public TestCaseContract() {

        System.out.println("Please Enter the feature name: ");
        Scanner scanner = new Scanner(System.in);
        String tcName = scanner.nextLine();
        tc.setName(tcName);

        System.out.println("Please enter url of app being tested: [ localhost <url> ]");
        String tcUserIdentifier = scanner.nextLine();
        tc.setUserID(tcUserIdentifier);

        System.out.println("Please describe <goal/desire> when action happens: [ scenario ]");
        String tcEventVerb = scanner.nextLine();
        tc.setTcEventVerb(tcEventVerb);

        System.out.println("Please specify WHEN does this action occur on the page: [ click||mouse over||type||send ]");
        String tcEventListener = scanner.nextLine();
        tc.setTcEvent(tcEventListener);

        System.out.println("In order to " + tc.getTcEventVerb() + " "
                + " on " + tc.getUserID() + ", I want to " + tc.getTcEvent().toUpperCase()
                + " by feeding the system these value(s):");

        System.out.println("------------------------------------------------");

        String addingPropertyYesNo;
        do {
            System.out.println("Please Enter a property name: used to combine more than one event or outcome ");
            String propertyName = scanner.nextLine();
            tc.setPropertyName(propertyName);

            System.out.println("Please Enter a property value:");
            String propertyValue = scanner.nextLine();
            tc.setValue(propertyValue);

           tcProperties = addNewPropertyToList(propertyName,propertyValue);

            System.out.println("Do you want to add another property: y,n ");
            addingPropertyYesNo = scanner.nextLine();
        } while (addingPropertyYesNo.equalsIgnoreCase("y"));//{


        printTestCase();

    }

    /**
     * @param propertyName
     * @return list of custom properties
     */

    public ArrayList<String> addNewPropertyToList(String propertyName, String propertyValue) {


            tcProperties.add(tc.getPropertyName());
            tcProperties.add(tc.getValue());


        return tcProperties;
    }

    public ArrayList<String> addNewPropertyToList(String propertyName) {

        tcProperties.add(tc.getPropertyName());

        return tcProperties;
    }

    public String printTestCase() {
        System.out.println("Feature:"+ tc.getName()+ "\n");
        System.out.println("Scenario: I want to " + tc.getTcEventVerb() + "\n");
        System.out.println("Given I am pointing to " + tc.getUserID() + "\n");
        System.out.println("When I " + tc.getTcEvent() + "\n");
        List<String> dtoProperties = null;
        /*for (int i = 0; i < tcProperties.size(); i++) {
            dtoProperties= Collections.singletonList((tcProperties.get(i)));
            printDtoSummary((ArrayList<String>) dtoProperties);
        }*/
        assert tcProperties != null;
        printDtoSummary( tcProperties);
        System.out.println("\n" + "Then " + tc.getTcEventVerb().toUpperCase() + " is successful!" + "\n");
        return null;
    }

    public TestCaseContract(String name) {
        super(name);
        TestCase tc = new TestCase();
        System.out.println("This test case: " + tc.getName() + " has properties: ");
        System.out.println("\n" + tc.getPropertyName());
    }


}
