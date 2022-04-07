package laboratory.com.laboratory.writeToFile;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriterHelper {


        public static String FileWriterHelperToTxt(String ln) throws IOException {

            File file = new File("out.txt");
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(ln);
            pw.close();

            return null;
        }

        public static String FileWriterHelperToXml(String scenarioID, String childItem, String valueOfChild, ArrayList<String> propertyList) throws ParserConfigurationException, TransformerException {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("feature");
            document.appendChild(root);

            // test case element
            Element scenario = document.createElement("scenario");

            root.appendChild(scenario);

            // set an attribute to TC element
            Attr attr = document.createAttribute("id");
            attr.setValue(scenarioID);
            scenario.setAttributeNode(attr);

            //Loop

            for (int i = 0; i < propertyList.size()-1; i++) {

             childItem = propertyList.get(i);
             valueOfChild = propertyList.get(i+1);

                //System.out.println(childItem.toLowerCase());
                // child element
                childItem = childItem.replaceAll("\\s+", "");
                Element childElement = document.createElement(childItem.toLowerCase());
                childElement.appendChild(document.createTextNode(valueOfChild));
                scenario.appendChild(childElement);
i++;
            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // make xml pretty, indent
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(document);
            String xmlFilePath = ("xmlfile.xml");
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);


            return xmlFilePath;

        }
}
