package inzagher.expense.tracker.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticWebContentConfiguration implements WebMvcConfigurer {
    @Value("${spring.resources.static-locations}")
    private String resourceLocations;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler(
                    "/**/*.css", "/**/*.html",
                    "/**/*.js",  "/**/*.jsx",
                    "/**/*.png", "/**/*.ttf",
                    "/**/*.woff", "/**/*.woff2")
            .setCachePeriod(0)
            .addResourceLocations(resourceLocations);
    }
}
