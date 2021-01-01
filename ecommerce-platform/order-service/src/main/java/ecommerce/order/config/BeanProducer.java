package ecommerce.order.config;

import jeventbus.kafka.producer.KafkaEventProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class BeanProducer {

    @Produces
    @ApplicationScoped
    public KafkaEventProducer kafkaEventProducer() {
        KafkaEventProducer producer = new KafkaEventProducer("localhost:9092");
        producer.connect();
        return producer;
    }

}
