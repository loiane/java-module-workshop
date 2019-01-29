package ecommerce.order.service;

import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.shared.event.ECommerceEventType;
import jeventbus.service.EventService;
import jeventbus.shared.Parameter;

import java.util.stream.Collectors;

public class OrderService {

    private final EventService eventService;

    public OrderService(EventService eventService) {
        this.eventService = eventService;
    }

    public void order(Order order) {
        eventService.fire(ECommerceEventType.ORDER,
                          Parameter.by("buyerId", order.getBuyerId()),
                          Parameter.by("basketId", order.getBasketId()),
                          Parameter.by("items", order.getOrderItems().stream().map(OrderItem::getItem).collect(Collectors.toList())));
    }
}
