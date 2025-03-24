package laboratory.fsqsWholeSale.data.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String address;

    // Using totalPrice instead of totalAmount for monetary values
    private BigDecimal totalPrice;

    private String fullName;
    private String email;
    private String billingAddress;
    private String billingAddressApartment;
    private String billingAddressProvince;
    private String billingPostal;
    private String status; // "pending" initially

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    public String getStatus() {
        return status == null ? "pending" : status;  // Return "pending" if status is null
    }

    public void setStatus(String status) { this.status = status; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getBillingAddressApartment() { return billingAddressApartment; }
    public void setBillingAddressApartment(String billingAddressApartment) { this.billingAddressApartment = billingAddressApartment; }

    public String getBillingAddressProvince() { return billingAddressProvince; }
    public void setBillingAddressProvince(String billingAddressProvince) { this.billingAddressProvince = billingAddressProvince; }

    public String getBillingPostal() { return billingPostal; }
    public void setBillingPostal(String billingPostal) { this.billingPostal = billingPostal; }
}
