package ModuloCompras.ModuloCompras.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cantidad;
    private Double precioUnitario;
    private Double subtotal; //  nuevo campo

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @PrePersist
    public void calcularSubtotal() {
        if (subtotal == null && precioUnitario != null) {
            subtotal = cantidad * precioUnitario;
        }
    }
}

