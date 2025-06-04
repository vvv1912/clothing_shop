package com.shop.clothing.common.Cqrs;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

public interface IRequestHandler<TRequest, TResponse> {
    HandleResponse<TResponse> handle(TRequest request) throws Exception;

}