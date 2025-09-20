package ModuloCompras.ModuloCompras.Service;

import ModuloCompras.ModuloCompras.Payload.ProductoCreateRequest;
import ModuloCompras.ModuloCompras.Payload.ProductoUpdateRequest;
import ModuloCompras.ModuloCompras.dto.ProductoDto;

import java.util.List;

public interface ProductoService {
    ProductoDto addProducto(ProductoCreateRequest request);
    ProductoDto updateProducto(ProductoUpdateRequest request, int id);
    ProductoDto getProductoById(int id);
    List<ProductoDto> getAllProductos();
    void deleteProducto(int id);
}

