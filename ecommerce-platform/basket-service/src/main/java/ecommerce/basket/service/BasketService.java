package ecommerce.basket.service;

import ecommerce.basket.core.Basket;
import ecommerce.basket.core.BasketItem;
import ecommerce.basket.core.Baskets;
import jeventbus.service.EventService;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;

import java.util.Optional;

import static ecommerce.shared.event.ECommerceEventType.*;

public class BasketService implements EventListener {

    private final Baskets baskets = new Baskets();

    private final EventService eventService;

    public BasketService(EventService eventService) {

        this.eventService = eventService;
    }

    public Optional<Basket> get(Integer buyerId) {

        return baskets.get(buyerId);
    }

    public Basket create(Integer buyerId) {

        Basket basket = baskets.create(buyerId);

        eventService.fire(BASKET_CREATED,
                          Parameter.by("buyerId", buyerId));

        System.out.println(String.format("BASKET CREATED : {\"buyerId\":%d}",
                                         buyerId));
        return basket;
    }

    public void clear(Integer buyerId) {

        Basket clearedBasket = baskets.clear(buyerId);

        eventService.fire(BASKET_CLEARED,
                          Parameter.by("buyerId", buyerId),
                          Parameter.by("basketId", clearedBasket.getId()),
                          Parameter.by("totalPrice", clearedBasket.getTotalPrice()));

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

            eventService.fire(BASKET_ITEM_COUNT_DECREASED,
                              Parameter.by("buyerId", buyerId),
                              Parameter.by("productId", productId),
                              Parameter.by("count", count));

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

        eventService.fire(BASKET_ITEM_REMOVED,
                          Parameter.by("buyerId", buyerId),
                          Parameter.by("productId", productId));

        System.out.println(String.format("BASKET REMOVE ITEM : {\"buyerId\":%d, \"productId\":%d }", buyerId, productId));
    }

    private void onOrder(EventSource source) {
        Integer buyerId = (Integer) source.get("buyerId");
        clear(buyerId);
    }
}
