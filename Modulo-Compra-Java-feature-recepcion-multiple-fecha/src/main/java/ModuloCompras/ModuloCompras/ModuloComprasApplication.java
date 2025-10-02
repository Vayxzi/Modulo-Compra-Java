package ModuloCompras.ModuloCompras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ModuloCompras.ModuloCompras.client")
public class ModuloComprasApplication {
	public static void main(String[] args) {
		SpringApplication.run(ModuloComprasApplication.class, args);
	}
}

