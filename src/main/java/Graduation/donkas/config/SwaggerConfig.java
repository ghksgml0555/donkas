package Graduation.donkas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("블록체인 기반 숙박 공유 플랫폼")
                        .description("블록체인 기반 숙박 공유 플랫폼")
                        .version("1.0.0"));
    }

}