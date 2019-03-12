package ecommerce.api.model;

import static java.lang.String.format;

public  class ItemWithCount {

    private final Product product;
    private final Integer count;

    public ItemWithCount(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return format("{\"productId\":%d, \"unitPrice\":%.2f, \"count\":%d}", product.getId(), product.getUnitPrice(), count);
    }
}
