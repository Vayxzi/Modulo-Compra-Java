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
    private ProveedorDto proveedor;
    private Double total; //  agregado
}
