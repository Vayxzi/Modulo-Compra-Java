package ModuloCompras.ModuloCompras.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String codigoOrden;

    private LocalDate fechaOrden;

    private String estado; // Ejemplo: "PENDIENTE", "APROBADA", "CERRADA"

    // Relación con Proveedor
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @PrePersist
    public void prePersist() {
        if (fechaOrden == null) {
            fechaOrden = LocalDate.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }
}
