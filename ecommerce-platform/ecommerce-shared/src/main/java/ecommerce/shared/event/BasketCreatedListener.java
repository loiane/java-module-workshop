package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface BasketCreatedListener extends EventListener {
    void onBasketCreated(EventSource eventSource);
}
