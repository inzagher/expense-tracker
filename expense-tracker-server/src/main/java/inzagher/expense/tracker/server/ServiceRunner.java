package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.core.BackupDataOutbox;
import inzagher.expense.tracker.server.impl.FileBackupDataOutbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
public class ServiceRunner implements WebMvcConfigurer {
    @Value("${spring.resources.static-locations}")
    private String resourceLocations;
    @Value("${backup.directory}")
    private String backupDirectoryPath;

    public static void main(String[] args) {
        SpringApplication.run(ServiceRunner.class, args);
    }

    @Bean
    public BackupDataOutbox fileBackupDataOutbox() {
        return new FileBackupDataOutbox(backupDirectoryPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/**/*.css", "/**/*.html",
                "/**/*.js",  "/**/*.jsx",
                "/**/*.png", "/**/*.ttf",
                "/**/*.woff", "/**/*.woff2",
                "/**/*.ico")
                .setCachePeriod(0)
                .addResourceLocations(resourceLocations);
    }
}
