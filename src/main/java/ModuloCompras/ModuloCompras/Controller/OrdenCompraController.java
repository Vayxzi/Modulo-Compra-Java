package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Payload.OrdenCompraCreateRequest;
import ModuloCompras.ModuloCompras.Payload.OrdenCompraUpdateRequest;
import ModuloCompras.ModuloCompras.Service.OrdenCompraService;
import ModuloCompras.ModuloCompras.dto.OrdenCompraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orden-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService service;

    @GetMapping
    public ResponseEntity<List<OrdenCompraDto>> getAll() {
        return ResponseEntity.ok(service.getAllOrdenes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getOrdenById(id));
    }

    @PostMapping
    public ResponseEntity<OrdenCompraDto> create(@RequestBody OrdenCompraCreateRequest request) {
        return new ResponseEntity<>(service.addOrden(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompraDto> update(@RequestBody OrdenCompraUpdateRequest request, @PathVariable int id) {
        return ResponseEntity.ok(service.updateOrden(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteOrden(id);
        return ResponseEntity.ok().build();
    }
}
