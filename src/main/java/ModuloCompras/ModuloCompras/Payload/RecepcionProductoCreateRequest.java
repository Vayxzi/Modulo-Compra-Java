package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class RecepcionProductoCreateRequest {
    private int cantidadRecibida;
    private String observacion;
    private Integer detalleId;
}

