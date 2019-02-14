package logger.feed;

import logger.shared.LogMessage;
import logger.service.LoggerService;

public class FeedLogger implements LoggerService {

    @Override
    public String getServiceName() {
        return "feed";
    }

    @Override
    public void log(LogMessage message) {
        System.out.println("FEED :: "+ message.getMessage());
    }
}
