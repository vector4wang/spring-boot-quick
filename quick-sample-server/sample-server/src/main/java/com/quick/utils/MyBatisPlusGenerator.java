package com.quick.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusGenerator extends BaseGenerator {

    public static final String OUT_DIR = "D:\\github\\spring-boot-quick\\quick-archetype\\src\\main\\java";


    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) throws IOException {
        FastAutoGenerator.create(dataSourceGenerate())
                // 全局配置
                .globalConfig((scanner, builder) -> {
                    builder.author(scanner.apply("请输入作者")).fileOverride();
                    builder.outputDir(OUT_DIR);
                })
                // 包配置
                .packageConfig((scanner, builder) -> {
                    builder
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "/src/main/resources/mapper"))
                            .parent(scanner.apply("请输入包名？"));
                })
                // 策略配置
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(MyBatisPlusGenerator.getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
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
                            .logicDeleteColumnName("is_delete")
                            .columnNaming(NamingStrategy.underline_to_camel)
//                            .idType(IdType.AUTO)
                            .formatFileName("%sEntity")
                            .build();

                    // mapper xml配置
                    builder.mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build();
                })
                .execute();
    }
}
