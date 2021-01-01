package ecommerce.basket.core;

import java.util.concurrent.atomic.AtomicInteger;

public class BasketItem {

    private final Long productId;
    private final Double unitPrice;
    private final AtomicInteger count = new AtomicInteger(0);

    public BasketItem(Long productId, Double unitPrice) {

        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    public BasketItem(Long productId, Double unitPrice, Integer count) {

        this.productId = productId;
        this.unitPrice = unitPrice;
        this.count.set(count);
    }

    public Long getProductId() {

        return productId;
    }

    public Integer getCount() {

        return count.get();
    }

    public Integer incCountAndGet(Integer count) {

        return this.count.addAndGet(count);
    }

    public Integer decCountAndGet(Integer count) {

        return this.count.addAndGet(-1 * count);
    }

    public Double getUnitPrice() {

        return unitPrice;
    }

    @Override
    public String toString() {
        return String.format("{ productId:%d, unitPrice:%d, count:%d }", productId, unitPrice, count.get());
    }
}
