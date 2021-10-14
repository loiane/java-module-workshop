module basket.service {
    exports ecommerce.basket.service;
    exports ecommerce.basket.core to ecommerce.api;
    requires ecommerce.shared;
    requires jeventbus.core;
    opens ecommerce.basket.service to jeventbus.core;
}