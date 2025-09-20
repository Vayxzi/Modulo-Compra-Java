package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class PagoCreateRequest {
    private Double monto;
    private Integer ordenId;
}

