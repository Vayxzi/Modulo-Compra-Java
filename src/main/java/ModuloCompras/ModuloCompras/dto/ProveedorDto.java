package ModuloCompras.ModuloCompras.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDto {
    private Integer id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaRegistro;
}
