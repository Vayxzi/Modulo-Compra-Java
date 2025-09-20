package ModuloCompras.ModuloCompras.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String contacto;
    private String telefono;
    private String email;
    private String direccion;

    private LocalDate fechaRegistro;

    // 👇 Esto asegura que siempre se ponga la fecha al crear un nuevo proveedor
    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }
}
