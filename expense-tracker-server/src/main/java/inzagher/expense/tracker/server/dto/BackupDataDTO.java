package inzagher.expense.tracker.server.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BackupDataDTO {
    @XmlElement(name="pesron")
    @XmlElementWrapper(name="persons")
    private List<PersonDTO> persons;
    @XmlElement(name="category")
    @XmlElementWrapper(name="categories")
    private List<CategoryDTO> categories;
    @XmlElement(name="expense")
    @XmlElementWrapper(name="expenses")
    private List<ExpenseDTO> expenses;

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<ExpenseDTO> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDTO> expenses) {
        this.expenses = expenses;
    }
}
