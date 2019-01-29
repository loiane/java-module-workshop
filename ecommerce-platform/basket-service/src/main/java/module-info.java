module ecommerce.basket {
    exports ecommerce.basket.service;
    exports ecommerce.basket.core to ecommerce.api;

    requires jeventbus;
    requires ecommerce.shared;
}