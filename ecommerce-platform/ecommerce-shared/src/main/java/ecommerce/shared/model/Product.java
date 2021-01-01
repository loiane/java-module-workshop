package ecommerce.shared.model;

public class Product {

    private final Long id;
    private final String title;
    private final Double unitPrice;

    public Product(Long id, String title, Double unitPrice) {

        this.id = id;
        this.title = title;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

   public  Double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return String.format("Product{\"id\":%d, \"title\":%s, \"unitPrice\":%.2f}", id, title, unitPrice);
    }
}
