package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.Entity.Producto;
import ModuloCompras.ModuloCompras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired private ProductoRepository repo;

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto p) {
        return ResponseEntity.ok(repo.save(p));
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Integer id) {
        return ResponseEntity.of(repo.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto p) {
        Producto prod = repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        prod.setNombre(p.getNombre());
        prod.setDescripcion(p.getDescripcion());
        prod.setStock(p.getStock());
        prod.setPrecio(p.getPrecio());
        return ResponseEntity.ok(repo.save(prod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Filtros
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(repo.findByNombreContainingIgnoreCase(nombre));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> disponibles() {
        return ResponseEntity.ok(repo.findByStockGreaterThan(0));
    }
}

