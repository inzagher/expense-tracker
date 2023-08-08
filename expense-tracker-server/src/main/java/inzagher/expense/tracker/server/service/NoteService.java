package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.dto.NoteEditDTO;
import inzagher.expense.tracker.server.model.dto.NoteReadDTO;
import inzagher.expense.tracker.server.model.exception.NotFoundException;
import inzagher.expense.tracker.server.model.mapper.NoteMapper;
import inzagher.expense.tracker.server.repository.NoteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;
    private final NoteMapper mapper;

    @Transactional
    public Page<NoteReadDTO> findAllNotes(@NonNull Pageable pageable) {
        log.info("Find all notes");
        return repository.findAll(pageable)
                .map(mapper::toReadDTO);
    }

    @Transactional
    public NoteReadDTO getNoteById(@NonNull Long id) {
        log.info("Find note with id {}", id);
        return repository.findById(id)
                .map(mapper::toReadDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createNote(@NonNull NoteEditDTO dto) {
        log.info("Create note. Data: {}", dto);
        var entity = mapper.toEntity(dto);
        entity.setResolved(Boolean.FALSE);
        return repository.save(entity).getId();
    }

    @Transactional
    public void editNote(@NonNull Long id, @NonNull NoteEditDTO dto) {
        log.info("Edit note. Id: {}. Data: {}", id, dto);
        var entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapper.mergeToExistingEntity(entity, dto);
        repository.save(entity);
    }

    @Transactional
    public void updateNoteResolution(@NonNull Long id, Boolean resolved) {
        log.info("Update note resolution. Id: {}. Resolved: {}", id, resolved);
        var entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        entity.setResolved(resolved);
        repository.save(entity);
    }

    @Transactional
    public void deleteNoteById(@NonNull Long id) {
        log.info("Delete note with id {}", id);
        repository.deleteById(id);
    }
}
