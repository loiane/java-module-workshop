package ecommerce.logger;

import ecommerce.api.LoggerFactory;
import ecommerce.shared.event.ECommerceEventType;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jeventbus.kafka.consumer.KafkaEventConsumer;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;

@QuarkusMain
public class Application {

    public static void main(final String[] array) {
        Quarkus.run(array);
    }

}
