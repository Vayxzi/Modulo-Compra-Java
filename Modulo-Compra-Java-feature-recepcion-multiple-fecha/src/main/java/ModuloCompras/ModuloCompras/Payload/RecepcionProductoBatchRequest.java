package ModuloCompras.ModuloCompras.Payload;

import lombok.Data;
import java.util.List;

@Data
public class RecepcionProductoBatchRequest {
    private List<RecepcionProductoCreateRequest> recepciones;
}
