package ecommerce.basket.service;

import ecommerce.shared.event.*;
import jeventbus.kafka.producer.KafkaEventProducer;
import jeventbus.shared.EventSource;
import jeventbus.shared.Parameter;
import jeventbus.streaming.EventDefinition;
import jeventbus.streaming.EventMessage;
import jeventbus.streaming.EventSourceKeys;
import jeventbus.streaming.EventToMessageConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static jeventbus.shared.Parameter.by;

@ApplicationScoped
public class EventProducerService {

    private final KafkaEventProducer producer;

    @Inject
    public EventProducerService(KafkaEventProducer producer) {
        this.producer = producer;
    }

    public void fireBasketCreated(Long buyerId, Long basketId) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.BASKET_CREATED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.BASKET_CREATED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.BUY_SOMETHING),
                by("buyerId", buyerId),
                by("basketId", basketId));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.BASKET_CREATED, source, definition);
        producer.produce("events", message);

    }

    public void fireBasketCleared(Long buyerId, Long basketId, Double totalPrice) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.BASKET_CLEARED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.BASKET_CLEARED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.FINDING_CHEAPER_OTHER_PLATFORM),
                by("buyerId", buyerId),
                by("basketId", basketId),
                by("totalPrice", totalPrice));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.BASKET_CLEARED, source, definition);
        producer.produce("events",message);
    }

    public void fireItemAddedToBasket(Long buyerId, Long basketId, Double unitPrice, Integer count, Long productId) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.BASKET_ITEM_ADDED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.BASKET_ITEM_ADDED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.I_LIKED_IT_TOO_MUCH),
                Parameter.by("buyerId", buyerId),
                Parameter.by("basketId", basketId),
                Parameter.by("unitPrice", unitPrice),
                Parameter.by("count", count),
                Parameter.by("productId", productId));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.BASKET_ITEM_ADDED, source, definition);
        producer.produce("events",message);
    }

    public void fireItemDecreasedToBasket(Long buyerId, Long basketId, Long productId, Integer count, Integer latestCount) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.BASKET_ITEM_COUNT_DECREASED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.BASKET_ITEM_COUNT_DECREASED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.I_LIKED_IT_TOO_MUCH),
                Parameter.by("buyerId", buyerId),
                Parameter.by("basketId", basketId),
                Parameter.by("productId", productId),
                Parameter.by("count", count),
                Parameter.by("latestCount",latestCount));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.BASKET_ITEM_COUNT_DECREASED, source, definition);
        producer.produce("events",message);
    }

    public void fireItemRemoved(Long buyerId, Long basketId, Long productId) {
        EventDefinition definition = EcommerceEventDefinition.of(ECommerceEventType.BASKET_ITEM_REMOVED).orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.BASKET_ITEM_REMOVED,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, ECommerceEventReason.I_LIKED_IT_TOO_MUCH),
                Parameter.by("buyerId", buyerId),
                Parameter.by("basketId", basketId),
                Parameter.by("productId", productId));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.BASKET_ITEM_REMOVED, source, definition);
        producer.produce("events",message);
    }
}
