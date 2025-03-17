package laboratory.fsqsWholeSale.data;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssService {

    public List<String> fetchRssFeed(String rssUrl) {
        List<String> newsItems = new ArrayList<>();
        try {
            // Use RestTemplate to fetch the RSS feed
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(rssUrl, String.class);

            if (response != null && !response.isEmpty()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true); // Security fix
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(response)));
                doc.getDocumentElement().normalize();

                NodeList items = doc.getElementsByTagName("item");
                for (int i = 0; i < Math.min(items.getLength(), 5); i++) {
                    Node item = items.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) item;

                        String title = getElementText(element, "title");
                        String link = getElementText(element, "link");

                        if (title != null && link != null) {
                            newsItems.add("<a href='" + link + "' target='_blank' class='text-blue-500 hover:text-blue-700'>" + title + "</a>");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching or parsing RSS feed: " + e.getMessage());
        }

        return newsItems;
    }

    private String getElementText(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return (nodeList.getLength() > 0) ? nodeList.item(0).getTextContent() : null;
    }
}
