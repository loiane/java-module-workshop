package ecommerce.catalog;

import ecommerce.catalog.service.CatalogService;
import ecommerce.catalog.service.EventProducerService;
import ecommerce.shared.model.Product;
import jeventbus.kafka.producer.KafkaEventProducer;

public class Application {

    public static void main(String[] args) {

        KafkaEventProducer producer = new KafkaEventProducer("localhost:9092");
        producer.connect();
        EventProducerService eventProducerService = new EventProducerService(producer);
        CatalogService catalogService = new CatalogService(eventProducerService);

        System.out.println("ADDING PRODUCTS");

        Product bossKulaklik = catalogService.add("Boss Kulaklık", 500D);
        Product appleKlavye = catalogService.add("Apple Klavye", 200D);
        Product dellMonitor = catalogService.add("Dell Monitor", 200D);
    }

}
