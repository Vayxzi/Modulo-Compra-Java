package ModuloCompras.ModuloCompras.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private Integer id;
    private String codigo;
    private String nombre;
    private Integer stock;
}

