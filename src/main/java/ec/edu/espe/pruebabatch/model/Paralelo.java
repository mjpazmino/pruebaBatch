package ec.edu.espe.pruebabatch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Builder
@Document(collection = "paralelos")
@TypeAlias("paralelos")
public class Paralelo {

  @Id private String id;
  private Integer nivel;
  private Integer numero;

  @DocumentReference private List<Estudiante> estudiantesList;
}
