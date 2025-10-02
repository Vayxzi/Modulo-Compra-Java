package ModuloCompras.ModuloCompras.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecepcionProductoDto {
    private Integer id;
    private LocalDate fechaRecepcion;
    private Integer cantidadRecibida;
    private String observacion;
    private Integer detalleId;

    private Long productoId;      // 👈 nuevo
    private String productoNombre; // 👈 opcional, puedes llenarlo con Feign si lo necesitas
}


