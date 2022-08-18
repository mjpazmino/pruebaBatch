package ec.edu.espe.pruebabatch.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Estudiante implements Serializable {
  private String cedula;
  private String apellidos;
  private String nombres;
  private Integer nivel;
}
