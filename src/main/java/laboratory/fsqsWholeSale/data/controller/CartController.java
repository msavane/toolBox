package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.model.Order;
import laboratory.fsqsWholeSale.data.helpers.DataCreatorHelper;
import laboratory.fsqsWholeSale.data.model.OrderItem;
import laboratory.fsqsWholeSale.data.service.CartService;
import laboratory.fsqsWholeSale.data.service.EmailService;
import laboratory.fsqsWholeSale.data.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/shopping-cart")
    public String showCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalPrice = cartService.calculateTotalPrice();
        BigDecimal shippingCost = cartService.calculateShippingCost();
        BigDecimal totalAmount = totalPrice.add(shippingCost);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingCost", shippingCost);
        model.addAttribute("totalAmount", totalAmount);

        return "cart";
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam String action,
                                 @RequestParam int quantity,
                                 Model model) {
        if (productId == null || quantity <= 0) {
            return "redirect:/cart"; // Prevent errors
        }

        if ("increase".equals(action)) {
            cartService.updateCartItemQuantity(productId, quantity + 1);
        } else if ("decrease".equals(action)) {
            cartService.updateCartItemQuantity(productId, Math.max(1, quantity - 1));
        } else if ("update".equals(action)) {
            cartService.updateCartItemQuantity(productId, Math.min(quantity, 5));
        }

        // Ensure total price is recalculated
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalPrice = cartService.calculateTotalPrice();
        BigDecimal shippingCost = cartService.calculateShippingCost();
        BigDecimal totalAmount = totalPrice.add(shippingCost);

        // Add updated values to model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingCost", shippingCost);
        model.addAttribute("totalAmount", totalAmount);

        return "cart";  // Return the Thymeleaf template with updated data
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalPrice = cartService.calculateTotalPrice();
        BigDecimal shippingCost = cartService.calculateShippingCost();
        BigDecimal totalAmount = totalPrice.add(shippingCost);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingCost", shippingCost);
        model.addAttribute("totalAmount", totalAmount);

        return "checkout"; // Ensure checkout.html exists in the templates folder
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String billingAddress,
                                  @RequestParam String shippingAddress,
                                  @RequestParam String paymentMethod,
                                  Model model) {
        // Logic to process order and payment
        boolean isPaymentSuccessful = cartService.processPayment(paymentMethod, billingAddress, shippingAddress);

        if (isPaymentSuccessful) {
            cartService.clearCart(); // Empty the cart after successful checkout
            return "redirect:/order-confirmation";
        } else {
            model.addAttribute("error", "Payment failed. Please try again.");
            return "checkout";
        }
    }

    // New functionality to process checkout, send email, and redirect to PayPal
    @PostMapping("/process-checkout")
    public String processCheckout(@RequestParam String fullName,
                                  @RequestParam String clientsMail,
                                  @RequestParam String billingAddress,
                                  @RequestParam String billingAddressApartment,
                                  @RequestParam String billingAddressProvince,
                                  @RequestParam String billingPostal,
                                  Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        Order order = new Order();
        order.setFullName(fullName);
        order.setEmail(clientsMail);
        order.setBillingAddress(billingAddress);
        order.setBillingAddressApartment(billingAddressApartment);
        order.setBillingAddressProvince(billingAddressProvince);
        order.setBillingPostal(billingPostal);
        order.setStatus("pending");
        order.setTotalPrice(calculateTotalAmount(cartItems)); // Calculate total amount

// **Step 1: Save Order FIRST so it gets an ID**
        order = orderService.saveOrder(order);  // Ensure order gets an ID

        // Set order items
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getTotalPrice());
            // **Step 2: Assign the saved order to each orderItem**
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }


        order.setOrderItems(orderItems);

        // **Step 3: Save order with order items**
        orderService.saveOrder(order);

        System.out.println("Work on email Service next ....");


        // Send order confirmation email
        // emailService.sendOrderConfirmationEmail(clientsMail, order);
        // Here we can proceed to PayPal setup later
        // return "Order processed successfully! You will receive an email with the details.";

        return ("cart");
    }

    // Helper method to calculate total order amount
    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO; // Start with 0
        BigDecimal afterTax = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
// Assuming item.getTotalPrice() returns BigDecimal and item.getQuantity() returns int
            BigDecimal price = item.getTotalPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity()); // Convert int to BigDecimal

// Calculate total before taxes
            total = price.multiply(quantity); // No need to add to zero-initialized total

// Corrected tax calculations
            BigDecimal gst = total.multiply(BigDecimal.valueOf(0.05)); // 5% GST
            BigDecimal qst = total.multiply(BigDecimal.valueOf(0.09975)); // 9.975% QST

// Define delivery fee
            BigDecimal deliveryFee = BigDecimal.valueOf(7.15);

// Calculate total after taxes, including the delivery fee
            afterTax = total.add(gst).add(qst).add(deliveryFee);

// Debugging print
            System.out.println("Total: " + total + " | GST: " + gst + " | QST: " + qst + " | After Tax: " + afterTax);

        }
        return afterTax;
    }

    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }
}
