package ModuloCompras.ModuloCompras.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenCompraDto {
    private Integer id;
    private String codigoOrden;
    private LocalDate fechaOrden;
    private String estado;
    //cambie esto para que no solo sea ID
    private ProveedorDto proveedor;
}
