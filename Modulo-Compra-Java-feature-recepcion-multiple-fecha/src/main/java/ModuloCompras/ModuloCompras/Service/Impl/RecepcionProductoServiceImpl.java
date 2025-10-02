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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecepcionProductoServiceImpl implements RecepcionProductoService {

    @Autowired private RecepcionProductoRepository repo;
    @Autowired private DetalleOrdenRepository detalleRepo;
    @Autowired private RecepcionProductoMapper mapper;
    @Autowired private ProductoClient productoClient;

    @Override
    public RecepcionProductoDto registrarRecepcion(RecepcionProductoCreateRequest req) {
        DetalleOrden detalle = detalleRepo.findById(req.getDetalleId())
                .orElseThrow(() -> new RuntimeException("Detalle de orden no encontrado"));

        OrdenCompra orden = detalle.getOrdenCompra();
        if (!"APROBADA".equalsIgnoreCase(orden.getEstado())) {
            throw new RuntimeException("No se pueden recibir productos de una orden en estado " + orden.getEstado());
        }

        if (req.getCantidadRecibida() <= 0) {
            throw new RuntimeException("La cantidad recibida debe ser mayor a 0");
        }

        // 4. Crear recepción
        RecepcionProducto recepcion = RecepcionProducto.builder()
                .fechaRecepcion(req.getFechaRecepcion() != null ? req.getFechaRecepcion() : LocalDate.now())
                .cantidadRecibida(req.getCantidadRecibida())
                .observacion(req.getObservacion())
                .detalleOrden(detalle)
                .build();

        repo.save(recepcion);

        // 5. Consumir Inventarios para aumentar stock
        productoClient.aumentarStock(detalle.getProductoId(), req.getCantidadRecibida());
// 6. Consultar producto en Inventarios para completar info
        ProductoDto producto = productoClient.getProductoById(detalle.getProductoId());

        RecepcionProductoDto dto = mapper.toDto(recepcion);
        dto.setProductoNombre(producto.getNombre());



        return dto;
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

