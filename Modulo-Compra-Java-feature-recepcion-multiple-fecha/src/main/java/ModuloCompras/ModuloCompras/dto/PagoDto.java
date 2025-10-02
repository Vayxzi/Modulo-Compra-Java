package ModuloCompras.ModuloCompras.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDto {
    private Integer id;
    private LocalDate fechaPago;
    private Double monto;
    private String estado;
    private Integer ordenId;
}
