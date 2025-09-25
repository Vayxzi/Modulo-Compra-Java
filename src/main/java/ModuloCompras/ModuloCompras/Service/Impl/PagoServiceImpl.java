package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.OrdenCompra;
import ModuloCompras.ModuloCompras.Entity.Pago;
import ModuloCompras.ModuloCompras.Mapper.PagoMapper;
import ModuloCompras.ModuloCompras.Payload.PagoCreateRequest;
import ModuloCompras.ModuloCompras.Payload.PagoUpdateRequest;
import ModuloCompras.ModuloCompras.Service.PagoService;
import ModuloCompras.ModuloCompras.dto.PagoDto;
import ModuloCompras.ModuloCompras.repository.OrdenCompraRepository;
import ModuloCompras.ModuloCompras.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired private PagoRepository repo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private PagoMapper mapper;

    @Override
    public PagoDto registrarPago(PagoCreateRequest req) {
        OrdenCompra orden = ordenRepo.findById(req.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // validar monto
        if (!req.getMonto().equals(orden.getTotal())) {
            throw new RuntimeException("El monto del pago no coincide con el total de la orden (" + orden.getTotal() + ")");
        }

        Pago pago = Pago.builder()
                .monto(req.getMonto())
                .ordenCompra(orden)
                .estado("PAGADO")
                .build();

        repo.save(pago);

        // cerrar la orden
        orden.setEstado("CERRADA");
        ordenRepo.save(orden);

        return mapper.toDto(pago);
    }

    @Override
    public PagoDto actualizarEstado(Integer id, PagoUpdateRequest req) {
        Pago pago = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setEstado(req.getEstado());
        return mapper.toDto(repo.save(pago));
    }

    @Override
    public List<PagoDto> listarPorOrden(Integer ordenId) {
        return mapper.toDtoList(repo.findByOrdenCompraId(ordenId));
    }
}

