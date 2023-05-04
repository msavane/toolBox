package laboratory.ReadRssFeeds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static data_factory.UserInfo.DtoSummary;

public class RSSReader {
    static ArrayList<String> dto;

    public static void main(String[] args) throws IOException {

        dto = new ArrayList<String>();

        //dto.add(readRSS("https://www.cbc.ca/cmlink/rss-canada", "<title>", "</title>"));
        //dto.add(readRSS("https://www.cbc.ca/cmlink/rss-canada", "<link>", "</link>"));
        readRSS("https://www.cbc.ca/cmlink/rss-canada", "<title>", "</title>");
        //readRSS("https://www.cbc.ca/cmlink/rss-canada", "<link>", "</link>");
        DtoSummary(dto);

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

        }
        in.close();
        return sourceCode;
    }
}
