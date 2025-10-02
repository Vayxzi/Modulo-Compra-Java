package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetalleOrdenMapper {

    public DetalleOrdenDto toDto(DetalleOrden entity) {
        DetalleOrdenDto dto = new DetalleOrdenDto();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setSubtotal(entity.getSubtotal());
        dto.setOrdenId(entity.getOrdenCompra().getId());
        dto.setProductoId(entity.getProductoId()); //
        return dto;
    }

    public List<DetalleOrdenDto> toDtoList(List<DetalleOrden> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}

