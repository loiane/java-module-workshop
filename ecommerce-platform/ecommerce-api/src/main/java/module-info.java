module ecommerce.api {
    requires jeventbus;
    requires ecommerce.shared;
    requires ecommerce.basket;
    requires ecommerce.catalog;
    requires ecommerce.order;
    requires logger.service;

    uses logger.service.LoggerService;
}