package ec.edu.espe.pruebabatch.process;

import ec.edu.espe.pruebabatch.model.Estudiante;
import ec.edu.espe.pruebabatch.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReadLineTask implements Tasklet, StepExecutionListener {

  private List<Estudiante> estudiantes;
  private FileUtils fileUtils;

  @Override
  public void beforeStep(StepExecution stepExecution) {
    estudiantes = new ArrayList<>();
    fileUtils = new FileUtils("estudiantes.csv");
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    Estudiante estudiante = fileUtils.readLine();
    while (estudiante  != null) {
      log.info("Estudiante ={}", estudiante);

      estudiantes.add(estudiante );
      estudiante = fileUtils.readLine();
    }
    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    fileUtils.closeReader();
    stepExecution.getJobExecution().getExecutionContext().put("estudiantes", estudiantes);

    log.info("Lectura correcta de estudiantes");

    return ExitStatus.COMPLETED;
  }
}
