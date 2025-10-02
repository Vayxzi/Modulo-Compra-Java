package ModuloCompras.ModuloCompras.client;

import ModuloCompras.ModuloCompras.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "inventario-service",
        url = "https://endpoints-production-4a52.up.railway.app/api/productos"
)
public interface ProductoClient {

    @GetMapping
    List<ProductoDto> getAllProductos();

    @GetMapping("/{id}")
    ProductoDto getProductoById(@PathVariable("id") Long id);


    @PutMapping("/increase-stock/{id}")
    void aumentarStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);

    //
    @PutMapping("/decrease-stock/{id}")
    void disminuirStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}
