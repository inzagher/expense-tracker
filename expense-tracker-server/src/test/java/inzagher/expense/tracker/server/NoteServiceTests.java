package inzagher.expense.tracker.server;

import inzagher.expense.tracker.server.model.dto.NoteEditDTO;
import inzagher.expense.tracker.server.model.dto.NoteReadDTO;
import inzagher.expense.tracker.server.model.dto.PersonDTO;
import inzagher.expense.tracker.server.service.NoteService;
import inzagher.expense.tracker.server.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = ExpenseTrackerServerApp.class)
class NoteServiceTests {

    @Autowired
    private NoteService noteService;
    @Autowired
    private PersonService personService;

    @Test
    void createThenEditThenDelete() {
        PersonDTO person = new PersonDTO();
        person.setName("TOM");
        Long personId = personService.createPerson(person);
        assertThat(personId).isPositive();

        NoteEditDTO creation = new NoteEditDTO();
        creation.setDate(LocalDate.now());
        creation.setSubject("TEST_NOTE_SUBJECT");
        creation.setContent("TEST_NOTE_CONTENT");
        creation.setPersonId(personId);
        Long noteId = noteService.createNote(creation);
        assertThat(noteId).isPositive();

        NoteReadDTO afterCreation = noteService.getNoteById(noteId);
        assertThat(afterCreation).isNotNull()
                .extracting("id", "subject", "content", "resolved")
                .contains(noteId, "TEST_NOTE_SUBJECT", "TEST_NOTE_CONTENT", Boolean.FALSE);

        Page<NoteReadDTO> page = noteService.findAllNotes(Pageable.ofSize(10));
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).hasSize(1);

        NoteEditDTO edit = new NoteEditDTO();
        edit.setPersonId(personId);
        edit.setSubject("TEST_NOTE_SUBJECT_2");
        edit.setContent("TEST_NOTE_CONTENT_2");
        edit.setDate(LocalDate.now().minusDays(1));
        noteService.editNote(noteId, edit);

        NoteReadDTO afterEdit = noteService.getNoteById(noteId);
        assertThat(afterEdit).isNotNull()
                .extracting("id", "subject", "content", "resolved")
                .contains(noteId, "TEST_NOTE_SUBJECT_2", "TEST_NOTE_CONTENT_2", Boolean.FALSE);

        noteService.updateNoteResolution(noteId, Boolean.TRUE);

        NoteReadDTO afterMarkAsResolved = noteService.getNoteById(noteId);
        assertThat(afterMarkAsResolved.getResolved()).isTrue();

        noteService.deleteNoteById(noteId);
        page = noteService.findAllNotes(Pageable.ofSize(10));
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isZero();

        // Assert that person is not delete with note
        assertThat(personService.findAllPersons()).hasSize(1);
    }
}
