package ecommerce.shared.model;

import static java.lang.String.format;

public  class ItemWithCount {

    private final Integer productId;
    private final Double unitPrice;
    private final Integer count;

    public ItemWithCount(Integer productId, Double unitPrice, Integer count) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.count = count;
    }

    public Integer getProductId() {
        return productId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return format("{\"productId\":%d, \"unitPrice\":%.2f, \"count\":%d}", productId, unitPrice, count);
    }
}
