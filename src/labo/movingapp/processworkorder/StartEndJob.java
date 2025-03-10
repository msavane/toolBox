package laboratory.movingapp.processworkorder;

import data_factory.UserInfo;
import laboratory.movingapp.MovingAppBaseClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartEndJob extends MovingAppBaseClass {

    static UserInfo dc = new UserInfo();
    static String getTime;

    static {
        try {
            getTime = dc.getCustomDate("HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public static void computeTotal(double manPower, String driverID, double hrlyRate, String time) throws InterruptedException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        Date startTime = dateFormat.parse(time);
        Date endTime = dateFormat.parse(getTime);
        System.out.println(driverID + " and crew started job @ --> " + time);
        long timeSpentOnSite = endTime.getTime() - startTime.getTime();
        int hours   = (int) ((Math.abs(timeSpentOnSite) / (1000*60*60)) % 24);
        hours+=1;
        System.out.println("Worked for --> "+ hours +" HH");
        double TotalWorkOrder = hours*hrlyRate;
        double ToatalJob = ((hours*hrlyRate) / manPower);
        Thread.sleep(100);
        System.out.println("job ended @ --> " + getTime);
        System.out.println("total job PayOut " + TotalWorkOrder);
        System.out.println("total crew pay is " + ToatalJob);
        System.out.println("================================");
        System.out.println();



    }
}
