package laboratory.movingapp.createjob;

import data_factory.DataCreatorHelper;
import data_factory.UserInfo;
import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import laboratory.TestCase;

import java.text.ParseException;
import java.util.ArrayList;

public class BookMoving {

    public String movingDate;
    public String bookingDate;
    UserInfo nu = new UserInfo();
    DataCreatorHelper dc = new DataCreatorHelper();

    public BookMoving() {
        //System.out.println("In Booking default constructor");
    }

    public String bookMoving(String driverID, String deviceID, double manPower, String movingDate, String destination, String clientsName, String clientsMobile) throws ParseException {
        this.movingDate = getMovingDate();
        this.bookingDate = nu.getCustomDate("dd/MM/yy HH:mm:ss");
        ArrayList<String> arrK = new ArrayList<>();
        /*ArrayList<String> arrV = new ArrayList<>();
        ArrayList<String> dto = new ArrayList<>();*/
        String jobId = dc.getAlphaNumericString(7);

        arrK.add("jobID");
        arrK.add(jobId);
        arrK.add("driverID");
        arrK.add(driverID);
        arrK.add("deviceID");
        arrK.add(deviceID);
        arrK.add("movingDate");
        arrK.add(movingDate);
        arrK.add("destination");
        arrK.add(destination);
        arrK.add("clientsName");
        arrK.add(clientsName);
        arrK.add("clientsMobileNumber");
        arrK.add(clientsMobile);

        TestCase.printDtoSummary(arrK);

        String json = Json.object()
                .add("jobID", jobId)
                .add("driverID", driverID)
                .add("deviceID", deviceID)
                .add("manPower", manPower)
                .add("movingDate", movingDate)
                .add("destination", destination)
                .add("clientsName", clientsName)
                .add("clientsMobileNumber", clientsMobile)
                .toString();

        return json;
    }

    public String getMovingDate() {
        return movingDate;
    }

    public void setMovingDate(String movingDate) {
        this.movingDate = movingDate;
    }
}
