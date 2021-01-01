package ecommerce.basket;

import ecommerce.basket.service.BasketService;
import ecommerce.basket.service.EventProducerService;
import ecommerce.shared.event.ECommerceEventType;
import jeventbus.service.EventBuilder;
import jeventbus.kafka.consumer.KafkaEventConsumer;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.service.EventService;

public class Application {

    public static void main(String[] args) {
        KafkaEventProducer producer = new KafkaEventProducer("localhost:9092");
        producer.connect();
        EventProducerService eventProducerService = new EventProducerService(producer);
        BasketService basketService = new BasketService(eventProducerService);

        Thread daemonThread = new Thread(()->{
            KafkaEventConsumer consumer = new KafkaEventConsumer("localhost:9092","basket");
            consumer.consume();
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        Long buyerId = 1001l;

        try {
            Thread.sleep(20*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ADDING ITEMS");
        basketService.addItem(buyerId, 1l, 500D, 5);
        basketService.addItem(buyerId, 2l, 200D, 3);
        basketService.addItem(buyerId, 3l, 200D, 1);
    }

}
