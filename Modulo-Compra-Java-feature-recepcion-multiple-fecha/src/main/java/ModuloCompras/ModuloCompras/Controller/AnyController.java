package ModuloCompras.ModuloCompras.Controller;

import ModuloCompras.ModuloCompras.client.ValidationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shareddtos.usersmodule.auth.SimpleUserDto;

@RestController
@RequestMapping("/any")
public class AnyController {
    @Autowired
    private ValidationServiceClient validationServiceClient;

    @GetMapping("/header")
    public ResponseEntity<SimpleUserDto> validate$ession(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @CookieValue(value = "token", required = false) String token
    ){
        SimpleUserDto user = validationServiceClient.validate$ession(authorization);
        return ResponseEntity.ok(user);
    }
}