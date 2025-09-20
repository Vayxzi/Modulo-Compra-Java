package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Entity.Producto;
import ModuloCompras.ModuloCompras.Mapper.DetalleOrdenMapper;
import ModuloCompras.ModuloCompras.Payload.DetalleOrdenCreateRequest;
import ModuloCompras.ModuloCompras.Service.DetalleOrdenService;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;
import ModuloCompras.ModuloCompras.repository.DetalleOrdenRepository;
import ModuloCompras.ModuloCompras.repository.OrdenCompraRepository;
import ModuloCompras.ModuloCompras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired private DetalleOrdenRepository repo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private DetalleOrdenMapper mapper;

    @Override
    public DetalleOrdenDto agregarDetalle(DetalleOrdenCreateRequest req) {
        OrdenCompra orden = ordenRepo.findById(req.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Producto producto = productoRepo.findById(req.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleOrden detalle = DetalleOrden.builder()
                .cantidad(req.getCantidad())
                .precioUnitario(req.getPrecioUnitario())
                .ordenCompra(orden)
                .producto(producto)
                .build();

        return mapper.toDto(repo.save(detalle));
    }

    @Override
    public List<DetalleOrdenDto> listarPorOrden(Integer ordenId) {
        return mapper.toDtoList(repo.findByOrdenCompraId(ordenId));
    }

    @Override
    public void eliminarDetalle(Integer id) {
        repo.deleteById(id);
    }
}
