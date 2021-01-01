package ecommerce.stock.core;

import ecommerce.shared.model.ItemWithCount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Stock {

    private Map<Long, AtomicInteger> productCounts = new HashMap<>();

    public void add(Long productId, Integer count) {
        AtomicInteger counts = productCounts.getOrDefault(productId, new AtomicInteger(0));
        counts.addAndGet(count);
        productCounts.putIfAbsent(productId, counts);
    }

    public Integer getCount(Long productId) {
        return productCounts.getOrDefault(productId, new AtomicInteger(0)).get();
    }

    public Integer checkout(Long productId, Integer countToCheckout) {
        AtomicInteger countInStock = productCounts.getOrDefault(productId, new AtomicInteger(0));

//        if (countToCheckout > countInStock.get()) {
//            throw new NotEnoughStockException(productId, countToCheckout, countInStock.get());
//        }

        return countInStock.addAndGet(-1 * countToCheckout);
    }
}
