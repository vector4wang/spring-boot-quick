package com.quick.api.invoker.transform.request;

import com.quick.api.invoker.transform.AbstractTransformAdapter;
import org.springframework.stereotype.Component;

@Component
public class DefaultRequestTransformAdapter extends AbstractTransformAdapter {

    @Override
    protected String conversion(String source) {
        return null;
    }
}
