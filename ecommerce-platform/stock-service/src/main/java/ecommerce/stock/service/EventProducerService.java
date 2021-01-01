package ecommerce.stock.service;

import ecommerce.shared.event.*;
import ecommerce.shared.model.ItemWithCount;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;
import jeventbus.streaming.EventDefinition;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventSourceKeys;
import jeventbus.streaming.EventToMessageConverter;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static ecommerce.shared.event.ECommerceEventType.STOCK_ADDED;
import static ecommerce.shared.event.ECommerceEventType.STOCK_CHECKOUTED;
import static jeventbus.shared.Parameter.by;

@ApplicationScoped
public class EventProducerService {

    private final KafkaEventProducer producer;

    public EventProducerService(KafkaEventProducer producer) {
        this.producer = producer;
    }

    public void fireStockAdded(Long productId, Integer count) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.STOCK_ADDED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.STOCK_ADDED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, 1l),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.SELLER),
                by(EventSourceKeys.EVENT_REASON, null),
                Parameter.by("productId", productId),
                Parameter.by("count", count));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.STOCK_ADDED, source, definition);
        producer.produce("events", message);
    }

    public void fireStockCheckouted(Long productId, Integer countToCheckout, Integer currentCount) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.STOCK_CHECKOUTED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.STOCK_CHECKOUTED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, null),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.SYSTEM),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.ORDER),
                Parameter.by("productId", productId),
                Parameter.by("countToCheckout", countToCheckout),
                Parameter.by("countAfterCheckout", currentCount));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.STOCK_CHECKOUTED, source, definition);
        producer.produce("events", message);
    }
}
