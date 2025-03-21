package laboratory.fsqsWholeSale.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Min(0)
    private double price;

    @Min(0)
    private int stock;

    private String imageUri; // New field for storing image location

    // Default constructor (required by JPA/Hibernate)
    public Product() {
        // Empty constructor
    }

    // Constructor without the 'id' field as it will be auto-generated
    public Product(String name, String description, double price, int stock, String imageUri) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUri = imageUri;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
