package com.quick.api.invoker.executor;

import com.quick.api.invoker.Adapter;
import com.quick.api.invoker.enums.HttpMethod;
import com.quick.api.invoker.executor.bo.ExecutorConfig;

import java.util.Map;

public abstract class AbstractExecutorAdapter implements Adapter {

    public abstract String execute(String url, HttpMethod method, Map<String, Object> params, ExecutorConfig config);

    public   void print() {
        System.out.println(this.getClass().getName());
    }
}
