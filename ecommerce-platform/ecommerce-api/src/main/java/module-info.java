module ecommerce.api {
    requires basket.service;
    requires catalog.service;
    requires ecommerce.shared;
    requires order.service;
    requires stock.service;
    requires jeventbus.core;
    opens ecommerce.api to jeventbus.core;
    requires logger.service;
    uses logger.service.LoggerService;
}