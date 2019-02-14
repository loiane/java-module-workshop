package ecommerce.stock.service;

import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.core.Stock;
import jeventbus.service.EventService;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

import java.util.List;

import static ecommerce.shared.event.ECommerceEventType.STOCK_ADDED;
import static ecommerce.shared.event.ECommerceEventType.STOCK_CHECKOUTED;
import static jeventbus.shared.Parameter.by;

public class StockService implements EventListener {

    private final Stock stock = new Stock();

    private final EventService eventService;

    public StockService(EventService eventService) {

        this.eventService = eventService;
    }

    public void add(Integer productId, Integer count) {
        stock.add(productId, count);

        eventService.fire(STOCK_ADDED, by("productId", productId), by("count",count));
    }

    public Integer getCount(Integer productId) {
        return stock.getCount(productId);
    }

    public void checkout(ItemWithCount item) {
        Integer productId = item.getProductId();
        Integer countToCheckout = item.getCount();
        Integer currentCount = stock.checkout(productId, countToCheckout);

        eventService.fire(STOCK_CHECKOUTED,
                          by("productId", productId),
                          by("countToCheckout", countToCheckout),
                          by("countAfterCheckout", currentCount));

    }

    public void checkout(List<ItemWithCount> items) {
        for (ItemWithCount item : items) {
            checkout(item);
        }
    }

    private void onOrder(EventSource source) {
        List<ItemWithCount> items = (List<ItemWithCount>) source.get("items");
        checkout(items);
    }
}
