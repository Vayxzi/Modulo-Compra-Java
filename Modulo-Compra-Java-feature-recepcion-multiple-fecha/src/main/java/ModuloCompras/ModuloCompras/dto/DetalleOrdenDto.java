package ModuloCompras.ModuloCompras.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrdenDto {
    private Integer id;
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Integer ordenId;
    private Long productoId;
}


