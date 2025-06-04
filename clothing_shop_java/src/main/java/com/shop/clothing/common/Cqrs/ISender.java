package com.shop.clothing.common.Cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

public interface ISender {

    <TResponse>  HandleResponse<TResponse> send(IRequest<TResponse> request);

}


