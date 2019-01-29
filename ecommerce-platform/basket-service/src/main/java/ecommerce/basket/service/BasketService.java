package ecommerce.basket.service;

import ecommerce.basket.core.Basket;
import ecommerce.basket.core.Baskets;
import ecommerce.basket.core.BasketItem;
import ecommerce.shared.event.OrderListener;
import jeventbus.service.EventService;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;

import java.util.Optional;

import static ecommerce.shared.event.ECommerceEventType.*;

public class BasketService implements OrderListener {

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
        return basket;
    }

    public void clear(Integer buyerId) {

        Basket clearedBasket = baskets.clear(buyerId);

        eventService.fire(BASKET_CLEARED,
                          Parameter.by("buyerId", buyerId),
                          Parameter.by("basketId", clearedBasket.getId()),
                          Parameter.by("totalPrice", clearedBasket.getTotalPrice()));
    }

    public Integer addItem(Integer buyerId, Integer productId, Double unitPrice, Integer count) {

        BasketItem item = new BasketItem(productId, unitPrice, count);

        Basket basket = baskets.getOrCreate(buyerId);

        Integer latestCount = basket.add(item);

        eventService.fire(BASKET_ITEM_ADDED,
                          Parameter.by("buyerId", buyerId),
                          Parameter.by("productId", productId),
                          Parameter.by("unitPrice", unitPrice),
                          Parameter.by("count", count));

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
            return latestCount;

        } else {
            // TODO: log warn : basket not found but return 0
            return 0;
        }

    }

    public void removeItem(Integer buyerId, Integer productId) {

        Optional<Basket> basket = baskets.get(buyerId);

        basket.ifPresent(b->b.remove(productId));

        eventService.fire(BASKET_ITEM_REMOVED,
                          Parameter.by("buyerId", buyerId),
                          Parameter.by("productId", productId));
    }

    @Override
    public void onOrder(EventSource source) {
        Integer buyerId = (Integer) source.get("buyerId");
        clear(buyerId);
    }
}
