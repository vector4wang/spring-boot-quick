package com.quick.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusWithTemplateGenerator extends BaseGenerator {

    // TODO
    public static final String OUT_DIR = "D:\\develop\\code\\temp";


    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }


    public static void main(String[] args) throws IOException {
        FastAutoGenerator.create(dataSourceGenerate())
                // 全局配置
                .globalConfig((scanner, builder) -> {
                    builder.author(scanner.apply("请输入作者"))
                            .fileOverride()
                            .outputDir(OUT_DIR);

                })
                // 包配置
                .packageConfig((scanner, builder) -> {
                    builder
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/src/main/resources/mapper"))
                            .parent(scanner.apply("请输入包名？"));
                })
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(MyBatisPlusWithTemplateGenerator.getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .superClass("com.quick.common.base.rest.BaseController")
                            .build();

                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            .build();
                    //entity的策略配置
                    builder.entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .versionColumnName("version")
//                            .logicDeleteColumnName("is_delete")
                            .columnNaming(NamingStrategy.underline_to_camel)
//                            .idType(IdType.AUTO)
                            .formatFileName("%s")
                            .build();

                    // mapper xml配置
                    builder.mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .superClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build();
                })
                .templateEngine(new VelocityTemplateEngine())
                .templateConfig(builder -> {
                    builder.entity("/templates/entity.java.vm")
                            .service("/templates/service.java.vm")
                            .serviceImpl("/templates/serviceImpl.java.vm")
                            .controller("/templates/controller.java.vm");
                })
                //注入配置————自定义模板
//                .injectionConfig(builder -> builder
//                        .beforeOutputFile((tableInfo, objectMap) -> {
//                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
//                        }) //输出文件之前消费者
//                        .customMap(Collections.singletonMap("my_field", "自定义配置 Map 对象")) //自定义配置 Map 对象
//                        .customFile(Collections.singletonMap("query.java", "/templates/query.java.vm")) //自定义配置模板文件
//                        .build())//加入构建队列
                .execute();
    }
}
