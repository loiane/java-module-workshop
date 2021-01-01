package ecommerce.order.service;

import ecommerce.shared.event.ECommerceActorType;
import ecommerce.shared.event.ECommerceEventChannel;
import ecommerce.shared.event.ECommerceEventType;
import ecommerce.shared.event.EcommerceEventDefinition;
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

import static jeventbus.shared.Parameter.by;

@ApplicationScoped
public class EventProducerService {

    private final KafkaEventProducer producer;

    public EventProducerService(KafkaEventProducer producer) {
        this.producer = producer;
    }

    public void fireOrder(Long buyerId, Long basketId,  List<ItemWithCount> items) {
        EventDefinition definition = EcommerceEventDefinition
                                        .of(ECommerceEventType.ORDER)
                                        .orElseThrow(()->new IllegalStateException());
        EventSource source = EventSource.aNew(ECommerceEventType.ORDER,
                by(EventSourceKeys.EVENT_CHANNEL, ECommerceEventChannel.STANDALONE_APP),
                by(EventSourceKeys.ACTOR_ID, buyerId),
                by(EventSourceKeys.ACTOR_TYPE, ECommerceActorType.BUYER),
                by(EventSourceKeys.EVENT_REASON, null),
                Parameter.by("buyerId", buyerId),
                Parameter.by("basketId", basketId),
                Parameter.by("items", items));
        EventMessage message = EventToMessageConverter.convert(ECommerceEventType.ORDER, source, definition);
        producer.produce("events", message);
    }
}
