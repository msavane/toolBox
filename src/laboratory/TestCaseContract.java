package laboratory;

import laboratory.com.laboratory.writeToFile.FileWriterHelper;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestCaseContract extends TestCase {

    ArrayList<String> tcProperties = new ArrayList<>();

    TestCase tc = new TestCase();
    Scanner scanner = new Scanner(System.in);

    public TestCaseContract() throws IOException, ParserConfigurationException, TransformerException {

        System.out.println("Please Enter the feature name: ");

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

            tcProperties = addNewPropertyToList(propertyName, propertyValue);

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


        tcProperties.add(propertyName);
        tcProperties.add(propertyValue);


        return tcProperties;
    }


    public String printTestCase() throws IOException, ParserConfigurationException, TransformerException {

        System.out.println("Feature:" + tc.getName() + "\n");
        FileWriterHelper.FileWriterHelperToTxt("Feature:" + tc.getName() + "\n");

        System.out.println("Scenario: I want to " + tc.getTcEventVerb() + "\n");
        FileWriterHelper.FileWriterHelperToTxt("Scenario: I want to " + tc.getTcEventVerb() + "\n");

        System.out.println("Given I am pointing to " + tc.getUserID() + "\n");
        FileWriterHelper.FileWriterHelperToTxt("Given I am pointing to " + tc.getUserID() + "\n");

        System.out.println("When I " + tc.getTcEvent() + "\n");
        FileWriterHelper.FileWriterHelperToTxt("When I " + tc.getTcEvent() + "\n");

        if (tcProperties.isEmpty()) {
            System.out.println("The payload is empty, please insert property");
            TestCaseContract tcc = new TestCaseContract();

        } else {

            printDtoSummary(tcProperties);
            System.out.println("\n" + "Then " + tc.getTcEventVerb().toUpperCase() + " is successful!" + "\n");
            FileWriterHelper.FileWriterHelperToTxt("\n" + "Then " + tc.getTcEventVerb().toUpperCase() + " is successful!" + "\n");

//            FileWriterHelper.FileWriterHelper("-----------------------------------------------------");
            FileWriterHelper.FileWriterHelperToTxt(" PROPERTIES " + " || " + " MAX CHAR ");

            TestCasePropertiesAttributes();


        }
        return null;
    }

    public void TestCasePropertiesAttributes() throws IOException, ParserConfigurationException, TransformerException {

        System.out.println("The " + tc.getName() + " test case has properties: ");
        //System.out.println("\n" + tc.getPropertyName()+ "\n");
        String nomDePropriete = null;
        String valeurDePropriete = null;
        for (int i = 0; i < tcProperties.size() - 1; i++) {
            nomDePropriete = tcProperties.get(i);
            valeurDePropriete = tcProperties.get(i+1);
            System.out.println("Please Enter max char allowed for: " + tcProperties.get(i));
            String tcSetNumOfChar = scanner.nextLine();
            tc.setNumOfChar(Integer.parseInt(tcSetNumOfChar));
            FileWriterHelper.FileWriterHelperToTxt(" --> " + nomDePropriete + " || [ " + tc.getNumOfChar() + " ]");
            i++;
        }
        FileWriterHelper.FileWriterHelperToTxt("-----------------------------------------------------");
        FileWriterHelper.FileWriterHelperToXml(tc.getTcEventVerb(),nomDePropriete,valeurDePropriete, tcProperties);
    }


}
