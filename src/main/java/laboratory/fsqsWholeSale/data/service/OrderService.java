package laboratory.fsqsWholeSale.data.service;

import laboratory.fsqsWholeSale.data.OrderRepository;
import laboratory.fsqsWholeSale.data.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
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
            /*if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }*/

            // Update stock and price at the time of purchase
            //product.setStock(product.getStock() - item.getQuantity());
            item.setPriceAtPurchase(product.getPrice());

            // **Ensure OrderItem is linked to the Order**
            item.setOrder(order);
        }

        // Save the order and return it
        return orderRepository.save(order);
    }

    @Transactional
    public void saveOrderItem(OrderItem orderItem) {
      /*  Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < orderItem.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        orderItem.setPriceAtPurchase(product.getPrice());
*/
        orderItemRepository.save(orderItem);


    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
