package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class OrdenCompraUpdateRequest {
    private String codigoOrden;   //  agregado
    private String estado;
    private Integer proveedorId;
}
