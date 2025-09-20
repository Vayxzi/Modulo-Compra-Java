package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class ProductoCreateRequest {
    private String codigo;
    private String nombre;
    private Integer stock;
}

