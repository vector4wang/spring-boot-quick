package com.quick.mulit.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/8/22
 * Time: 18:05
 * Description:
 */
@Configuration
@MapperScan(basePackages = "com.quick.mulit.mapper.secondary", sqlSessionTemplateRef  = "secondarySqlSessionTemplate")
public class DataSourceSecondaryConfig {

    @Autowired
    private DruidConfigSecondaryProperties druidConfigSecondaryProperties;

    @Bean(name = "secondaryDataSource",initMethod = "init", destroyMethod = "close")
    public DataSource testDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidConfigSecondaryProperties.getDriverClassName());
        druidDataSource.setUrl(druidConfigSecondaryProperties.getUrl());
        druidDataSource.setUsername(druidConfigSecondaryProperties.getUsername());
        druidDataSource.setPassword(druidConfigSecondaryProperties.getPassword());
        druidDataSource.setInitialSize(druidConfigSecondaryProperties.getMinIdle());
        druidDataSource.setMinIdle(druidConfigSecondaryProperties.getMinIdle());
        druidDataSource.setMaxActive(druidConfigSecondaryProperties.getMaxActive());
        druidDataSource.setMaxWait(druidConfigSecondaryProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidConfigSecondaryProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidConfigSecondaryProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidConfigSecondaryProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidConfigSecondaryProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidConfigSecondaryProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(druidConfigSecondaryProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidConfigSecondaryProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidConfigSecondaryProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setFilters(druidConfigSecondaryProperties.getFilters());
        return druidDataSource;
    }

    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/secondary/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "secondaryTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
