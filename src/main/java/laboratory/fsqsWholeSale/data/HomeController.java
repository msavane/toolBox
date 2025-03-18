package laboratory.fsqsWholeSale.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RssService rssService;

    // Default home page and products page combined
    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "4") int pageSize, Model model) {
        return getProductsPage(page, pageSize, model);
    }

    // Handle /products page, pagination included
    @GetMapping("/products")
    public String productsPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "4") int pageSize, Model model) {
        return getProductsPage(page, pageSize, model);
    }

    private String getProductsPage(int page, int pageSize, Model model) {
        try {
            // Fetch paginated products from ProductService
            Page<Product> productPage = productService.getPaginatedProducts(page, pageSize);

            // Add attributes to the model
            model.addAttribute("products", productPage.getContent()); // Current page products
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages()); // Total pages for pagination control
            model.addAttribute("hasNextPage", productPage.hasNext());
            model.addAttribute("hasPreviousPage", productPage.hasPrevious()); // Add previous page support

            // Fetch and add the first RSS feed (Default RSS)
           // String rssUrl = "https://lepatriote.ci/rss/category/economie"; // Default RSS URL
            String rssUrl = "https://www.bankofcanada.ca/content_type/canadian-survey-of-consumer-expectations/feed/";
            model.addAttribute("rssFeeds", rssService.fetchRssFeed(rssUrl)); // Pass the fetched RSS feeds

            // Fetch and add the second RSS feed (You can change the URL below)
            String secondRssUrl = "https://www.cirad.fr/rss/actualites"; // Second RSS URL
            model.addAttribute("secondRssFeeds", rssService.fetchRssFeed(secondRssUrl)); // Pass second RSS feeds

            model.addAttribute("loading", false);
            model.addAttribute("error", null);
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", "Failed to load products or RSS feeds.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }

        return "index"; // Return the view (index.html for Thymeleaf)
    }


    @GetMapping("/inventaire-magasin")
    public String showInventoryPage(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        // Fetch paginated products based on the page and pageSize
        model.addAttribute("products", productService.getAllProducts(page, pageSize));
        return "inventaire-magasin"; // Thymeleaf template
    }


    // Display add product form
    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Provide an empty Product object for the form
        return "add-product";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId) {
        // Add the product to the user's cart (logic here)
        return "redirect:/cart"; // Redirect to the cart page or wherever you want
    }
    @GetMapping("/cart")
    public String showCart(Model model) {
        // Logic to show the cart contents
        return "cart"; // The name of the cart page, ensure there's a cart.html or cart.html template
    }

    // Handle the add product form submission
    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            productService.saveProduct(product);
            return "redirect:/";  // Redirect to home after adding
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "add-product"; // Stay on the page in case of an error
        }
    }
}
