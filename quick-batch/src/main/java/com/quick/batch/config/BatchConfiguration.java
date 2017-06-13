package com.quick.batch.config;

import com.quick.batch.model.RecordSO;
import com.quick.batch.model.WriterSO;
import com.quick.batch.processor.RecordProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    @Bean
    public ItemReader<RecordSO> reader(DataSource dataSource) {
        JdbcCursorItemReader<RecordSO> reader = new JdbcCursorItemReader<>();
        reader.setSql("select id, firstName, lastname, random_num from reader");
        reader.setDataSource(dataSource);
        reader.setRowMapper(
                (ResultSet resultSet, int rowNum) -> {
                    if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
                        RecordSO recordSO = new RecordSO();
                        recordSO.setFirstName(resultSet.getString("firstName"));
                        recordSO.setLastName(resultSet.getString("lastname"));
                        recordSO.setId(resultSet.getInt("Id"));
                        recordSO.setRandomNum(resultSet.getString("random_num"));

                        LOGGER.info("RowMapper record : {}", recordSO);
                        return recordSO;
                    } else {
                        LOGGER.info("Returning null from rowMapper");
                        return null;
                    }
                });
        return reader;
    }

    @Bean
    public ItemProcessor<RecordSO, WriterSO> processor() {
        return new RecordProcessor();
    }

    @Bean
    public ItemWriter<WriterSO> writer(DataSource dataSource, ItemPreparedStatementSetter<WriterSO> setter) {
        JdbcBatchItemWriter<WriterSO> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setItemPreparedStatementSetter(setter);
        writer.setSql("insert into writer (id, full_name, random_num) values (?,?,?)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemPreparedStatementSetter<WriterSO> setter() {
        return (item, ps) -> {
            ps.setLong(1, item.getId());
            ps.setString(2, item.getFullName());
            ps.setString(3, item.getRandomNum());
        };
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<RecordSO> reader,
                      ItemWriter<WriterSO> writer, ItemProcessor<RecordSO, WriterSO> processor) {
        return stepBuilderFactory.get("step1")
                .<RecordSO, WriterSO>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}