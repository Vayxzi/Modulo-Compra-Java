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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService {

    @Autowired private DetalleOrdenRepository repo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private DetalleOrdenMapper mapper;
    @Autowired private ProductoClient productoClient;

    @Override
    public DetalleOrdenDto agregarDetalle(DetalleOrdenCreateRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request de detalle vacío");
        }
        if (req.getOrdenId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ordenId es obligatorio");
        }
        if (req.getProductoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productoId es obligatorio");
        }
        if (req.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor que 0");
        }

        OrdenCompra orden = ordenRepo.findById(req.getOrdenId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        // No permitir agregar detalles si la orden está PENDIENTE o CERRADA
        // Requisito: PENDIENTE = no permitir detalles; solamente APROBADA permite detalles
        if (!"APROBADA".equalsIgnoreCase(orden.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se pueden agregar detalles a una orden en estado " + orden.getEstado());
        }

        // Consultar producto en Inventarios (FeignClient)
        ProductoDto producto;
        try {
            producto = productoClient.getProductoById(req.getProductoId().longValue());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error al consultar servicio de inventarios: " + e.getMessage());
        }

        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El producto con id " + req.getProductoId() + " no existe en Inventarios");
        }
        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El producto con id " + req.getProductoId() + " no está activo");
        }

        double subtotal = req.getCantidad() * producto.getPrecio();

        DetalleOrden detalle = DetalleOrden.builder()
                .cantidad(req.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .ordenCompra(orden)
                .productoId(req.getProductoId().longValue())
                .build();

        repo.save(detalle);

        // Recalcular total de la orden
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado"));

        OrdenCompra orden = detalle.getOrdenCompra();

        repo.deleteById(id);

        // Recalcular total
        double nuevoTotal = repo.findByOrdenCompraId(orden.getId())
                .stream()
                .mapToDouble(DetalleOrden::getSubtotal)
                .sum();

        orden.setTotal(nuevoTotal);
        ordenRepo.save(orden);
    }
}
