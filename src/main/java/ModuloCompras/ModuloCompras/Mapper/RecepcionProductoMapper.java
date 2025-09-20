package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.RecepcionProducto;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecepcionProductoMapper {
    public RecepcionProductoDto toDto(RecepcionProducto e) {
        RecepcionProductoDto dto = new RecepcionProductoDto();
        dto.setId(e.getId());
        dto.setFechaRecepcion(e.getFechaRecepcion());
        dto.setCantidadRecibida(e.getCantidadRecibida());
        dto.setObservacion(e.getObservacion());
        dto.setDetalleId(e.getDetalleOrden().getId());
        dto.setProductoId(e.getDetalleOrden().getProducto().getId());
        dto.setProductoNombre(e.getDetalleOrden().getProducto().getNombre());
        return dto;
    }

    public List<RecepcionProductoDto> toDtoList(List<RecepcionProducto> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}

