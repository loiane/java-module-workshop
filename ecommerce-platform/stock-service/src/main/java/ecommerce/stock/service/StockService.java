package ecommerce.stock.service;

import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.core.Stock;
import jeventbus.service.EventService;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import static ecommerce.shared.event.ECommerceEventType.STOCK_ADDED;
import static ecommerce.shared.event.ECommerceEventType.STOCK_CHECKOUTED;
import static jeventbus.shared.Parameter.by;

@ApplicationScoped
public class StockService implements EventListener {

    private final Stock stock = new Stock();

    @Inject
    private final EventProducerService eventService;

    public StockService(EventProducerService eventService) {

        this.eventService = eventService;
    }

    public void add(Long productId, Integer count) {
        stock.add(productId, count);

        eventService.fireStockAdded(productId, count);
    }

    public Integer getCount(Long productId) {
        return stock.getCount(productId);
    }

    public void checkout(Long productId, Integer countToCheckout) {
        Integer currentCount = stock.checkout(productId, countToCheckout);

        eventService.fireStockCheckouted(productId, countToCheckout, currentCount);

    }

    public void onOrder(EventSource source) {
        List<ItemWithCount> items = (List<ItemWithCount>) source.get("items");
        items.stream().forEach(i->checkout(i.getProductId(), i.getCount()));
    }
}
