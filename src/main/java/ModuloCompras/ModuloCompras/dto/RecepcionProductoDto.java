package ModuloCompras.ModuloCompras.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecepcionProductoDto {
    private Integer id;
    private LocalDate fechaRecepcion;
    private int cantidadRecibida;
    private String observacion;
    private Integer detalleId;
    private Integer productoId;
    private String productoNombre;
}

