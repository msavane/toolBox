package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.OrderRepository;
import laboratory.fsqsWholeSale.data.ProductRepository;
import laboratory.fsqsWholeSale.data.model.Order;
import laboratory.fsqsWholeSale.data.model.OrderItem;
import laboratory.fsqsWholeSale.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final laboratory.fsqsWholeSale.data.OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();  // Return all orders from the repository
    }

    public Order getOrderById(Long id) {
        // Return the Order or null if not found
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order saveOrder(Order order) {
        // Iterate over each order item
        for (OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check stock availability
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Update stock and price at the time of purchase
            product.setStock(product.getStock() - item.getQuantity());
            //productRepository.save(product);  // Save the updated product

            item.setPriceAtPurchase(product.getPrice());  // Set price at the time of purchase



        }

        // Save and return the order
        return orderRepository.save(order);
    }



    public void deleteOrder(Long id) {
        // Delete the order by its ID
        orderRepository.deleteById(id);
    }
}
