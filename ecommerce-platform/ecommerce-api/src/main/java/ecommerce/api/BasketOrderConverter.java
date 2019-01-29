package ecommerce.api;

import ecommerce.basket.core.Basket;
import ecommerce.basket.core.BasketItem;
import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.shared.model.ItemWithCount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BasketOrderConverter {

    public static OrderItem convert(BasketItem basketItem) {
        ItemWithCount item = new ItemWithCount(basketItem.getProductId(), basketItem.getUnitPrice(), basketItem.getCount());
        OrderItem orderItem = new OrderItem(item);
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
