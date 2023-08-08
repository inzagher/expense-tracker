package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.NoteEditDTO;
import inzagher.expense.tracker.server.model.dto.NoteReadDTO;
import inzagher.expense.tracker.server.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Note operations")
@RequestMapping("/api/notes")
public class NoteApiController {

    private final NoteService service;

    @GetMapping
    @Operation(summary = "Find all notes")
    public Page<NoteReadDTO> findAll(@NonNull Pageable pageable) {
        return service.findAllNotes(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by id")
    public NoteReadDTO getById(@PathVariable Long id) {
        return service.getNoteById(id);
    }

    @PostMapping
    @Operation(summary = "Create note")
    public Long create(@RequestBody NoteEditDTO dto) {
        return service.createNote(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit note")
    public void edit(@PathVariable Long id,
                     @RequestBody NoteEditDTO dto) {
        service.editNote(id, dto);
    }

    @PutMapping("/{id}/resolution")
    @Operation(summary = "Update resolution state")
    public void updateResolution(@PathVariable Long id,
                                 @RequestBody Boolean resolved) {
        service.updateNoteResolution(id, resolved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note by id")
    public void deleteById(@PathVariable Long id) {
        service.deleteNoteById(id);
    }
}
