package spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by achat1 on 9/22/15.
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Value("${spark.app.name}")
    private String appName;

    @Value("${spark.home}")
    private String sparkHome;

    @Value("${spark.master.uri}")
    private String masterUri;

    @Bean
    public SparkConf sparkConf() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:file/winutils.exe");
        System.out.println(appName);
        System.out.println(sparkHome);
        System.out.println(masterUri);
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setSparkHome(sparkHome)
                .setMaster(masterUri)
                .set("spark.testing.memory", "2147480000");

        return sparkConf;
    }

    @Bean
    public JavaSparkContext javaSparkContext() throws FileNotFoundException {
        return new JavaSparkContext(sparkConf());
    }
//
//    @Bean
//    public SparkSession sparkSession() {
//        return SparkSession
//                .builder()
//                .sparkContext(javaSparkContext().sc())
//                .appName("Java Spark SQL basic example")
//                .getOrCreate();
//    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}