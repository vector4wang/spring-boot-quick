package com.quick.api.invoker.model;

import lombok.Data;

@Data
public class AdapterClassModel {
    private int id;
    private String className;

    private String classFqn;

    private String classContent;

    /**
     * 1: executor,2: transform-request,3: transform-response
     */
    private int adapterType;

    /**
     * 1: 代码内部加载，2: 实时加载
     * 为2时，classContent肯定有值
     */
    private int loadType = 1;
}
