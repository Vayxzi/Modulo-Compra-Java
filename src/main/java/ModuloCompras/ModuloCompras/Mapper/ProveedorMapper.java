package ModuloCompras.ModuloCompras.Mapper;


import ModuloCompras.ModuloCompras.Entity.Proveedor;
import ModuloCompras.ModuloCompras.dto.ProveedorDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorMapper {
    public ProveedorDto toDto(Proveedor entity) {
        ProveedorDto dto = new ProveedorDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setContacto(entity.getContacto());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setDireccion(entity.getDireccion());
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }

    public List<ProveedorDto> toDtoList(List<Proveedor> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}
