package laboratory.com.laboratory.writeToFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterHelper {


        public static String FileWriterHelper(String ln) throws IOException {

            File file = new File("out.txt");
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(ln);
            pw.close();

            return null;
        }
}
