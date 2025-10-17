package ModuloCompras.ModuloCompras.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shareddtos.usersmodule.auth.SimpleUserDto;

@FeignClient(name = "validation-service", url = "${app.clients.validations}")
public interface ValidationServiceClient {
    @GetMapping("/api/auth/validation/header")
    SimpleUserDto validate$ession(
            @RequestHeader(value = "Authorization", required = true) String token
    );
}
