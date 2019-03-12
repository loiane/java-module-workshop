package ecommerce.api.model;

import java.util.concurrent.atomic.AtomicInteger;

public class BasketItem {

    // TODO : Decouple product dependency
    private final Product product;
    private final AtomicInteger count = new AtomicInteger(0);

    public BasketItem(Product product) {

        this.product = product;
    }

    public BasketItem(Product product,  Integer count) {

        this.product = product;
        this.count.set(count);
    }

    public Product getProduct() {

        return product;
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

    @Override
    public String toString() {
        return String.format("{ productId:%d, unitPrice:%d, count:%d }", product, product.getUnitPrice(), count.get());
    }
}
