import logger.feed.FeedLogger;
import logger.service.LoggerService;

module feed.logger {
    requires logger.service;
    provides LoggerService with FeedLogger;
}