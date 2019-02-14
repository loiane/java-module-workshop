package logger.console;

import logger.shared.LogMessage;
import logger.service.LoggerService;

public class ConsoleLogger implements LoggerService {

    @Override
    public String getServiceName() {
        return "console";
    }

    @Override
    public void log(LogMessage message) {
        System.out.println("CONSOLE :: "+ message.getMessage());
    }
}
