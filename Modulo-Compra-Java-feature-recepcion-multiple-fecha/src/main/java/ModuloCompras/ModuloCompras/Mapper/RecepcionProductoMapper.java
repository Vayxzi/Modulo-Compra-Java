package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.RecepcionProducto;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;
import org.springframework.stereotype.Component;
import ModuloCompras.ModuloCompras.Entity.DetalleOrden;

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

        // ✅ Ahora solo usamos productoId
        dto.setProductoId(e.getDetalleOrden().getProductoId());

        // 🚨 El nombre del producto no se obtiene desde la BD local
        // Para traerlo deberías llamar a Feign en el Service
        dto.setProductoNombre(null);

        return dto;
    }

    public List<RecepcionProductoDto> toDtoList(List<RecepcionProducto> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public RecepcionProducto toEntity(RecepcionProductoDto dto, DetalleOrden detalleOrden) {
        RecepcionProducto entity = new RecepcionProducto();
        entity.setId(dto.getId());
        entity.setFechaRecepcion(dto.getFechaRecepcion());
        entity.setCantidadRecibida(dto.getCantidadRecibida());
        entity.setObservacion(dto.getObservacion());
        entity.setDetalleOrden(detalleOrden); // referencia obligatoria
        return entity;
    }
}



