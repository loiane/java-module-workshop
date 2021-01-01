package ecommerce.stock.config;

import ecommerce.shared.event.ECommerceEventType;
import ecommerce.stock.service.StockService;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jeventbus.kafka.consumer.KafkaEventConsumer;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    StockService stockService;

    @Inject
    KafkaEventConsumer consumer;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Stock Service is starting...");

        Thread daemonThread = new Thread(()->{
            System.out.println("Kafka Consumer is starting...");
            consumer.consume("events");
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println("Registering listeners to events...");

        EventService jeventbusService = new EventService();
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.ORDER).add(stockService));
    }

    void onStop(@Observes ShutdownEvent ev) {               
        System.out.println("The application is stopping...");
    }

}