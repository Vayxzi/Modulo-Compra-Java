package ModuloCompras.ModuloCompras.Service.Impl;

import ModuloCompras.ModuloCompras.Entity.Producto;
import ModuloCompras.ModuloCompras.Mapper.ProductoMapper;
import ModuloCompras.ModuloCompras.Payload.ProductoCreateRequest;
import ModuloCompras.ModuloCompras.Payload.ProductoUpdateRequest;
import ModuloCompras.ModuloCompras.Service.ProductoService;
import ModuloCompras.ModuloCompras.dto.ProductoDto;
import ModuloCompras.ModuloCompras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ProductoMapper mapper;

    @Override
    public ProductoDto addProducto(ProductoCreateRequest payload) {
        if (repository.existsByCodigo(payload.getCodigo())) {
            throw new RuntimeException("Código de producto ya existe");
        }
        Producto producto = Producto.builder()
                .codigo(payload.getCodigo())
                .nombre(payload.getNombre())
                .stock(payload.getStock())
                .build();
        return mapper.toDto(repository.save(producto));
    }

    @Override
    public ProductoDto updateProducto(ProductoUpdateRequest payload, int id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (payload.getNombre() != null) producto.setNombre(payload.getNombre());
        if (payload.getStock() != null) producto.setStock(payload.getStock());
        return mapper.toDto(repository.save(producto));
    }

    @Override
    public ProductoDto getProductoById(int id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

    @Override
    public List<ProductoDto> getAllProductos() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public void deleteProducto(int id) {
        repository.deleteById(id);
    }
}


