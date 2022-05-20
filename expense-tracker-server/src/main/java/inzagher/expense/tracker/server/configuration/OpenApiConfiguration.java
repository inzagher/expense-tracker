package inzagher.expense.tracker.server.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info();
        info.setTitle("EXPENSE-TRACKER-SERVER");
        info.setVersion("1.0.0");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
