package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                // Increase quantity
                item.setQuantity(item.getQuantity() + quantity);

                // Update total price correctly
                BigDecimal totalPrice = BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(product.getPrice()));
                item.setTotalPrice(totalPrice);
                return;
            }
        }

        // Add new item with correct quantity
        BigDecimal totalPrice = BigDecimal.valueOf(quantity).multiply(BigDecimal.valueOf(product.getPrice()));
        cartItems.add(new CartItem(product, quantity, totalPrice));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal calculateTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateShippingCost() {
        return BigDecimal.valueOf(5.00); // Flat rate for simplicity
    }

    public void clearCart() {
        cartItems.clear();
    }
}
