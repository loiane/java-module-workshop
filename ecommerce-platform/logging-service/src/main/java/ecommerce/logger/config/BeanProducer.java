package ecommerce.logger.config;

import ecommerce.api.LoggerFactory;
import jeventbus.kafka.consumer.KafkaEventConsumer;
import jeventbus.kafka.producer.KafkaEventProducer;
import logger.service.LoggerService;

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

    @Produces
    @ApplicationScoped
    public LoggerService getLoggerService() {
        System.out.println("DEBUG : Getting logger service for console");
       try {
           var service = LoggerFactory.getInstance().get("console");
           System.out.println("DEBUG :Service has been created : "+ service);
           return service;
       } catch(Exception ex){
           ex.printStackTrace();
           throw ex;
       }
    }

    @Produces
    @ApplicationScoped
    public KafkaEventConsumer getKafkaEventConsumer() {
        return new KafkaEventConsumer("localhost:9092", "logger");
    }

}
