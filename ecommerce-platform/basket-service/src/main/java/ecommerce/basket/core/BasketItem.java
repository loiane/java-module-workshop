package ecommerce.basket.core;

import java.util.concurrent.atomic.AtomicInteger;

public class BasketItem {

    private final Integer productId;
    private final Double unitPrice;
    private final AtomicInteger count = new AtomicInteger(0);

    public BasketItem(Integer productId, Double unitPrice) {

        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    public BasketItem(Integer productId, Double unitPrice, Integer count) {

        this.productId = productId;
        this.unitPrice = unitPrice;
        this.count.set(count);
    }

    public Integer getProductId() {

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
