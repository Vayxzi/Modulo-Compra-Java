package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Payload.ProveedorCreateRequest;
import ModuloCompras.ModuloCompras.Payload.ProveedorUpdateRequest;
import ModuloCompras.ModuloCompras.Service.ProveedorService;
import ModuloCompras.ModuloCompras.dto.ProveedorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // 👈 Aquí
@RequestMapping("/proveedores") // 👈 base URL
// 👈 plural, base de todos los endpoints
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> getAll() {
        return ResponseEntity.ok(service.getAllProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getProveedorById(id));
    }

    @PostMapping
    public ResponseEntity<ProveedorDto> create(@RequestBody ProveedorCreateRequest request) {
        return new ResponseEntity<>(service.addProveedor(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDto> update(@RequestBody ProveedorUpdateRequest request, @PathVariable int id) {
        return ResponseEntity.ok(service.updateProveedor(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteProveedor(id);
        return ResponseEntity.ok().build();
    }
}
