package laboratory.movingapp;


import laboratory.movingapp.createjob.BookMoving;
import laboratory.movingapp.processworkorder.StartEndJob;

import java.text.ParseException;
import java.util.Scanner;

public class MovingAppBaseClass {
    static String deviceID;
    static String driverID;
    static double manPower;

    static String date;
    static String time;
    static String destination;

    static String clientsName;
    static String clientsMobile;
    protected static BookMoving booking = new BookMoving();

    public static void main(String[] args) throws InterruptedException, ParseException {

        String workOrder = createBooking();
        System.out.println(workOrder);

    }

    protected static String  createBooking() throws InterruptedException, ParseException {
        System.out.println("What is your full name:");
        Scanner scanner = new Scanner(System.in);
        clientsName = scanner.nextLine();
        System.out.println("What is your phone number:");
        clientsMobile = scanner.nextLine();
        System.out.println("Which day is your reservation for: dd/MM/yy");
        date = scanner.nextLine();
        System.out.println("What time is your reservation for: HH:mm:ss");
        time = scanner.nextLine();
        booking.setMovingDate(date +" "+ time);
        System.out.println("What is your destination: Full Address");
        destination = scanner.nextLine();

        System.out.println("===========To be filled by Back Office===========");
        System.out.println("Please enter the device job is to be sent to");
        deviceID = scanner.nextLine();
        System.out.println("Please pick driver for Job");
        driverID = scanner.nextLine();
        System.out.println("Please enter the number of people to work on the job");
        manPower = Double.parseDouble(scanner.nextLine());
        double hrlyRate = 170;
        System.out.println("================================");
        StartEndJob.computeToatal(manPower, driverID, hrlyRate, time);
        System.out.println();
       return booking.bookMoving(driverID, deviceID, manPower, booking.movingDate, destination,clientsName,clientsMobile);

    }


}
