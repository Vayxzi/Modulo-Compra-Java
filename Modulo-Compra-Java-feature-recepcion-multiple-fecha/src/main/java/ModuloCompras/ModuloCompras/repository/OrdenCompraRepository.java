package ModuloCompras.ModuloCompras.repository;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    boolean existsByCodigoOrden(String codigoOrden);

    // Nuevo para filtrar pendientes
    List<OrdenCompra> findByEstado(String estado);
}

