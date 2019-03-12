package ecommerce.api.service;

import ecommerce.api.model.ItemWithCount;
import ecommerce.api.model.Order;
import ecommerce.api.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    private final StockService stockService;
    private final BasketService basketService;

    public OrderService(StockService stockService, BasketService basketService) {
        this.stockService = stockService;
        this.basketService = basketService;
    }

    public void order(Order order) {

        List<OrderItem> items = order.getOrderItems();

        basketService.clear(order.getBuyerId());
        stockService.checkout(items);

        String itemsStr = items.stream().map(i->i.toString()).collect(Collectors.joining(","));
        System.out.println(String.format("ORDER EXECUTED : {\"buyerId\":%d, \"basketId\":%d, \"items\":[%s]}",
                          order.getBuyerId(),
                          order.getBasketId(),
                                         itemsStr));
    }
}
