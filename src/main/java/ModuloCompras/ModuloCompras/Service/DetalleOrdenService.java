package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.DetalleOrdenCreateRequest;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;

import java.util.List;

public interface DetalleOrdenService {
    DetalleOrdenDto agregarDetalle(DetalleOrdenCreateRequest req);
    List<DetalleOrdenDto> listarPorOrden(Integer ordenId);
    void eliminarDetalle(Integer id);
}

