package ec.edu.espe.pruebabatch.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import ec.edu.espe.pruebabatch.model.Estudiante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class FileUtils {
  private final String fileName;
  private com.opencsv.CSVReader CSVReader;
  private com.opencsv.CSVWriter CSVWriter;
  private FileReader fileReader;
  private FileWriter fileWriter;
  private File file;

  public Estudiante readLine() {
    try {
      if (CSVReader == null) initReader();
      String[] line = CSVReader.readNext();

      if (line == null) return null;
      line = Arrays.stream(line).map(this::removeBOM).toArray(String[]::new);

      return Estudiante.builder()
          .cedula(line[0])
          .apellidos(line[1])
          .nombres(line[2])
          .nivel(Integer.parseInt(line[3]))
          .build();
    } catch (Exception e) {
      log.error("Error al leer el archivo");
      return null;
    }
  }

  private String removeBOM(String line) {
    boolean hasBOM = line.startsWith("\uFEFF");
    return hasBOM ? line.substring(1) : line;
  }

  public void writeLine(Estudiante estudiante) {
    try {
      if (CSVWriter == null) initWriter();
      String[] lineStr = new String[4];
      lineStr[0] = estudiante.getCedula();
      lineStr[1] = estudiante.getApellidos();
      lineStr[2] = estudiante.getNombres();
      lineStr[3] = estudiante.getNivel().toString();

      CSVWriter.writeNext(lineStr);
    } catch (Exception e) {
      log.error("Error al escribir en el archivo");
    }
  }

  private void initReader() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    if (file == null)
      file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
    if (fileReader == null) fileReader = new FileReader(file);
    if (CSVReader == null) CSVReader = new CSVReader(fileReader);
  }

  private void initWriter() throws Exception {
    if (file == null) {
      file = new File(fileName);
      file.createNewFile();
    }
    if (fileWriter == null) fileWriter = new FileWriter(file, true);
    if (CSVWriter == null) CSVWriter = new CSVWriter(fileWriter);
  }

  public void closeWriter() {
    try {
      CSVWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      log.error("Error al cerrar la escritura del archivo.");
    }
  }

  public void closeReader() {
    try {
      CSVReader.close();
      fileReader.close();
    } catch (IOException e) {
      log.error("Error al cerrar la lectura del archivo.");
    }
  }
}
