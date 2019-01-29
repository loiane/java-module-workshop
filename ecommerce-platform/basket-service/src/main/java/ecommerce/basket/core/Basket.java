package ecommerce.basket.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class Basket {

    private final Integer id;
    private final Integer buyerId;
    private Map<Integer, BasketItem> items = new HashMap<>();

    public Basket(Integer id, Integer buyerId) {
        this.id = id;
        this.buyerId = buyerId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public Map<Integer, BasketItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public Integer add(BasketItem item) {

        Integer productId = item.getProductId();
        Double unitPrice = item.getUnitPrice();

        BasketItem itemInBasket = items.getOrDefault(productId, new BasketItem(productId, unitPrice));

        Integer latestCount = itemInBasket.incCountAndGet(item.getCount());

        items.putIfAbsent(productId, itemInBasket);

        return latestCount;
    }

    public Integer dec(Integer productId, Integer countToDec) {

        Integer latestCount = 0;
        BasketItem itemInBasket = items.get(productId);

        if (nonNull(itemInBasket)) {
            latestCount = itemInBasket.decCountAndGet(countToDec);
        }

        return latestCount;
    }

    public void remove(Integer productId) {

        items.remove(productId);
    }

    public Double getTotalPrice() {
        return items.values().stream().mapToDouble(i->i.getUnitPrice() * i.getCount()).sum();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Basket is empty!!!";
        } else {
            return items.values().stream().map(i->i.toString()).collect(Collectors.joining(",\\n"));
        }
    }

}
