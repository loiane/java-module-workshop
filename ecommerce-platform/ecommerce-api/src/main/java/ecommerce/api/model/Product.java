package ecommerce.api.model;

public class Product {

    private final Integer id;
    private final String title;
    private final Double unitPrice;

    public Product(Integer id, String title, Double unitPrice) {

        this.id = id;
        this.title = title;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
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
