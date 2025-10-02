package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class DetalleOrdenCreateRequest {
    private int cantidad;
    private Double precioUnitario; //
    private Long productoId;       //
    private Integer ordenId;
}



