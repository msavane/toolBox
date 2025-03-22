package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final List<CartItem> cartItems = new ArrayList<>();
    private static final int MAX_QTY = 5; // Maximum allowed quantity per product

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                int newQuantity = Math.min(item.getQuantity() + quantity, MAX_QTY);
                item.setQuantity(newQuantity);
                updateItemPrice(item);
                return;
            }
        }

        int initialQuantity = Math.min(quantity, MAX_QTY);
        BigDecimal totalPrice = BigDecimal.valueOf(product.getStock()*product.getPrice()).multiply(BigDecimal.valueOf(initialQuantity));
        cartItems.add(new CartItem(product, initialQuantity, totalPrice));
    }

    public void updateCartItemQuantity(Long productId, int newQuantity) {
        if (productId == null) {
            return; // Prevent NullPointerException
        }

        Optional<CartItem> cartItemOpt = cartItems.stream()
                .filter(item -> item.getProduct() != null && item.getProduct().getId().equals(productId))
                .findFirst();

        cartItemOpt.ifPresent(item -> {
            int validQuantity = Math.max(1, Math.min(newQuantity, MAX_QTY)); // Ensure quantity is between 1 and MAX_QTY
            item.setQuantity(validQuantity);
            updateItemPrice(item);
        });
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public BigDecimal calculateTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(calculateShippingCost()); // Include shipping
    }

    public BigDecimal calculateShippingCost() {
        return BigDecimal.valueOf(5.00); // Flat shipping rate
    }

    public void clearCart() {
        cartItems.clear();
    }

    private void updateItemPrice(CartItem item) {

        BigDecimal totalPrice = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getProduct().getPrice()*item.getStock()));
      //  BigDecimal totalPrice = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getProduct().getPrice()));
        item.setTotalPrice(totalPrice);
    }
}
