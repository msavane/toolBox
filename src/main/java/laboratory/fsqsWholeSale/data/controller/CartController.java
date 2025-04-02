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

    private Order order = new Order();
    private  OrderItem orderItem = new OrderItem();

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

    @PostMapping("/process-checkout")
    public String processCheckout(@RequestParam(required = false) String fullName,
                                  @RequestParam(required = false) String clientsMail,
                                  @RequestParam(required = false) String billingAddress,
                                  @RequestParam(required = false) String billingAddressApartment,
                                  @RequestParam(required = false) String billingAddressProvince,
                                  @RequestParam(required = false) String billingPostal,
                                  @RequestParam(required = false) String paymentMethod,
                                  Model model) {



        if (fullName != null && clientsMail != null && billingAddress != null &&
                billingAddressApartment != null && billingAddressProvince != null && billingPostal != null) {
            // New functionality to process full checkout details
            List<CartItem> cartItems = cartService.getCartItems();

            order.setFullName(fullName);
            order.setEmail(clientsMail);
            order.setBillingAddress(billingAddress);
            order.setBillingAddressApartment(billingAddressApartment);
            order.setBillingAddressProvince(billingAddressProvince);
            order.setBillingPostal(billingPostal);
            order.setStatus("pending");
            order.setTotalPrice(calculateTotalAmount(cartItems));

            // Save order and order items
            order = orderService.saveOrder(order);

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cartItems) {

                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPriceAtPurchase(cartItem.getTotalPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }

            order.setOrderItems(orderItems);
            System.out.println(order.getTotalPrice().toString());
            orderService.saveOrder(order);

            // Additional logic for payment processing
            boolean isPaymentSuccessful = cartService.processPayment(paymentMethod, billingAddress, null);
            isPaymentSuccessful=true;
            if (isPaymentSuccessful) {
                cartService.clearCart(); // Empty the cart after successful checkout
                // Send order confirmation email
                // emailService.sendOrderConfirmationEmail(clientsMail, order);
                // return "redirect:/order-confirmation";
                return "cart";
            } else {
                model.addAttribute("error", "Payment failed. Please try again.");
                return "cart";
            }
        } else {
            model.addAttribute("error", "Incomplete checkout details. Please fill in all required fields.");
            return "cart";
        }
    }


    // Helper method to calculate total order amount
    private BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO; // Start with 0
        BigDecimal afterTax = BigDecimal.ZERO;
        BigDecimal price = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
// Assuming item.getTotalPrice() returns BigDecimal and item.getQuantity() returns int
            price = price.add(item.getTotalPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity()); // Convert int to BigDecimal


// Calculate total before taxes
            total = price; // No need to add to zero-initialized total


        }

// Corrected tax calculations
        BigDecimal gst = total.multiply(BigDecimal.valueOf(0.05)); // 5% GST
        BigDecimal qst = total.multiply(BigDecimal.valueOf(0.09975)); // 9.975% QST
        BigDecimal Taxes = qst.add(gst);

// Define delivery fee
        BigDecimal deliveryFee = BigDecimal.valueOf(7.15);

// Calculate total after taxes, including the delivery fee
        afterTax = deliveryFee.add( total.add(Taxes));

// Debugging print
        System.out.println("Total: " + total + " | GST: " + gst + " | QST: " + qst + " | Delivery: " + deliveryFee + " | After Tax: " + afterTax);

        emailService.sendOrderConfirmationEmail(order.getEmail(),order);
        return afterTax;
    }

    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }
}