package ec.edu.espe.pruebabatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class PruebaBatchApplication {
    private final JobLauncher launcher;
    @Autowired
    @Qualifier("processTextFileJob")
    Job processTextFileJob;
    public static void main(String[] args) {
        SpringApplication.run(PruebaBatchApplication.class, args);
    }

    @Scheduled(fixedDelay = 2_000, initialDelay = 1_000)
    public void performJob()
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        log.info("Procesando archivo");

        JobParameters params =
                new JobParametersBuilder()
                        .addString("ProcessTextFileJob", String.valueOf(System.currentTimeMillis()))
                        .toJobParameters();

        launcher.run(processTextFileJob, params);
    }
}
