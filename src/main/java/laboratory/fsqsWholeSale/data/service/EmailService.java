
package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.model.Order;
import laboratory.fsqsWholeSale.data.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import java.math.BigDecimal;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String toEmail, Order order, Long idForOrder) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mawrysy@yahoo.com");
        message.setTo(toEmail);
        message.setSubject("Order Confirmation - " + idForOrder.toString());

        BigDecimal total = order.getTotalPrice();
        BigDecimal gst = total.multiply(BigDecimal.valueOf(0.05)); // 5% GST
        BigDecimal qst = total.multiply(BigDecimal.valueOf(0.09975)); // 9.975% QST
        BigDecimal taxes = gst.add(qst);
        BigDecimal deliveryFee = BigDecimal.valueOf(7.15);
        BigDecimal afterTax = total.add(taxes).add(deliveryFee);

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Thank you for your order. Here are your details:\n\n")
                .append("Full Name: ").append(order.getFullName()).append("\n")
                .append("Billing Address: ").append(order.getBillingAddress()).append("\n")
                .append("Total Amount: $").append(order.getTotalPrice()).append("\n")
                .append("Status: ").append(order.getStatus()).append("\n\n")
                .append("Basket:\n");

        for (OrderItem item : order.getOrderItems()) {
            emailContent.append("- ")
                    .append(" | ").append(item.getProduct().getName()) // Assuming product has a name
                    .append(" | Quantity: ").append(item.getQuantity())
                    .append(" | Price: $").append(item.getPriceAtPurchase()).append("\n");
                    //.append(item.getProduct().getId()); // Assuming product has an ID;
        }

        emailContent.append("\nReceipt Breakdown:\n")
                .append("Subtotal: $").append(total).append("\n")
                .append("GST (5%): $").append(gst).append("\n")
                .append("QST (9.975%): $").append(qst).append("\n")
                .append("Delivery Fee: $").append(deliveryFee).append("\n")
                .append("Total After Taxes: $").append(afterTax).append("\n");

        message.setText(emailContent.toString());
        mailSender.send(message);
    }

}