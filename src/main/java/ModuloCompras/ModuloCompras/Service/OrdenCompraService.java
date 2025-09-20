package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.OrdenCompraCreateRequest;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraUpdateRequest;
import ModuloCompras.ModuloCompras.dto.OrdenCompraDto;

import java.util.List;

public interface OrdenCompraService {
    OrdenCompraDto addOrden(OrdenCompraCreateRequest payload);
    OrdenCompraDto updateOrden(OrdenCompraUpdateRequest payload, int id);
    OrdenCompraDto getOrdenById(int id);
    List<OrdenCompraDto> getAllOrdenes();
    void deleteOrden(int id);
}
