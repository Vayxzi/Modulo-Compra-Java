package ModuloCompras.ModuloCompras.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fechaPago;
    private Double monto;
    private String estado; // PENDIENTE, PAGADO

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra ordenCompra;

    @PrePersist
    public void prePersist() {
        if (fechaPago == null) fechaPago = LocalDate.now();
        if (estado == null) estado = "PENDIENTE";
    }
}

