package inzagher.expense.tracker.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("scheduling-enabled")
public class SchedulingConfiguration {
}
