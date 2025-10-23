package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.RecepcionProductoCreateRequest;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;

import java.util.List;

public interface RecepcionProductoService {
    RecepcionProductoDto registrarRecepcion(RecepcionProductoCreateRequest req);
    List<RecepcionProductoDto> listarPorDetalle(Integer detalleId);
    void eliminarRecepcion(Integer id);

    // Nuevo método para registrar varios
    List<RecepcionProductoDto> registrarRecepciones(List<RecepcionProductoCreateRequest> reqs);
     List<RecepcionProductoDto> listarTodas();
}
