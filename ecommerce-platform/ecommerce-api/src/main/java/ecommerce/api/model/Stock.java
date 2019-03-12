package ecommerce.api.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Stock {

    private Map<Integer, AtomicInteger> productCounts = new HashMap<>();

    public void add(Product product, Integer count) {
        AtomicInteger counts = productCounts.getOrDefault(product.getId(), new AtomicInteger(0));
        counts.addAndGet(count);
        productCounts.putIfAbsent(product.getId(), counts);
    }

    public Integer getCount(Integer productId) {
        return productCounts.getOrDefault(productId, new AtomicInteger(0)).get();
    }

    public void checkout(Product product, Integer countToCheckout) {
        AtomicInteger countInStock = productCounts.getOrDefault(product.getId(), new AtomicInteger(0));

//        if (countToCheckout > countInStock.get()) {
//            throw new NotEnoughStockException(productId, countToCheckout, countInStock.get());
//        }

        countInStock.addAndGet(-1 * countToCheckout);
    }
}
