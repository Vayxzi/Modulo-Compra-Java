package ModuloCompras.ModuloCompras.Payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraUpdateRequest {
    private String estado;
    private Integer proveedorId;
}
