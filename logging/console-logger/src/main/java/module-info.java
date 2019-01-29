import logger.console.ConsoleLogger;
import logger.service.LoggerService;

import java.io.Console;

module console.logger {
    requires logger.service;

    provides LoggerService with ConsoleLogger;
}