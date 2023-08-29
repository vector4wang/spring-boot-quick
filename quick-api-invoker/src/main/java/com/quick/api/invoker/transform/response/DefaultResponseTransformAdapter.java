package com.quick.api.invoker.transform.response;

import com.quick.api.invoker.transform.AbstractTransformAdapter;
import org.springframework.stereotype.Component;

@Component
public class DefaultResponseTransformAdapter extends AbstractTransformAdapter {
    @Override
    protected String conversion(String source) {
        return null;
    }
}
