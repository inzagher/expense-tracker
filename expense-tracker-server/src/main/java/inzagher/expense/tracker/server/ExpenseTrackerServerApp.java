package inzagher.expense.tracker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExpenseTrackerServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerServerApp.class, args);
    }
}
