package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.model.Order;
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
    private final OrderService orderService;
    private final EmailService emailService;

    @Autowired
    public CartController(CartService cartService, OrderService orderService, EmailService emailService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @GetMapping("/order-confirmation")
    public String showOrderConfirmation(Model model) {
        // You can retrieve the latest order details if needed
        // Example: model.addAttribute("order", order);

        // For now, just display the confirmation page
        return "order-confirmation"; // Make sure this matches your actual view template
    }

    // Display the shopping cart
    @GetMapping("/cart")
    public String showCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalPrice = cartService.calculateTotalPrice();
        BigDecimal shippingCost = cartService.calculateShippingCost();
        BigDecimal totalAmount = totalPrice.add(shippingCost);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingCost", shippingCost);
        model.addAttribute("totalAmount", totalAmount);

        return "cart"; // Render cart view
    }

    // Update cart item quantity
    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam String action,
                                 @RequestParam(required = false) Integer quantity,
                                 Model model) {
        if (productId == null || action == null || action.isEmpty()) {
            return "redirect:/cart"; // Redirect back to cart if data is invalid
        }

        CartItem cartItem = cartService.getCartItemByProductId(productId);
        if (cartItem == null) {
            return "redirect:/cart"; // Redirect back to cart if item not found
        }

        int newQuantity = cartItem.getQuantity();

        // Handle actions to increase, decrease, or update quantity
        if ("increase".equals(action)) {
            newQuantity++;
        } else if ("decrease".equals(action)) {
            newQuantity = Math.max(1, newQuantity - 1); // Ensure quantity doesn't go below 1
        } else if ("update".equals(action) && quantity != null) {
            newQuantity = Math.min(quantity, 5); // Max quantity restricted to 5
        }

        cartService.updateCartItemQuantity(productId, newQuantity); // Update the cart item quantity

        return "redirect:/cart"; // Redirect back to cart page after update
    }

    // Display checkout page
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

        return "checkout"; // Render checkout view
    }

    // Process the checkout and payment
    @PostMapping("/process-checkout")
    public String processCheckout(@RequestParam String fullName,
                                  @RequestParam String clientsMail,
                                  @RequestParam String billingAddress,
                                  @RequestParam String billingAddressApartment,
                                  @RequestParam String billingCity,
                                  @RequestParam String billingAddressProvince,
                                  @RequestParam String billingPostal,
                                  @RequestParam String paymentMethod,
                                  Model model) {

        List<CartItem> cartItems = cartService.getCartItems();
        Order order = new Order();

        order.setFullName(fullName);
        order.setEmail(clientsMail);
        order.setBillingAddress(billingAddress);
        order.setBillingAddressApartment(billingAddressApartment);
        order.setBillingCity(billingCity);
        order.setBillingAddressProvince(billingAddressProvince);
        order.setBillingPostal(billingPostal);
        order.setStatus("pending");
        order.setTotalPrice(calculateTotalAmount(cartItems)); // Calculate total amount

        order = orderService.saveOrder(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderService.saveOrder(order);

        boolean isPaymentSuccessful = cartService.processPayment(paymentMethod, billingAddress, null);
        if (isPaymentSuccessful) {
            cartService.clearCart(); // Clear the cart if payment is successful
            emailService.sendOrderConfirmationEmail(order.getEmail(), order, order.getId()); // Send confirmation email
            //return "redirect:/order-confirmation"; // Redirect to order confirmation page
            return "cart";
        } else {
            model.addAttribute("error", "Payment failed. Please try again.");
            return "checkout"; // Stay on checkout page if payment fails
        }
    }

    // Calculate the total amount including taxes and delivery fee
    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            total = total.add(item.getTotalPrice());
        }

        BigDecimal gst = total.multiply(BigDecimal.valueOf(0.05)); // 5% GST
        BigDecimal qst = total.multiply(BigDecimal.valueOf(0.09975)); // 9.975% QST
        BigDecimal taxes = gst.add(qst);
        BigDecimal deliveryFee = BigDecimal.valueOf(7.15); // Delivery fee

        return total.add(taxes).add(deliveryFee); // Total amount including taxes and delivery fee
    }

    // Clear the shopping cart
    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "cart"; // Render cart view after clearing
    }
}
