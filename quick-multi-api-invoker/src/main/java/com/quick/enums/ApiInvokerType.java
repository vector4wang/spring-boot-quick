package com.quick.enums;

public enum ApiInvokerType {

    SCENE_A("SCENE_A"),
    SCENE_B("SCENE_B"),
    SCENE_C("SCENE_C"),
    SCENE_D("SCENE_D");


    ApiInvokerType(String type) {
        this.type = type;
    }
    public static ApiInvokerType getTypeEnum(String code){
        ApiInvokerType[] values = ApiInvokerType.values();
        for (ApiInvokerType typeEnum :values){
            if(typeEnum.type.equals(code)){
                return typeEnum;
            }
        }
        return null;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
