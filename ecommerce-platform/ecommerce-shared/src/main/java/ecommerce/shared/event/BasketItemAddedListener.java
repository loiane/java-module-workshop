package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface BasketItemAddedListener extends EventListener {
    void onBasketItemAdded(EventSource eventSource);
}
