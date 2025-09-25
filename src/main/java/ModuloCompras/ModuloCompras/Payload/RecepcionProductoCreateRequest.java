package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecepcionProductoCreateRequest {
    private LocalDate fechaRecepcion;
    private int cantidadRecibida;
    private String observacion;
    private Integer detalleId;
}

