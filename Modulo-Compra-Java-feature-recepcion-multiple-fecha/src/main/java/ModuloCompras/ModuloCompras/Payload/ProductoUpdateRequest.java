package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;

@Data
public class ProductoUpdateRequest {
    private String nombre;
    private Integer stock;
}

