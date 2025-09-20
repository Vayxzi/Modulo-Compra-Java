package ModuloCompras.ModuloCompras.repository;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    boolean existsByCodigoOrden(String codigoOrden);
}
