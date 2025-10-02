package ModuloCompras.ModuloCompras.Payload;
import lombok.Data;
@Data
public class ProveedorCreateRequest {
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
}
