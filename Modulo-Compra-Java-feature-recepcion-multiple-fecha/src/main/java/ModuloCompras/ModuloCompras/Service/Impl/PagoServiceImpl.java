import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired private PagoRepository repo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private PagoMapper mapper;

    @Override
    public PagoDto registrarPago(PagoCreateRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload vacío");
        }
        if (req.getOrdenId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ordenId es obligatorio");
        }
        if (req.getMonto() == null || req.getMonto() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto del pago debe ser mayor a 0");
        }

        OrdenCompra orden = ordenRepo.findById(req.getOrdenId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        // Solo permitir pagos en órdenes APROBADAS
        if (!"APROBADA".equalsIgnoreCase(orden.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se pueden registrar pagos para una orden en estado " + orden.getEstado());
        }

        Pago pago = Pago.builder()
                .monto(req.getMonto())
                .fechaPago(LocalDate.now())
                .estado("PAGADO")
                .ordenCompra(orden)
                .build();

        repo.save(pago);

        // Calcular total pagado y si cubre total → cerrar orden
        double totalPagado = repo.findByOrdenCompraId(orden.getId())
                .stream()
                .mapToDouble(Pago::getMonto)
                .sum();

        if (totalPagado >= orden.getTotal()) {
            orden.setEstado("CERRADA");
            ordenRepo.save(orden);
        }

        return mapper.toDto(pago);
    }

    @Override
    public PagoDto actualizarEstado(Integer id, PagoUpdateRequest req) {
        if (req == null || req.getEstado() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado es obligatorio");
        }
        Pago pago = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        pago.setEstado(req.getEstado());
        return mapper.toDto(repo.save(pago));
    }

    @Override
    public List<PagoDto> listarPorOrden(Integer ordenId) {
        return mapper.toDtoList(repo.findByOrdenCompraId(ordenId));
    }

    @Override
public List<PagoDto> listarTodos() {
    return mapper.toDtoList(repo.findAll());
}

}

