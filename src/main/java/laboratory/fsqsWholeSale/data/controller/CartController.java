package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/shopping-cart ")
    public String showCart(Model model) {  // Ensure 'Model' is properly imported
        // Retrieve cart items and relevant totals from your service
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalPrice = cartService.calculateTotalPrice();
        BigDecimal shippingCost = cartService.calculateShippingCost();
        BigDecimal totalAmount = totalPrice.add(shippingCost);

        // Debugging: Print values to ensure they are retrieved correctly
        System.out.println("Cart Items: " + cartItems);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Shipping Cost: " + shippingCost);
        System.out.println("Total Amount: " + totalAmount);

        // Add attributes to the model to be used in Thymeleaf template
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingCost", shippingCost);
        model.addAttribute("totalAmount", totalAmount);

        return "cart";  // Return the Thymeleaf template
    }

    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "cart";
    }


}
