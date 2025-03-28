package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.Product;
import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.ProductService;
import laboratory.fsqsWholeSale.data.service.CartService;
import laboratory.fsqsWholeSale.data.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private RssService rssService;

    private String categoriesVals;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "4") int pageSize,
                       @RequestParam(defaultValue = "all") String category,
                       Model model) {

        // Load products for each category and store them under distinct names in the model
        getProductsPage(page, pageSize, "veggie", model);  // stores "veggieProducts"
        getProductsPage(page, pageSize, "aisles", model);  // stores "aislesProducts"
        getProductsPage(page, pageSize, "frozen", model);  // stores "frozenProducts"

        // Now load the selected category data
        return getProductsPage(page, pageSize, category, model);
    }


    @GetMapping("/products")
    public String productsPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "4") int pageSize,
                               @RequestParam(defaultValue = "all") String category,
                               Model model) {
        return getProductsPage(page, pageSize, category, model);
    }

    @GetMapping("/aisles")
    public String setAislesCategory(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        return getProductsPage(page, pageSize, "aisles", model);
    }

    @GetMapping("/veggie")
    public String setFruitsCategory(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        return getProductsPage(page, pageSize, "veggie", model);
    }

    @GetMapping("/frozen")
    public String setFrozenCategory(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        return getProductsPage(page, pageSize, "frozen", model);
    }

    private String getProductsPage(int page, int pageSize, String category, Model model) {
        try {
            Page<Product> productPage;

            // Handle the category-specific product page
            if ("veggie".equalsIgnoreCase(category)) {
                productPage = productService.getFruitsPage(page, pageSize);
            } else if ("aisles".equalsIgnoreCase(category)) {
                productPage = productService.getAislesPage(page, pageSize);
            } else if ("frozen".equalsIgnoreCase(category)) {
                productPage = productService.getFrozenPage(page, pageSize);
            } else {
                productPage = productService.getPaginatedProducts(page, pageSize);
            }

            List<Product> products = productPage.getContent();
            model.addAttribute("category", category);
            model.addAttribute("products", products);

            // Set pagination info
            model.addAttribute("currentPage", productPage.getNumber() );
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("hasNextPage", productPage.hasNext());
            model.addAttribute("hasPreviousPage", productPage.hasPrevious());

            // Category-specific pagination (just add once based on the category)
            if ("veggie".equalsIgnoreCase(category)) {
                model.addAttribute("fruitsPage", productPage.getNumber() + 1);
                model.addAttribute("fruitsTotalPages", productPage.getTotalPages());
            } else if ("aisles".equalsIgnoreCase(category)) {
                model.addAttribute("aislesPage", productPage.getNumber() + 1);
                model.addAttribute("aislesTotalPages", productPage.getTotalPages());
            } else if ("frozen".equalsIgnoreCase(category)) {
                model.addAttribute("frozenGoodsPage", productPage.getNumber() + 1);
                model.addAttribute("frozenGoodsTotalPages", productPage.getTotalPages());
            }else {
                model.addAttribute("currentPage", productPage.getNumber()+1 );
                model.addAttribute("totalPages", productPage.getTotalPages());
                model.addAttribute("hasNextPage", productPage.hasNext());
                model.addAttribute("hasPreviousPage", productPage.hasPrevious());
            }

        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products or RSS feeds.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }
        return "index";
    }



    @GetMapping("/inventaire-magasin")
    public String showInventoryPage(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        model.addAttribute("products", productService.getAllProducts(page, pageSize));
        return "inventaire-magasin";
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            productService.saveProduct(product);
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "add-product";
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> searchResults = productService.searchProducts(query);
        model.addAttribute("products", searchResults);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1); // Only one page for search results
        return "index"; // Ensure "index.html" contains the product table
    }


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            Model model) {
        try {
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            cartService.addToCart(product, quantity);

            List<CartItem> cartItems = cartService.getCartItems();
            BigDecimal totalPrice = cartService.calculateTotalPrice();
            BigDecimal shippingCost = cartService.calculateShippingCost();
            BigDecimal totalAmount = totalPrice.add(shippingCost);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("shippingCost", shippingCost);
            model.addAttribute("totalAmount", totalAmount);

            return "cart";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product to cart.");
            return "error";
        }
    }
}
