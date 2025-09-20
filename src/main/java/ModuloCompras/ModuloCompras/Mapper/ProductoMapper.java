package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.Producto;
import ModuloCompras.ModuloCompras.dto.ProductoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoMapper {
    public ProductoDto toDto(Producto entity) {
        ProductoDto dto = new ProductoDto();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setStock(entity.getStock());
        return dto;
    }

    public List<ProductoDto> toDtoList(List<Producto> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}

