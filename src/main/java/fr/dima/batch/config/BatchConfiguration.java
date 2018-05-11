package fr.dima.batch.config;

import javax.sql.DataSource;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import fr.dima.batch.listener.JobCompletionNotificationListener;
import fr.dima.batch.model.Personne;
import fr.dima.batch.processor.PersonItemProcessor;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
  
    @Bean
    public FlatFileItemReader<Personne> reader() {
        FlatFileItemReader<Personne> reader = new FlatFileItemReader<Personne>();
        reader.setResource(new ClassPathResource("fichierAIntegrer.csv"));
        reader.setLineMapper(new DefaultLineMapper<Personne>() {{
            setLineTokenizer(new DelimitedLineTokenizer(";") {{
                setNames(new String[] { "prenom", "nom","email","societe","tel" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Personne>() {{
                setTargetType(Personne.class);
            }});
        }});
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Personne> writer() {
        JdbcBatchItemWriter<Personne> writer = new JdbcBatchItemWriter<Personne>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Personne>());
        writer.setSql("INSERT INTO contact (prenom, nom,email,societe,tel) VALUES (:prenom, :nom,:societe,:email,:tel)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Personne, Personne> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}