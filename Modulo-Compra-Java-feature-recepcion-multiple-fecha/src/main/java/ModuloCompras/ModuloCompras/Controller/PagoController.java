package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Payload.PagoCreateRequest;
import ModuloCompras.ModuloCompras.Payload.PagoUpdateRequest;
import ModuloCompras.ModuloCompras.Service.PagoService;
import ModuloCompras.ModuloCompras.dto.PagoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired private PagoService service;

    @PostMapping
    public ResponseEntity<PagoDto> registrar(@RequestBody PagoCreateRequest req) {
        return new ResponseEntity<>(service.registrarPago(req), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDto> actualizar(@PathVariable Integer id,
                                              @RequestBody PagoUpdateRequest req) {
        return ResponseEntity.ok(service.actualizarEstado(id, req));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<PagoDto>> listarPorOrden(@PathVariable Integer ordenId) {
        return ResponseEntity.ok(service.listarPorOrden(ordenId));
    }

    @GetMapping
    public ResponseEntity<List<PagoDto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}


