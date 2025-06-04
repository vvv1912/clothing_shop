package com.shop.clothing.common.Cqrs.Impl;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequest;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.common.Cqrs.ISender;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Component

class SenderImpl implements ISender {

    Map<Class, Class> handlers = new HashMap<>();
    private final ConfigurableApplicationContext context;
    @Autowired
    public SenderImpl(ConfigurableApplicationContext context) {
        this.context = context;
        Reflections reflections = new Reflections("com.shop.clothing");
        Set<Class<? extends IRequestHandler>> classes = reflections.getSubTypesOf(IRequestHandler.class);
        for (Class<? extends IRequestHandler> clazz : classes) {
            Type[] interfaces = clazz.getGenericInterfaces();
            if(interfaces.length > 0 && interfaces[0] instanceof ParameterizedType) {
                Type requestType = ((ParameterizedType)interfaces[0]).getActualTypeArguments()[0];
                handlers.put((Class<?>)requestType, clazz);
            }
        }
    }

    public IRequestHandler getHandler(Class<?> clazz) {

        Class handler = handlers.get(clazz);
        if (handler == null) {
            return null;
        }
        return (IRequestHandler) context.getBean(handler);
    }

    @Override
    public <TResponse> HandleResponse<TResponse> send(IRequest<TResponse> request) {
        var handler = getHandler(request.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler found for " + request.getClass().getName());
        }
        try {
            return  (HandleResponse<TResponse>) handler.handle(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
 