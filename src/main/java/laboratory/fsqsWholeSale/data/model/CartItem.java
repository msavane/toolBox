package laboratory.fsqsWholeSale.data.model;

import java.math.BigDecimal;

public class CartItem {
    private Product product;
    private int quantity;
    private BigDecimal totalPrice;

    // Constructor accepting BigDecimal for price
    public CartItem(Product product, int quantity, BigDecimal totalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Constructor accepting double, converting to BigDecimal
    public CartItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));
    }

    public Product getProduct() {
        return product;
    }

    public double getStock() {
        return product.getStock();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


}
