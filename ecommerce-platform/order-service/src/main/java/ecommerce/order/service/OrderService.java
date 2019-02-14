package ecommerce.order.service;

import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.shared.event.ECommerceEventType;
import ecommerce.shared.model.ItemWithCount;

import java.util.List;
import java.util.stream.Collectors;

import jeventbus.service.EventService;
import jeventbus.shared.Parameter;

public class OrderService {

    private final EventService eventService;

    public OrderService(EventService eventService) {

        this.eventService = eventService;
    }

    public void order(Order order) {

        List<ItemWithCount> items = order.getOrderItems().stream().map(OrderItem::getItem).collect(Collectors.toList());

        eventService.fire(ECommerceEventType.ORDER,
                          Parameter.by("buyerId", order.getBuyerId()),
                          Parameter.by("basketId", order.getBasketId()),
                          Parameter.by("items", order.getOrderItems().stream().map(OrderItem::getItem).collect(Collectors.toList())));

    }
}
