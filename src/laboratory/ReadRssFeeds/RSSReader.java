package laboratory.ReadRssFeeds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RSSReader {
    static ArrayList<String> dto;

    public static void main(String[] args) throws IOException {

        dto = new ArrayList<String>();

       // dto.add(readRSS("https://www.cbc.ca/cmlink/rss-canada", "<title>", "</title>"));
        //dto.add(readRSS("https://www.cbc.ca/cmlink/rss-canada", "<link>", "</link>"));
        readRSS("https://www.cbc.ca/cmlink/rss-canada", "<title>", "</title>");
        //readRSS("https://www.cbc.ca/cmlink/rss-canada", "<link>", "</link>");
        FeedDtoSummary(dto);

    }

    public static String readRSS(String urlAddress, String tagPref, String tagSuff) throws IOException {
        URL rssUrl = new URL(urlAddress);
        BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
        String sourceCode = "";
        String line;
        while ((line = in.readLine()) != null) {
            if (line.contains(tagPref)) {
                int firstPos = line.indexOf(tagPref);
                String temp = line.substring(firstPos);
                temp = temp.replace(tagPref, "");
                int lastPos = temp.indexOf(tagSuff);
                temp = temp.substring(0, lastPos);
                temp = temp.replace("<![CDATA[", " + ");
                temp = temp.replace("]]>", " ");
                sourceCode += temp + "\n";

                dto.add(temp);
            }
            if (line.contains("<link>")) {
                int firstPos = line.indexOf("<link>");
                String temp = line.substring(firstPos);
                temp = temp.replace("<link>", "");
                int lastPos = temp.indexOf("</link>");
                temp = temp.substring(0, lastPos);
                sourceCode += temp + "\n";

                dto.add(temp);
            }

        }
        in.close();
        return sourceCode;
    }

    public static String FeedDtoSummary(ArrayList<String> dto) {

        ArrayList<String> dtoNameOfProperty = new ArrayList<>();
        ArrayList<String> dtoValueOfProperty = new ArrayList<>();

        Map<String, String> dtoProperties = new HashMap();

        String propertyName = null;
        String propertyValue = null;

        System.out.println("RSS Feed results:");
        System.out.println("-----------------------------------------------------");

        int i;
        for (i = 0; i < dto.size() ;i++) {

            propertyName = dto.get(i);
            propertyValue = dto.get(i+1);

            //System.out.println(i+ propertyName + "\n"  +(i+1)+ propertyValue);
            System.out.println(propertyName + "\n"  +"  --> " + propertyValue);

            //System.out.println( i+ propertyValue + "\n");
            i++;
        }

        System.out.println("-----------------------------------------------------");

        return null;

    }

}
