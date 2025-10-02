package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Entity.Proveedor;
import ModuloCompras.ModuloCompras.Mapper.ProveedorMapper;
import ModuloCompras.ModuloCompras.Payload.ProveedorCreateRequest;
import ModuloCompras.ModuloCompras.Payload.ProveedorUpdateRequest;
import ModuloCompras.ModuloCompras.dto.ProveedorDto;
import ModuloCompras.ModuloCompras.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service   // 👈 IMPORTANTE: esto registra el bean
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository repository;
    @Autowired
    private ProveedorMapper mapper;

    @Override
    public ProveedorDto addProveedor(ProveedorCreateRequest payload) {
        if (repository.existsByNombre(payload.getNombre())) {
            throw new RuntimeException("Proveedor ya existe");
        }
        Proveedor proveedor = Proveedor.builder()
                .nombre(payload.getNombre())
                .contacto(payload.getContacto())
                .telefono(payload.getTelefono())
                .email(payload.getEmail())
                .direccion(payload.getDireccion())
                .fechaRegistro(LocalDate.now()) // <-- Se asigna la fecha aquí
                .build();
        return mapper.toDto(repository.save(proveedor));
    }

    @Override
    public ProveedorDto updateProveedor(ProveedorUpdateRequest payload, int id) {
        Proveedor proveedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        if (payload.getNombre() != null) proveedor.setNombre(payload.getNombre());
        if (payload.getContacto() != null) proveedor.setContacto(payload.getContacto());
        if (payload.getTelefono() != null) proveedor.setTelefono(payload.getTelefono());
        if (payload.getEmail() != null) proveedor.setEmail(payload.getEmail());
        if (payload.getDireccion() != null) proveedor.setDireccion(payload.getDireccion());
        return mapper.toDto(repository.save(proveedor));
    }

    @Override
    public ProveedorDto getProveedorById(int id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")));
    }

    @Override
    public List<ProveedorDto> getAllProveedores() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public void deleteProveedor(int id) {
        repository.deleteById(id);
    }
}