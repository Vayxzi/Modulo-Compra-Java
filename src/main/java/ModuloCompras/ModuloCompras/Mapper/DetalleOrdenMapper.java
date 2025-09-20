package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.DetalleOrden;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetalleOrdenMapper {
    public DetalleOrdenDto toDto(DetalleOrden e) {
        DetalleOrdenDto dto = new DetalleOrdenDto();
        dto.setId(e.getId());
        dto.setCantidad(e.getCantidad());
        dto.setPrecioUnitario(e.getPrecioUnitario());
        dto.setProductoId(e.getProducto().getId());
        dto.setProductoNombre(e.getProducto().getNombre());
        dto.setOrdenId(e.getOrdenCompra().getId());
        return dto;
    }

    public List<DetalleOrdenDto> toDtoList(List<DetalleOrden> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}


