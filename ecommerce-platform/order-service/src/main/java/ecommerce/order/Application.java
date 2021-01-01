package ecommerce.order;

import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.order.service.EventProducerService;
import ecommerce.order.service.OrderService;
import ecommerce.shared.model.ItemWithCount;
import jeventbus.kafka.producer.KafkaEventProducer;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        KafkaEventProducer producer = new KafkaEventProducer("localhost:9092");
        producer.connect();
        EventProducerService eventProducerService = new EventProducerService(producer);
        OrderService orderService = new OrderService(eventProducerService);

        Long buyerId = 1001L;
        Long basketId = 1L;

        try {
            Thread.sleep(40*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ORDER");
        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setBasketId(basketId);
        ItemWithCount item1 = new ItemWithCount(1L,500D, 5);
        ItemWithCount item2 = new ItemWithCount(2L,200D, 3);
        ItemWithCount item3 = new ItemWithCount(3L,200D, 1);
        List<OrderItem> orderItems = List.of(new OrderItem(item1), new OrderItem(item2), new OrderItem(item3));
        order.setItems(orderItems);
        orderService.order(order);
    }

}
