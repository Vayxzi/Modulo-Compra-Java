package ModuloCompras.ModuloCompras.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecepcionProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fechaRecepcion;
    private int cantidadRecibida;
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "detalle_id", nullable = false)
    private DetalleOrden detalleOrden;

    @PrePersist
    public void prePersist() {
        if (fechaRecepcion == null) fechaRecepcion = LocalDate.now();
    }
}


