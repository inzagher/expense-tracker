package inzagher.expense.tracker.server.configuration;

import inzagher.expense.tracker.server.model.mapper.BackupMetadataMapper;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.model.mapper.ExpenseMapper;
import inzagher.expense.tracker.server.model.mapper.PersonMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {
    @Bean
    public BackupMetadataMapper backupMetadataMapper() {
        return Mappers.getMapper(BackupMetadataMapper.class);
    }

    @Bean
    public ExpenseMapper expenseMapper() {
        return Mappers.getMapper(ExpenseMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public PersonMapper personMapper() {
        return Mappers.getMapper(PersonMapper.class);
    }
}
