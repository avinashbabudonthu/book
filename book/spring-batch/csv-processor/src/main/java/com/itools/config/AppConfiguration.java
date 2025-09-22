package com.itools.config;

import com.itools.component.JobCompletionNotificationListener;
import com.itools.component.PersonItemProcessor;
import com.itools.record.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    /**
     * creates an ItemReader. It looks for a file called sample-data.csv and
     * parses each line item with enough information to turn it into a Person
     */
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("person-data-reader")
                .resource(new ClassPathResource("person-data.csv"))
                .delimited()
                .names("firstName", "lastName")
                .targetType(Person.class)
                .build();
    }

    /**
     * creates an instance of the PersonItemProcessor that you defined earlier, meant to convert the data to upper case
     */
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /**
     * creates an ItemWriter. This one is aimed at a JDBC destination and automatically gets a DataSource created by Spring Boot.
     * It includes the SQL statement needed to insert a single Person, driven by Java record components
     */
    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .sql("INSERT INTO person(first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Step csvImportStep(JobRepository jobRepository, DataSourceTransactionManager dataSourceTransactionManager,
                              FlatFileItemReader<Person> reader,
                              PersonItemProcessor processor,
                              JdbcBatchItemWriter<Person> writer){
        return new StepBuilder("csv-import", jobRepository)
                .<Person, Person>chunk(3, dataSourceTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job csvImport(JobRepository jobRepository, Step step, JobCompletionNotificationListener listener, JobRegistry jobRegistry) {
        return new JobBuilder("csv-import-job", jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }
}