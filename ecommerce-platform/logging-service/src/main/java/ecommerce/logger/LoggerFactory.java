package ecommerce.api;

import logger.service.LoggerService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class LoggerFactory {

    private static class SingletonHelper
    {
        public static final LoggerFactory instance = new LoggerFactory();
    }

    private static Map<String, LoggerService> serviceProviders = new HashMap<>();


    @SuppressWarnings("unchecked")
    private LoggerFactory()
    {
        ServiceLoader loader = ServiceLoader.load(LoggerService.class);
        Iterator<LoggerService> iterator = loader.iterator();
        while(iterator.hasNext())
        {
            LoggerService serviceImpl = iterator.next();
            serviceProviders.put(serviceImpl.getServiceName(), serviceImpl);
        }
    }

    public static LoggerFactory getInstance()
    {
        return SingletonHelper.instance;
    }

    public LoggerService get(String name)
    {
        return serviceProviders.get(name);
    }
}
