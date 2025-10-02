package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Mapper.DetalleOrdenMapper;
import ModuloCompras.ModuloCompras.Payload.DetalleOrdenCreateRequest;
import ModuloCompras.ModuloCompras.Service.DetalleOrdenService;
import ModuloCompras.ModuloCompras.client.ProductoClient;
import ModuloCompras.ModuloCompras.dto.ProductoDto;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;
import ModuloCompras.ModuloCompras.repository.DetalleOrdenRepository;
import ModuloCompras.ModuloCompras.repository.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired private DetalleOrdenRepository repo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private DetalleOrdenMapper mapper;
    @Autowired private ProductoClient productoClient;

    @Override
    public DetalleOrdenDto agregarDetalle(DetalleOrdenCreateRequest req) {
        OrdenCompra orden = ordenRepo.findById(req.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 🔎 Verificar que el producto existe en Inventarios
        ProductoDto producto = productoClient.getProductoById(req.getProductoId());
        if (producto == null) {
            throw new RuntimeException("El producto con id " + req.getProductoId() + " no existe en Inventarios");
        }
        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new RuntimeException("El producto con id " + req.getProductoId() + " no está activo");
        }

        double subtotal = req.getCantidad() * producto.getPrecio();

        DetalleOrden detalle = DetalleOrden.builder()
                .cantidad(req.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .ordenCompra(orden)
                .productoId(req.getProductoId()) // 👈 usa el ID que vino del request
                .build();

        repo.save(detalle);

        // 🔄 recalcular total
        double nuevoTotal = repo.findByOrdenCompraId(orden.getId())
                .stream()
                .mapToDouble(DetalleOrden::getSubtotal)
                .sum();

        orden.setTotal(nuevoTotal);
        ordenRepo.save(orden);

        return mapper.toDto(detalle);
    }

    @Override
    public List<DetalleOrdenDto> listarPorOrden(Integer ordenId) {
        return mapper.toDtoList(repo.findByOrdenCompraId(ordenId));
    }

    @Override
    public void eliminarDetalle(Integer id) {
        DetalleOrden detalle = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        OrdenCompra orden = detalle.getOrdenCompra();
        repo.deleteById(id);

        // 🔄 Recalcular total después de eliminar
        double nuevoTotal = repo.findByOrdenCompraId(orden.getId())
                .stream()
                .mapToDouble(DetalleOrden::getSubtotal)
                .sum();

        orden.setTotal(nuevoTotal);
        ordenRepo.save(orden);
    }
}
