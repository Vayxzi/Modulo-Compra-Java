package ModuloCompras.ModuloCompras.repository;

import ModuloCompras.ModuloCompras.Entity.RecepcionProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecepcionProductoRepository extends JpaRepository<RecepcionProducto, Integer> {
    List<RecepcionProducto> findByDetalleOrdenId(Integer detalleId);
}

