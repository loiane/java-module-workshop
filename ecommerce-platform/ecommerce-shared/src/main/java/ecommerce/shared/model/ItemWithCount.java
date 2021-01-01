package ecommerce.shared.model;

import static java.lang.String.format;

public  class ItemWithCount {

    private Long productId;
    private Double unitPrice;
    private Integer count;

    public ItemWithCount(){

    }

    public ItemWithCount(Long productId, Double unitPrice, Integer count) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.count = count;
    }

    public Long getProductId() {
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
