package ecommerce.order.service;

import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;

import javax.enterprise.context.ApplicationScoped;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class OrderService {

    private final EventProducerService eventService;

    public OrderService(EventProducerService eventService) {

        this.eventService = eventService;
    }

    public void order(Order order) {

        eventService.fireOrder(order.getBuyerId(),
                                order.getBasketId(),
                                order.getOrderItems()
                                        .stream()
                                        .map(OrderItem::getItem)
                                        .collect(toList()));
    }
}
