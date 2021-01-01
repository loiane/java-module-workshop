package ecommerce.basket.service;

import ecommerce.basket.core.Basket;
import ecommerce.basket.core.BasketItem;
import ecommerce.basket.core.Baskets;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

import static java.lang.String.format;

@ApplicationScoped
public class BasketService implements EventListener {

    private final Baskets baskets = new Baskets();

    private final EventProducerService eventProducerService;

    @Inject
    public BasketService(EventProducerService eventProducerService) {

        this.eventProducerService = eventProducerService;
    }

    public Optional<Basket> get(Long buyerId) {

        return baskets.get(buyerId);
    }

    public Basket create(Long buyerId) {

        Basket basket = baskets.getOrCreate(buyerId);

        eventProducerService.fireBasketCreated(buyerId, basket.getId());

        return basket;
    }

    public void clear(Long buyerId) {

        baskets.get(buyerId)
                .ifPresentOrElse(
                        b-> {
                                baskets.clear(buyerId);
                                eventProducerService.fireBasketCleared(buyerId, b.getId(), b.getTotalPrice());
                            },
                        ()->System.out.println(format("Basket not found for buyer{%d}",buyerId)));

    }

    public Integer addItem(Long buyerId, Long productId, Double unitPrice, Integer count) {

        BasketItem item = new BasketItem(productId, unitPrice, count);

        Basket basket = baskets.getOrCreate(buyerId);

        Integer latestCount = basket.add(item);

        eventProducerService.fireItemAddedToBasket(buyerId.longValue(), basket.getId().longValue(), unitPrice, latestCount, productId.longValue());

        return latestCount;
    }

    public Integer decCountOfItem(Long buyerId, Long productId, Integer count) {
        Optional<Basket> basket = baskets.get(buyerId);

        if (basket.isPresent()) {
            Integer latestCount = basket.get().dec(productId, count);

            eventProducerService.fireItemDecreasedToBasket(buyerId, basket.get().getId(), productId, count, latestCount);

            return latestCount;

        } else {
            // TODO: log warn : basket not found but return 0
            return 0;
        }

    }

    public void removeItem(Long buyerId, Long productId) {

        Optional<Basket> basket = baskets.get(buyerId);

        basket.ifPresent(b -> b.remove(productId));

        eventProducerService.fireItemRemoved(buyerId, basket.get().getId(), productId);
    }

    public void onOrder(EventSource source) {
        Long buyerId = (Long) source.get("buyerId");
        clear(buyerId);
    }
}
