package ecommerce.stock.service;

import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.core.Stock;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;

import java.util.List;

import static ecommerce.shared.event.ECommerceEventType.STOCK_ADDED;
import static ecommerce.shared.event.ECommerceEventType.STOCK_CHECKOUTED;
import static jeventbus.shared.Parameter.by;

public class StockService implements OrderListener  {

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

        eventService.fire(STOCK_CHECKOUTED,
                          by("productId", productId),
                          by("countToCheckout", countToCheckout),
                          by("countAfterCheckout", currentCount));

        System.out.println(
                String.format("PRODUCT CHECKOUTED from STOCK : {\"productId\":%d, \"countCheckouted\":%d, \"countAfterCheckout\":%d}",
                              productId, countToCheckout, stock.getCount(productId)));
    }

    public void checkout(List<ItemWithCount> items) {
        for (ItemWithCount item : items) {
            checkout(item);
        }
    }
}
