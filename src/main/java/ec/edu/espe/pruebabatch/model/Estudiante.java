package ec.edu.espe.pruebabatch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "estudiantes")
@TypeAlias("estudiantes")
public class Estudiante {

  @Id private String id;

  @Indexed(name = "idxu_estudiantes_cedula", unique = true)
  private String cedula;

  private String apellidos;
  private String nombres;
  private Integer nivel;
}
