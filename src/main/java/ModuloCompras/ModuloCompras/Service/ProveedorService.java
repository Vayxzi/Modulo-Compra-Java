package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.ProveedorCreateRequest;
import ModuloCompras.ModuloCompras.Payload.ProveedorUpdateRequest;
import ModuloCompras.ModuloCompras.dto.ProveedorDto;

import java.util.List;

public interface ProveedorService {
    ProveedorDto addProveedor(ProveedorCreateRequest request);
    ProveedorDto updateProveedor(ProveedorUpdateRequest request, int id);
    ProveedorDto getProveedorById(int id);
    List<ProveedorDto> getAllProveedores();
    void deleteProveedor(int id);
}
