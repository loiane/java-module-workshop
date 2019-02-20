module console.logger {
    requires logger.service;
    provides logger.service.LoggerService with logger.console.ConsoleLogger;
}