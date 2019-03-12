package ecommerce.api.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Baskets {

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private Map<Integer, Basket> baskets = new HashMap<>();

    public Optional<Basket> get(Integer buyerId) {
        return Optional.ofNullable(baskets.get(buyerId));
    }

    public Basket getOrCreate(Integer buyerId) {
        if (baskets.containsKey(buyerId) ) {
            return baskets.get(buyerId);
        } else {
            return create(buyerId);
        }
    }

    public Basket create(Integer buyerId) {

        Basket basket = new Basket(idGenerator.getAndAdd(1), buyerId);
        baskets.put(buyerId, basket);
        return basket;
    }

    public Basket clear(Integer buyerId) {

        return baskets.remove(buyerId);
    }
}
