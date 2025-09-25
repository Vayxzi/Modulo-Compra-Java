package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Payload.RecepcionProductoBatchRequest;
import ModuloCompras.ModuloCompras.Payload.RecepcionProductoCreateRequest;
import ModuloCompras.ModuloCompras.Service.RecepcionProductoService;
import ModuloCompras.ModuloCompras.dto.RecepcionProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recepcion")
public class RecepcionProductoController {

    @Autowired private RecepcionProductoService service;

    // Registrar UN producto
    @PostMapping
    public ResponseEntity<RecepcionProductoDto> registrar(@RequestBody RecepcionProductoCreateRequest req) {
        return ResponseEntity.ok(service.registrarRecepcion(req));
    }

    // Registrar VARIOS productos
    @PostMapping("/batch")
    public ResponseEntity<List<RecepcionProductoDto>> registrarBatch(
            @RequestBody RecepcionProductoBatchRequest batchReq) {
        return ResponseEntity.ok(service.registrarRecepciones(batchReq.getRecepciones()));
    }

    @GetMapping("/detalle/{detalleId}")
    public ResponseEntity<List<RecepcionProductoDto>> listarPorDetalle(@PathVariable Integer detalleId) {
        return ResponseEntity.ok(service.listarPorDetalle(detalleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarRecepcion(id);
        return ResponseEntity.noContent().build();
    }
}

