package ModuloCompras.ModuloCompras.Payload;
import lombok.Data;

@Data
public class ProveedorUpdateRequest {
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
}
