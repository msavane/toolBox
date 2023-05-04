package laboratory.ReadRssFeeds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RSSReader {

    public static void main(String[] args) throws IOException {
        System.out.println(readRSS("https://www.cbc.ca/cmlink/rss-canada","<title>", "</title>" ));
        //System.out.println(" + "+ readRSS("https://www.cbc.ca/cmlink/rss-canada","<link>", "</link>" ));
    }
    public static String readRSS(String urlAddress, String tagPref, String tagSuff) throws IOException {
        URL rssUrl = new URL(urlAddress);
        BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
        String sourceCode = "";
        String line;
        while ((line=in.readLine())!=null){
            if (line.contains(tagPref)){
                int firstPos = line.indexOf(tagPref);
                String temp = line.substring(firstPos);
                temp = temp.replace(tagPref,"");
                int lastPos = temp.indexOf(tagSuff);
                temp = temp.substring(0,lastPos);
                sourceCode += temp+"\n";
            }
        }
        in.close();
         sourceCode = sourceCode.replace("<![CDATA["," + ");
         sourceCode = sourceCode.replace("]]>"," ");
        return sourceCode;
    }
}
