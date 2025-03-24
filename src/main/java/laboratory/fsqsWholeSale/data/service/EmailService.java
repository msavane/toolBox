package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String toEmail, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Order Confirmation - " + order.getId());
        message.setText("Thank you for your order. Here are your details:\n"
                + "Full Name: " + order.getCustomerName() + "\n"
                + "Billing Address: " + order.getAddress() + "\n"
                + "Total Amount: $" + order.getTotalPrice() + "\n"
                + "Status: " + order.getStatus() + "\n\n"
                + "Please complete your payment.");
        mailSender.send(message);
    }
}
