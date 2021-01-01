package ecommerce.catalog.service;

import ecommerce.shared.event.*;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;
import jeventbus.streaming.EventDefinition;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventSourceKeys;
import jeventbus.streaming.EventToMessageConverter;

import javax.enterprise.context.ApplicationScoped;

import static jeventbus.shared.Parameter.by;

@ApplicationScoped
public class EventProducerService {

    private final KafkaEventProducer producer;

    public EventProducerService(KafkaEventProducer producer) {
        this.producer = producer;
    }

    public void fireProductDeleted(Long productId) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.PRODUCT_DELETED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.PRODUCT_DELETED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, 1L),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.SELLER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.SOLD_OUT),
                Parameter.by("productId", productId));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.PRODUCT_DELETED, source, definition);
        producer.produce("events", message);
    }

    public void fireProductAdded(Long productId, String productName) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.PRODUCT_ADDED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.PRODUCT_ADDED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, 1L),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.SELLER),
                by(EventSourceKeys.EVENT_REASON, null),
                by("productId", productId),
                by("productName", productName));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.PRODUCT_ADDED, source, definition);
        producer.produce("events", message);
    }
}
