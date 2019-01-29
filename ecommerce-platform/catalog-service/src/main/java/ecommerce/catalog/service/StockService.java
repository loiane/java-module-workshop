package ecommerce.catalog.service;

import ecommerce.catalog.core.Stock;
import ecommerce.catalog.exception.NotEnoughStockException;
import ecommerce.shared.event.OrderListener;
import ecommerce.shared.model.ItemWithCount;
import jeventbus.service.EventService;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;

import java.util.List;

import static ecommerce.shared.event.ECommerceEventType.STOCK_ADDED;
import static ecommerce.shared.event.ECommerceEventType.STOCK_CHECKOUTED;
import static jeventbus.shared.Parameter.by;

public class StockService implements OrderListener {

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
        stock.checkout(productId, countToCheckout);
        Integer currentCount = stock.getCount(productId);
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

    @Override
    public void onOrder(EventSource source) {
        List<ItemWithCount> items = (List<ItemWithCount>) source.get("items");
        checkout(items);
    }

}
