package ModuloCompras.ModuloCompras.repository;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
    List<DetalleOrden> findByOrdenCompraId(Integer ordenId);
}

