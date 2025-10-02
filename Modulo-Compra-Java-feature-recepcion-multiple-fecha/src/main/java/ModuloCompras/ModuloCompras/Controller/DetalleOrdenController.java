package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Payload.DetalleOrdenCreateRequest;
import ModuloCompras.ModuloCompras.Service.DetalleOrdenService;
import ModuloCompras.ModuloCompras.dto.DetalleOrdenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-orden")
public class DetalleOrdenController {

    @Autowired private DetalleOrdenService service;

    @PostMapping
    public ResponseEntity<DetalleOrdenDto> agregar(@RequestBody DetalleOrdenCreateRequest req) {
        return ResponseEntity.ok(service.agregarDetalle(req));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<DetalleOrdenDto>> listar(@PathVariable Integer ordenId) {
        return ResponseEntity.ok(service.listarPorOrden(ordenId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminarDetalle(id);
        return ResponseEntity.noContent().build();
    }
}

