package com.quick.api.invoker.executor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.quick.api.invoker.enums.HttpMethod;
import com.quick.api.invoker.executor.bo.ExecutorConfig;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class HttpExecutorAdapter extends AbstractExecutorAdapter {


    private void addConfig(HttpRequest request, ExecutorConfig config) {
        if (Objects.isNull(config)) {
            return;
        }
        if (MapUtil.isNotEmpty(config.getHeader())) {
            request.headerMap(config.getHeader(), true);
        }

        if (Objects.nonNull(config.getProxy())) {
            request.setProxy(config.getProxy());
        }
        request.timeout(config.getTimeout());

    }


    @Override
    public String execute(String url, HttpMethod method, Map<String, Object> params, ExecutorConfig config) {
        HttpRequest request = HttpUtil.createRequest(Method.valueOf(method.name()), url);

        if (MapUtil.isNotEmpty(params)) {
            if (HttpMethod.GET.equals(method)) {
                request.form(params);
            } else if (HttpMethod.POST.equals(method)) {

                request.body(JSONUtil.toJsonStr(params));
            }
        }

        addConfig(request, config);

        HttpResponse httpResponse = request.execute();
        return httpResponse.body();
    }
}
