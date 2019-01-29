package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface BasketItemCountDecreasedListener extends EventListener {
    void onBasketItemCountDecreased(EventSource eventSource);
}
