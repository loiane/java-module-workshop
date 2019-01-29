package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface OrderListener extends EventListener {
    void onOrder(EventSource source);
}
