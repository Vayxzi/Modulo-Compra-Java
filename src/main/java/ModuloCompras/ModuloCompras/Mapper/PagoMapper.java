package ModuloCompras.ModuloCompras.Mapper;

import ModuloCompras.ModuloCompras.Entity.Pago;
import ModuloCompras.ModuloCompras.dto.PagoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PagoMapper {
    public PagoDto toDto(Pago e) {
        PagoDto dto = new PagoDto();
        dto.setId(e.getId());
        dto.setFechaPago(e.getFechaPago());
        dto.setMonto(e.getMonto());
        dto.setEstado(e.getEstado());
        dto.setOrdenId(e.getOrdenCompra().getId());
        return dto;
    }

    public List<PagoDto> toDtoList(List<Pago> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}
