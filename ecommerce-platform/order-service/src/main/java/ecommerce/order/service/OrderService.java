package ecommerce.order.service;

import ecommerce.basket.service.BasketService;
import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.service.StockService;

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

        List<ItemWithCount> items = order.getOrderItems().stream().map(OrderItem::getItem).collect(Collectors.toList());

        basketService.clear(order.getBuyerId());
        stockService.checkout(items);

        String itemsStr = items.stream().map(i->i.toString()).collect(Collectors.joining(","));
        System.out.println(String.format("ORDER EXECUTED : {\"buyerId\":%d, \"basketId\":%d, \"items\":[%s]}",
                          order.getBuyerId(),
                          order.getBasketId(),
                                         itemsStr));
    }
}
