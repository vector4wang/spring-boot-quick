package com.quick.api.invoker.transform;

import com.quick.api.invoker.Adapter;

public abstract class AbstractTransformAdapter implements Adapter {
   protected abstract String conversion(String source);
}
