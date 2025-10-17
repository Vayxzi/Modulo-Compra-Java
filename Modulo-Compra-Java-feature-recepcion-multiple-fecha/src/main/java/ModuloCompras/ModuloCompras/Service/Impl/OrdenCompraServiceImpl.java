package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Mapper.OrdenCompraMapper;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraCreateRequest;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraUpdateRequest;
import ModuloCompras.ModuloCompras.Service.OrdenCompraService;
import ModuloCompras.ModuloCompras.dto.OrdenCompraDto;
import ModuloCompras.ModuloCompras.repository.OrdenCompraRepository;
import ModuloCompras.ModuloCompras.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired private OrdenCompraRepository repo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private OrdenCompraMapper mapper;

    @Override
    public OrdenCompraDto addOrden(OrdenCompraCreateRequest payload) {
        if (payload == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload vacío");
        }
        if (payload.getCodigoOrden() == null || payload.getCodigoOrden().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "codigoOrden es obligatorio");
        }
        if (payload.getProveedorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "proveedorId es obligatorio");
        }

        if (repo.existsByCodigoOrden(payload.getCodigoOrden())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una orden con ese código");
        }

        OrdenCompra orden = OrdenCompra.builder()
                .codigoOrden(payload.getCodigoOrden())
                .proveedor(proveedorRepo.findById(payload.getProveedorId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado")))
                .build();

        return mapper.toDto(repo.save(orden));
    }

    @Override
    public OrdenCompraDto updateOrden(OrdenCompraUpdateRequest payload, int id) {
        OrdenCompra orden = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        if (payload == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload vacío");
        }

        if (payload.getCodigoOrden() != null && !payload.getCodigoOrden().isBlank()) {
            // verificar unicidad si cambia el codigo
            if (!payload.getCodigoOrden().equals(orden.getCodigoOrden()) && repo.existsByCodigoOrden(payload.getCodigoOrden())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "codigoOrden ya en uso por otra orden");
            }
            orden.setCodigoOrden(payload.getCodigoOrden());
        }

        if (payload.getEstado() != null) {
            validarEstado(payload.getEstado());
            orden.setEstado(payload.getEstado());
        }

        return mapper.toDto(repo.save(orden));
    }

    @Override
    public OrdenCompraDto getOrdenById(int id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada")));
    }

    @Override
    public List<OrdenCompraDto> getAllOrdenes() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public void deleteOrden(int id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada");
        }
        repo.deleteById(id);
    }

    // Nuevos
    @Override
    public OrdenCompraDto actualizarEstado(int id, String estado) {
        validarEstado(estado);
        OrdenCompra orden = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));
        orden.setEstado(estado);
        return mapper.toDto(repo.save(orden));
    }

    @Override
    public List<OrdenCompraDto> getPendientes() {
        return mapper.toDtoList(repo.findByEstado("PENDIENTE"));
    }

    private void validarEstado(String estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado es nulo");
        }
        String e = estado.toUpperCase();
        if (!e.equals("PENDIENTE") && !e.equals("APROBADA") && !e.equals("CERRADA")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Estado inválido. Solo se permite: PENDIENTE, APROBADA o CERRADA");
        }
    }
}
