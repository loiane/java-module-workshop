package ecommerce.basket.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Baskets {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    private Map<Long, Basket> baskets = new HashMap<>();

    public Optional<Basket> get(Long buyerId) {
        return Optional.ofNullable(baskets.get(buyerId));
    }

    public Basket getOrCreate(Long buyerId) {
        if (baskets.containsKey(buyerId) ) {
            return baskets.get(buyerId);
        } else {
            return create(buyerId);
        }
    }

    public Basket create(Long buyerId) {

        Basket basket = new Basket(idGenerator.getAndAdd(1), buyerId);
        baskets.put(buyerId, basket);
        return basket;
    }

    public Basket clear(Long buyerId) {

        return baskets.remove(buyerId);
    }
}
