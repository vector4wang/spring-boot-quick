package com.quick.mulit.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/8/22
 * Time: 17:50
 * Description:
 */
@Configuration
@MapperScan(basePackages = "com.quick.mulit.mapper.primary", sqlSessionTemplateRef  = "primarySqlSessionTemplate")
public class DataSourcePrimaryConfig {

    @Autowired
    private DruidConfigPrimaryProperties druidConfigPrimaryProperties;

    @Bean(name = "primaryDataSource",initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource testDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidConfigPrimaryProperties.getDriverClassName());
        druidDataSource.setUrl(druidConfigPrimaryProperties.getUrl());
        druidDataSource.setUsername(druidConfigPrimaryProperties.getUsername());
        druidDataSource.setPassword(druidConfigPrimaryProperties.getPassword());
        druidDataSource.setInitialSize(druidConfigPrimaryProperties.getMinIdle());
        druidDataSource.setMinIdle(druidConfigPrimaryProperties.getMinIdle());
        druidDataSource.setMaxActive(druidConfigPrimaryProperties.getMaxActive());
        druidDataSource.setMaxWait(druidConfigPrimaryProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigPrimaryProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidConfigPrimaryProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidConfigPrimaryProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidConfigPrimaryProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidConfigPrimaryProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(druidConfigPrimaryProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidConfigPrimaryProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigPrimaryProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setFilters(druidConfigPrimaryProperties.getFilters());
        return druidDataSource;
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/primary/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
