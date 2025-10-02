package ModuloCompras.ModuloCompras.repository;

import ModuloCompras.ModuloCompras.Entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByOrdenCompraId(Integer ordenId);
}
