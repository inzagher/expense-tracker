package inzagher.expense.tracker.server.dto;

import java.time.LocalDateTime;

public class BackupMetadataDTO {
    private String id;
    private LocalDateTime time;
    private Integer expenses;
    private Integer categories;
    private Integer persons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getExpenses() {
        return expenses;
    }

    public void setExpenses(Integer expenses) {
        this.expenses = expenses;
    }

    public Integer getCategories() {
        return categories;
    }

    public void setCategories(Integer categories) {
        this.categories = categories;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }
}
