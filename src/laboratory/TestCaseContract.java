package laboratory;

import java.util.ArrayList;
import java.util.Scanner;

public class TestCaseContract extends TestCase {

    ArrayList<String> tcProperties = new ArrayList<String>();
    TestCase tc = new TestCase();

    public TestCaseContract() {

        System.out.println("Please Enter the feature name: ");
        Scanner scanner = new Scanner(System.in);
        String tcName = scanner.nextLine();
        tc.setName(tcName);

        System.out.println("Please Pick a user type: [ admin||user||employee||customer ]");
        String tcUserIdentifier = scanner.nextLine();
        tc.setUserID(tcUserIdentifier);

        System.out.println("Please Enter WHEN does this action (verb) occur: [ retrieve||create||update||delete ]");
        String tcEventListener = scanner.nextLine();
        tc.setTcEvent(tcEventListener);

        System.out.println("Please Enter the event for when the action happens: [ event ]");
        String tcEventVerb = scanner.nextLine();
        tc.setTcEventVerb(tcEventVerb);

        System.out.println("In order to/for "+ tc.getTcEventVerb() +" As a(n) "
                + tc.getUserID() + " I want to "
                + tc.getTcEvent() +" page by providing the system value(s):");

        System.out.println("------------------------------------------------");

                String addingPropertyYesNo = "";
        do {
            System.out.println("Please Enter a property name: used to combine more than one event or outcome ");
            String propertyName = scanner.nextLine();
            tc.setProperty(propertyName);
            addNewPropertyToList(propertyName);


            System.out.println("Do you want to add another property: y,n ");
            addingPropertyYesNo = scanner.nextLine();
        } while (addingPropertyYesNo.equalsIgnoreCase("y"));//{

        for (int i = 0; i < tcProperties.size(); i++) {
            System.out.println("And: " + tcProperties.get(i));
        }


    }

    /**
     * @param propertyName
     * @return list of custom properties
     */

    public ArrayList<String> addNewPropertyToList(String propertyName) {

        tcProperties.add(tc.getProperty());

        return tcProperties;
    }

    public TestCaseContract(String name) {
        super(name);
        TestCase tc = new TestCase();
        System.out.println("This test case: " + tc.getName() + "has properties: ");
        System.out.println("\n" + tc.getProperty());
    }


}
