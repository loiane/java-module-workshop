package ecommerce.api;

import ecommerce.api.model.Basket;
import ecommerce.api.model.BasketItem;
import ecommerce.api.model.Order;
import ecommerce.api.model.OrderItem;
import ecommerce.api.model.ItemWithCount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BasketOrderConverter {

    public static OrderItem convert(BasketItem basketItem) {
        OrderItem orderItem = new OrderItem(basketItem.getProduct(), basketItem.getCount());
        return orderItem;
    }

    public static Optional<Order> convert(Optional<Basket> basket) {
        Order order = basket.map(b->convert(b)).orElse(null);
        return Optional.ofNullable(order);
    }

    private static Order convert(Basket basket) {
        Order order = new Order();
        order.setBuyerId(basket.getBuyerId());
        order.setBasketId(basket.getId());
        List<OrderItem> orderItems = basket.getItems().values().stream().map(bi->convert(bi)).collect(Collectors.toList());
        order.setItems(orderItems);
        return order;
    }

}
