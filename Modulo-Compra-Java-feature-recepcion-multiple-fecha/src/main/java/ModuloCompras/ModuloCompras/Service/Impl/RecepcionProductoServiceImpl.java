package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Entity.RecepcionProducto;
import ModuloCompras.ModuloCompras.Mapper.RecepcionProductoMapper;
import ModuloCompras.ModuloCompras.Payload.RecepcionProductoCreateRequest;
import ModuloCompras.ModuloCompras.Service.RecepcionProductoService;
import ModuloCompras.ModuloCompras.client.ProductoClient;
import ModuloCompras.ModuloCompras.dto.ProductoDto;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;
import ModuloCompras.ModuloCompras.repository.DetalleOrdenRepository;
import ModuloCompras.ModuloCompras.repository.RecepcionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecepcionProductoServiceImpl implements RecepcionProductoService {

    @Autowired private RecepcionProductoRepository repo;
    @Autowired private DetalleOrdenRepository detalleRepo;
    @Autowired private RecepcionProductoMapper mapper;
    @Autowired private ProductoClient productoClient;

    @Override
    public RecepcionProductoDto registrarRecepcion(RecepcionProductoCreateRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload vacío");
        }
        if (req.getDetalleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "detalleId es obligatorio");
        }
        if (req.getCantidadRecibida() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad recibida debe ser mayor a 0");
        }

        DetalleOrden detalle = detalleRepo.findById(req.getDetalleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle de orden no encontrado"));

        OrdenCompra orden = detalle.getOrdenCompra();

        // Solo permitir recepción si la orden está APROBADA
        if (!"APROBADA".equalsIgnoreCase(orden.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se pueden recibir productos de una orden en estado " + orden.getEstado());
        }

        // Verificar que no supera lo solicitado
        int totalRecibidoHistorico = repo.findByDetalleOrdenId(detalle.getId())
                .stream()
                .mapToInt(RecepcionProducto::getCantidadRecibida)
                .sum();

        if (totalRecibidoHistorico + req.getCantidadRecibida() > detalle.getCantidad()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La cantidad recibida excede la cantidad solicitada (" + detalle.getCantidad() + ")");
        }

        RecepcionProducto recepcion = RecepcionProducto.builder()
                .fechaRecepcion(req.getFechaRecepcion() != null ? req.getFechaRecepcion() : LocalDate.now())
                .cantidadRecibida(req.getCantidadRecibida())
                .observacion(req.getObservacion())
                .detalleOrden(detalle)
                .build();

        repo.save(recepcion);

        // Intentar actualizar inventario remoto (Feign)
        try {
            productoClient.aumentarStock(detalle.getProductoId(), req.getCantidadRecibida());
        } catch (Exception e) {
            // Si falla el cliente, propagamos como SERVICE_UNAVAILABLE
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error al comunicar con el servicio de inventarios: " + e.getMessage());
        }

        // Consultar producto para completar nombre en DTO (no obligatorio)
        ProductoDto producto = null;
        try {
            producto = productoClient.getProductoById(detalle.getProductoId());
        } catch (Exception ignored) {
            producto = null;
        }

        RecepcionProductoDto dto = mapper.toDto(recepcion);
        dto.setProductoNombre(producto != null ? producto.getNombre() : "No disponible");

        return dto;
    }

    @Override
    public List<RecepcionProductoDto> listarPorDetalle(Integer detalleId) {
        List<RecepcionProducto> recepciones = repo.findByDetalleOrdenId(detalleId);
        return recepciones.stream().map(r -> {
            RecepcionProductoDto dto = mapper.toDto(r);
            try {
                ProductoDto producto = productoClient.getProductoById(dto.getProductoId());
                dto.setProductoNombre(producto != null ? producto.getNombre() : "No disponible");
            } catch (Exception e) {
                dto.setProductoNombre("No disponible");
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void eliminarRecepcion(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recepción no encontrada");
        }
        repo.deleteById(id);
    }

    @Override
    public List<RecepcionProductoDto> registrarRecepciones(List<RecepcionProductoCreateRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de recepciones vacía");
        }
        return reqs.stream().map(this::registrarRecepcion).collect(Collectors.toList());
    }
}

