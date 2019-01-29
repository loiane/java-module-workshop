package logger.service;

import logger.core.LogMessage;

public interface LoggerService {

    String getServiceName();

    void log(LogMessage message);
}
