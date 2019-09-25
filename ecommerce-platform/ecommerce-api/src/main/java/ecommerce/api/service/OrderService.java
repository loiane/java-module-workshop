package ecommerce.api.service;

import ecommerce.api.model.Order;
import ecommerce.api.model.OrderItem;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class OrderService {

    private static final String MESSAGE = "ORDER EXECUTED : "
                                        + "{\"buyerId\":%d,"
                                        + " \"basketId\":%d,"
                                        + " \"items\":[%s]}";

    private final StockService stockService;
    private final BasketService basketService;

    public OrderService(StockService stockService, BasketService basketService) {
        this.stockService = stockService;
        this.basketService = basketService;
    }

    public void order(Order order) {

        List<OrderItem> items = order.getOrderItems();

        // IMPORTANT PART IS HERE
        basketService.clear(order.getBuyerId());
        stockService.checkout(items);
        // -----------------------

        String itemsStr = items.stream()
                               .map(i->i.toString())
                               .collect(joining(","));

        System.out.println(format(MESSAGE,
                                  order.getBuyerId(),
                                  order.getBasketId(),
                                  itemsStr));
    }
}
