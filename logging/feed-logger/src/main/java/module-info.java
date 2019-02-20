module feed.logger {
    requires logger.service;
    provides logger.service.LoggerService with logger.feed.FeedLogger;
}