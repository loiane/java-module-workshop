package ecommerce.basket.service;

import ecommerce.basket.core.Basket;
import ecommerce.basket.core.Baskets;
import ecommerce.basket.core.BasketItem;

import java.util.Optional;


public class BasketService {

    private final Baskets baskets = new Baskets();

    public Optional<Basket> get(Integer buyerId) {

        return baskets.get(buyerId);
    }

    public Basket create(Integer buyerId) {

        Basket basket = baskets.create(buyerId);

        System.out.println(String.format("BASKET CREATED : {\"buyerId\":%d}",
                                         buyerId));
        return basket;
    }

    public void clear(Integer buyerId) {

        Basket clearedBasket = baskets.clear(buyerId);

        System.out.println(String.format("BASKET CLEARED : {\"buyerId\":%d, \"basketId\":%d, \"totalPrice\":%.2f}",
                                         buyerId,
                                         clearedBasket.getId(),
                                         clearedBasket.getTotalPrice()));
    }

    public Integer addItem(Integer buyerId, Integer productId, Double unitPrice, Integer count) {

        BasketItem item = new BasketItem(productId, unitPrice, count);

        Basket basket = baskets.getOrCreate(buyerId);

        Integer latestCount = basket.add(item);

        System.out.println(String.format("BASKET ADD ITEM : {\"buyerId\":%d, \"productId\":%d, \"unitPrice\":%.2f, \"count\":%d }",
                                         buyerId, productId, unitPrice, count));

        return latestCount;
    }

    public Integer decCountOfItem(Integer buyerId, Integer productId, Integer count) {
        Optional<Basket> basket = baskets.get(buyerId);

        if (basket.isPresent()) {
            Integer latestCount = basket.get().dec(productId, count);

            System.out.println(String.format("BASKET DEC ITEM COUNT : {\"buyerId\":%d, \"productId\":%d, \"countToDec\":%d, \"latestCount\":%d }",
                                             buyerId, productId, count, latestCount));
            return latestCount;

        } else {
            // TODO: log warn : basket not found but return 0
            return 0;
        }

    }

    public void removeItem(Integer buyerId, Integer productId) {

        Optional<Basket> basket = baskets.get(buyerId);

        basket.ifPresent(b -> b.remove(productId));

        System.out.println(String.format("BASKET REMOVE ITEM : {\"buyerId\":%d, \"productId\":%d }", buyerId, productId));
    }
}
