package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Entity.Producto;
import ModuloCompras.ModuloCompras.Entity.RecepcionProducto;
import ModuloCompras.ModuloCompras.Mapper.RecepcionProductoMapper;
import ModuloCompras.ModuloCompras.Payload.RecepcionProductoCreateRequest;
import ModuloCompras.ModuloCompras.Service.RecepcionProductoService;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;
import ModuloCompras.ModuloCompras.repository.DetalleOrdenRepository;
import ModuloCompras.ModuloCompras.repository.ProductoRepository;
import ModuloCompras.ModuloCompras.repository.RecepcionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecepcionProductoServiceImpl implements RecepcionProductoService {

    @Autowired private RecepcionProductoRepository repo;
    @Autowired private DetalleOrdenRepository detalleRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private RecepcionProductoMapper mapper;

    @Override
    public RecepcionProductoDto registrarRecepcion(RecepcionProductoCreateRequest req) {
        // 1. Validar existencia del detalle
        DetalleOrden detalle = detalleRepo.findById(req.getDetalleId())
                .orElseThrow(() -> new RuntimeException(" Detalle de orden no encontrado"));

        // 2. Validar estado de la orden
        OrdenCompra orden = detalle.getOrdenCompra();
        if (!"APROBADA".equalsIgnoreCase(orden.getEstado())) {
            throw new RuntimeException(" No se pueden recibir productos de una orden en estado " + orden.getEstado());
        }

        // 3. Validar cantidad recibida
        if (req.getCantidadRecibida() <= 0) {
            throw new RuntimeException(" La cantidad recibida debe ser mayor a 0");
        }

        // 4. Validar que no supere lo solicitado
        int cantidadSolicitada = detalle.getCantidad();
        int cantidadRecibidaHistorico = repo.findByDetalleOrdenId(detalle.getId())
                .stream()
                .mapToInt(RecepcionProducto::getCantidadRecibida)
                .sum();

        int nuevaCantidadTotal = cantidadRecibidaHistorico + req.getCantidadRecibida();
        if (nuevaCantidadTotal > cantidadSolicitada) {
            throw new RuntimeException(" La cantidad recibida (" + nuevaCantidadTotal +
                    ") supera la cantidad solicitada (" + cantidadSolicitada + ")");
        }

        // 5. Crear recepción
        RecepcionProducto recepcion = RecepcionProducto.builder()
                .fechaRecepcion(req.getFechaRecepcion() != null ? req.getFechaRecepcion() : LocalDate.now())
                .cantidadRecibida(req.getCantidadRecibida())
                .observacion(req.getObservacion())
                .detalleOrden(detalle)
                .build();

        // 6. Actualizar stock del producto
        Producto producto = detalle.getProducto();
        producto.setStock(producto.getStock() + req.getCantidadRecibida());
        productoRepo.save(producto);

        return mapper.toDto(repo.save(recepcion));
    }

    @Override
    public List<RecepcionProductoDto> listarPorDetalle(Integer detalleId) {
        return mapper.toDtoList(repo.findByDetalleOrdenId(detalleId));
    }

    @Override
    public void eliminarRecepcion(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<RecepcionProductoDto> registrarRecepciones(List<RecepcionProductoCreateRequest> reqs) {
        return reqs.stream().map(this::registrarRecepcion).toList();
    }
}
