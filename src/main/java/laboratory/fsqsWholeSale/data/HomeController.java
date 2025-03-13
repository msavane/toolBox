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

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10; // Display 10 products per page

        try {
            // Fetch paginated products from ProductService
            Page<Product> productPage = productService.getPaginatedProducts(page, pageSize);

            // Add attributes to the model
            model.addAttribute("products", productPage.getContent()); // Current page products
            model.addAttribute("currentPage", page);
            model.addAttribute("hasNextPage", productPage.hasNext());
            model.addAttribute("hasPreviousPage", productPage.hasPrevious()); // Add previous page support
            model.addAttribute("loading", false);
            model.addAttribute("error", null);
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", "Failed to load products.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }

        return "index"; // Load index.html (Thymeleaf)
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Provide an empty Product object for the form
        return "add-product";
    }

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
