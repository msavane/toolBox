package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.EmailService;
import laboratory.fsqsWholeSale.data.service.OrderService;
import laboratory.fsqsWholeSale.data.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    // Existing functionality to get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Existing functionality to get an order by ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Existing functionality to place an order (without email and PayPal)
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // Existing functionality to delete an order
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // New functionality to process checkout, send email, and redirect to PayPal
    @PostMapping("/process-checkout")
    public String processCheckout(@RequestParam String fullName,
                                  @RequestParam String clientsMail,
                                  @RequestParam String billingAddress,
                                  @RequestParam String billingAddressApartment,
                                  @RequestParam String billingAddressProvince,
                                  @RequestParam String billingPostal,
                                  @RequestParam String paymentMethod,
                                  @RequestParam List<CartItem> cartItems) {

        // Create an order entity and set properties
        Order order = new Order();
        order.setFullName(fullName);
        order.setEmail(clientsMail);
        order.setBillingAddress(billingAddress);
        order.setBillingAddressApartment(billingAddressApartment);
        order.setBillingAddressProvince(billingAddressProvince);
        order.setBillingPostal(billingPostal);
        order.setStatus("pending");
        order.setTotalPrice(calculateTotalAmount(cartItems)); // Calculate total amount

        // Save order to MySQL
        orderService.saveOrder(order);

        // Send order confirmation email
        emailService.sendOrderConfirmationEmail(clientsMail, order);

        // Here we can proceed to PayPal setup later
        return "Order processed successfully! You will receive an email with the details.";
    }

    // Helper method to calculate total order amount
    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO; // Start with 0
        for (CartItem item : cartItems) {
            BigDecimal price = item.getTotalPrice(); // Assuming getPrice() returns BigDecimal
            BigDecimal quantity = new BigDecimal(item.getQuantity()); // Convert int to BigDecimal
            total = total.add(price.multiply(quantity)); // Multiply and add to total
        }
        return total;
    }

}
