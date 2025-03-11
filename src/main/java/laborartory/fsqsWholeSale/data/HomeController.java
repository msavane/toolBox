package laborartory.fsqsWholeSale.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        try {
            // Dummy product list (replace with service call to fetch from DB)
            List<Product> products = List.of(
                    new Product("Organic Tomatoes", "Fresh organic tomatoes.", 5.99, 100),
                    new Product("Golden Corn", "High-quality farm-grown corn.", 3.49, 200),
                    new Product("Raw Honey", "Pure and natural honey.", 9.99, 150)
            );

            model.addAttribute("products", products);
            model.addAttribute("loading", false);
            model.addAttribute("error", null);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }

        return "index"; // Thymeleaf template that matches your index.html file
    }
}
