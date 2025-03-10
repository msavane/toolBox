package laboratory.movingapp.front_end_browsers;

import laboratory.movingapp.createjob.BookMoving;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String driverID = request.getParameter("driverID");
        String deviceID = request.getParameter("deviceID");
        int manPower = Integer.parseInt(request.getParameter("manPower"));
        String movingDate = request.getParameter("movingDate");
        String destination = request.getParameter("destination");
        String clientsName = request.getParameter("clientsName");
        String clientsMobile = request.getParameter("clientsMobile");

        // Pass parameters to the existing class
      BookMoving existingClassInstance = new BookMoving();

        try {
            existingClassInstance.bookMoving(driverID, deviceID, manPower, movingDate, destination, clientsName, clientsMobile);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Respond to the client
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Booking Details Submitted Successfully</h2>");
        response.getWriter().println("<p>Driver ID: " + driverID + "</p>");
        response.getWriter().println("<p>Device ID: " + deviceID + "</p>");
        response.getWriter().println("<p>Man Power: " + manPower + "</p>");
        response.getWriter().println("<p>Moving Date: " + movingDate + "</p>");
        response.getWriter().println("<p>Destination: " + destination + "</p>");
        response.getWriter().println("<p>Client's Name: " + clientsName + "</p>");
        response.getWriter().println("<p>Client's Mobile: " + clientsMobile + "</p>");
        response.getWriter().println("</body></html>");
    }
}

