package fr.dima.batch.config;

import java.io.File;
import java.io.FilenameFilter;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

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
	@Autowired
	public ApplicationContext appContext;


	
	
	@Bean
	public JdbcBatchItemWriter<Personne> writer() {
		JdbcBatchItemWriter<Personne> writer = new JdbcBatchItemWriter<Personne>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Personne>());
		writer.setSql("INSERT INTO contact (email,nom, prenom,societe,tel) VALUES (:email, :nom, :prenom, :societe, :tel)");
		writer.setDataSource(dataSource);
		return writer;
		
		
	
	}
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public FlatFileItemReader<Personne> reader() {
		File file = new File(System.getProperty("user.home") + "/ERP_DIMA/");
		File[] files = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".csv")) {
					return true;
				} else {
					return false;
				}
			}
		});
		for (File f : files) {
			System.out.println(f.getPath());
		}
		System.out.println(files[0].getName());
		System.out.println("file:" + System.getProperty("user.home") + "/ERP_DIMA/" + files[0].getName());
		FlatFileItemReader<Personne> reader = new FlatFileItemReader<Personne>();
		appContext = new ClassPathXmlApplicationContext(new String[] {});
		Resource resource = appContext.getResource("file:" + System.getProperty("user.home") + "/ERP_DIMA/" + files[0].getName());
		reader.setResource(resource);
		reader.setLineMapper(new DefaultLineMapper<Personne>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer(";") {
					{
						setNames(new String[] {"email" ,"nom", "prenom", "societe", "tel" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Personne>() {
					{
						setTargetType(Personne.class);
					}
				});
			}
		});
		return reader;
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
		return stepBuilderFactory.get("step1").<Personne, Personne>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

}