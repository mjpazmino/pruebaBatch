package ec.edu.espe.pruebabatch.config;

import ec.edu.espe.pruebabatch.process.ReadLineTask;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
  private final JobBuilderFactory jobs;
  private final StepBuilderFactory steps;

  @Bean
  protected Step readLines() {
    return steps.get("readLines").tasklet(new ReadLineTask()).build();
  }

  @Bean
  public Job processTextFileJob() {
    return jobs.get("processTextFileJob").start(readLines()).build();
  }
}
