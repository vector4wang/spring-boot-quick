package com.quick.api.invoker.executor;

import com.quick.api.invoker.enums.HttpMethod;
import com.quick.api.invoker.executor.bo.ExecutorConfig;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OkhttpExecutorAdapter extends AbstractExecutorAdapter{


    @Override
    public String execute(String url, HttpMethod method, Map<String, Object> querys, ExecutorConfig config) {
        return null;
    }
}
