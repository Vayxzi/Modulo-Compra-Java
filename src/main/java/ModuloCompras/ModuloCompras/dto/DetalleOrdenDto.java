package ModuloCompras.ModuloCompras.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenDto {
    private Integer id;
    private int cantidad;
    private Double precioUnitario;
    private Integer productoId;
    private String productoNombre;
    private Integer ordenId;
}

