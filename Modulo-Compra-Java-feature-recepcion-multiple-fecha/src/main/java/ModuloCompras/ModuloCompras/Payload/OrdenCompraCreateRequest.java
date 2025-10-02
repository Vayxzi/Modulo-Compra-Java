package ModuloCompras.ModuloCompras.Payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraCreateRequest {
    private String codigoOrden;
    private Integer proveedorId;
}
