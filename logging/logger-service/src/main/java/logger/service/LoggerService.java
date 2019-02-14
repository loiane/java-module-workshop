package logger.service;

import logger.shared.LogMessage;

public interface LoggerService {

    String getServiceName();

    void log(LogMessage message);
}
