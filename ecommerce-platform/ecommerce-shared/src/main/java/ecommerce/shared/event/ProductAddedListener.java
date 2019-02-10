package ecommerce.shared.event;

import jeventbus.shared.EventSource;
import jeventbus.shared.EventListener;

public interface ProductAddedListener extends EventListener {
    void onProductAdded(EventSource source);
}
