package ecommerce.stock;

import ecommerce.shared.event.ECommerceEventType;
import ecommerce.stock.service.EventProducerService;
import ecommerce.stock.service.StockService;
import jeventbus.kafka.consumer.KafkaEventConsumer;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;

public class Application {

    public static void main(String[] args) {

        KafkaEventProducer producer = new KafkaEventProducer("localhost:9092");
        producer.connect();
        EventProducerService eventProducerService = new EventProducerService(producer);
        StockService stockService = new StockService(eventProducerService);

        EventService jeventbusService = new EventService();
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.ORDER).add(stockService));

        Thread daemonThread = new Thread(()->{
            KafkaEventConsumer consumer = new KafkaEventConsumer("localhost:9092", "stock");
            consumer.consume();
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        try {
            Thread.sleep(10*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ADDING STOCK");
        stockService.add(1l, 100);
        stockService.add(2l, 100);
        stockService.add(3l, 100);
    }

}
