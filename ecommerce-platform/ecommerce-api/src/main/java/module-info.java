module ecommerce.api {
    requires basket.service;
    requires catalog.service;
    requires ecommerce.shared;
    requires order.service;
    requires stock.service;
    requires jeventbus.core;
    requires logger.service;
    opens ecommerce.api to jeventbus.core;
    uses logger.service.LoggerService;
}