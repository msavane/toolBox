package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

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

   /* @PostMapping("/process-checkout"){
        return null;
    }*/

    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }
}
