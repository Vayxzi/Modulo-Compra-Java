package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.dto.OrdenCompraDto;
import ModuloCompras.ModuloCompras.dto.ProveedorDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdenCompraMapper {

    private final ProveedorMapper proveedorMapper;

    public OrdenCompraMapper(ProveedorMapper proveedorMapper) {
        this.proveedorMapper = proveedorMapper;
    }

    public OrdenCompraDto toDto(OrdenCompra entity) {
        ProveedorDto proveedorDto = proveedorMapper.toDto(entity.getProveedor());

        return new OrdenCompraDto(
                entity.getId(),
                entity.getCodigoOrden(),
                entity.getFechaOrden(),
                entity.getEstado(),
                proveedorDto // 👈 ahora es el objeto completo
        );
    }

    public List<OrdenCompraDto> toDtoList(List<OrdenCompra> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
