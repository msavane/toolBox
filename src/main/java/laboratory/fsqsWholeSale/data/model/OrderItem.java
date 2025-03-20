package laboratory.fsqsWholeSale.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "order_items")
public class OrderItem  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Min(1)
    private int quantity;
    private double priceAtPurchase;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
