package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.PagoCreateRequest;
import ModuloCompras.ModuloCompras.Payload.PagoUpdateRequest;
import ModuloCompras.ModuloCompras.dto.PagoDto;

import java.util.List;

public interface PagoService {
    PagoDto registrarPago(PagoCreateRequest req);
    PagoDto actualizarEstado(Integer id, PagoUpdateRequest req);
    List<PagoDto> listarPorOrden(Integer ordenId);
}

