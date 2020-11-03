package inzagher.expense.tracker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRunner.class, args);
    }
}
