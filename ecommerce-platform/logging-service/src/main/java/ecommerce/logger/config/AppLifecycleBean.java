package ecommerce.logger.config;

import ecommerce.logger.Logger;
import ecommerce.shared.event.ECommerceEventType;
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
    Logger logger;

    @Inject
    KafkaEventConsumer consumer;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Log Service is starting...");

        Thread daemonThread = new Thread(()->{
            System.out.println("Kafka Consumer is starting...");
            consumer.consume("events");
        });
        daemonThread.setDaemon(true);
        daemonThread.start();


        System.out.println("Registering listeners to events...");
        EventService jeventbusService = new EventService();
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.BASKET_CLEARED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.BASKET_CREATED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_ADDED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_REMOVED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_COUNT_DECREASED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.PRODUCT_ADDED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.PRODUCT_DELETED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.STOCK_ADDED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.STOCK_CHECKOUTED).add(logger));
        jeventbusService.register(EventBuilder.aNew(ECommerceEventType.ORDER).add(logger));
    }

    void onStop(@Observes ShutdownEvent ev) {               
        System.out.println("The application is stopping...");
    }

}