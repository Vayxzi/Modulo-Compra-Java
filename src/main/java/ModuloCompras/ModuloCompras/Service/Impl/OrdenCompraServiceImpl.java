package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Entity.Proveedor;
import ModuloCompras.ModuloCompras.Mapper.OrdenCompraMapper;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraCreateRequest;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraUpdateRequest;
import ModuloCompras.ModuloCompras.Service.OrdenCompraService;
import ModuloCompras.ModuloCompras.dto.OrdenCompraDto;
import ModuloCompras.ModuloCompras.repository.OrdenCompraRepository;
import ModuloCompras.ModuloCompras.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private final OrdenCompraRepository repository;
    private final ProveedorRepository proveedorRepository;
    private final OrdenCompraMapper mapper;

    public OrdenCompraServiceImpl(
            OrdenCompraRepository repository,
            ProveedorRepository proveedorRepository,
            OrdenCompraMapper mapper) {
        this.repository = repository;
        this.proveedorRepository = proveedorRepository;
        this.mapper = mapper;
    }

    @Override
    public OrdenCompraDto addOrden(OrdenCompraCreateRequest payload) {

        // Buscar el proveedor
        Proveedor proveedor = proveedorRepository.findById(payload.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        // Generar un código de orden si no viene en el payload
        String codigo = payload.getCodigoOrden();
        if (codigo == null || codigo.isBlank()) {
            codigo = generarCodigoOrden();
        }

        // Crear la orden con datos completos
        OrdenCompra orden = OrdenCompra.builder()
                .codigoOrden(codigo)
                .proveedor(proveedor)
                .estado("PENDIENTE") // Estado por defecto
                .fechaOrden(java.time.LocalDate.now()) // Fecha actual
                .build();

        return mapper.toDto(repository.save(orden));
    }

    // Método auxiliar para generar código de orden único
    private String generarCodigoOrden() {
        long count = repository.count() + 1; // Secuencia simple
        return String.format("OC-%04d", count);
    }

    @Override
    public OrdenCompraDto updateOrden(OrdenCompraUpdateRequest payload, int id) {
        OrdenCompra orden = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (payload.getEstado() != null) {
            orden.setEstado(payload.getEstado());
        }
        if (payload.getProveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(payload.getProveedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
            orden.setProveedor(proveedor);
        }

        return mapper.toDto(repository.save(orden));
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenCompraDto getOrdenById(int id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenCompraDto> getAllOrdenes() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public void deleteOrden(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Orden no encontrada para eliminar");
        }
        repository.deleteById(id);
    }
}
