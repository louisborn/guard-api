package com.guard.restservice.notes;

import com.guard.restservice.LocalCalculation;
import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class NoteService {
    /** Used to calculate the current local time and date. */
    private final LocalCalculation globalService;

    /** Instance for database communication. */
    private final NoteRepository noteRepository;

    private final OperatorService operatorService;

    @Autowired
    public NoteService(
            LocalCalculation globalService,
            NoteRepository noteRepository,
            OperatorService operatorService) {
        this.globalService = globalService;
        this.noteRepository = noteRepository;
        this.operatorService = operatorService;
    }

    public List<Note> getNotes() {
        try {
            return noteRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void addNote(String token, Note note) {
        try {
            Optional<Operator> operator = operatorService.getOperatorByToken(token);

            operator.ifPresent(value -> note.setCreator(value.getName()));
            note.setTime(globalService.calculateLocalTime());
            note.setDate(globalService.calculateLocalDate());

            noteRepository.save(note);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void deleteNoteById(Long id) {
        try {
            noteRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void updateNoteById(Long id, Note note) {
        try {
            Note updatedNote = noteRepository.getOne(id);

            updatedNote.setTitle(note.getTitle());
            updatedNote.setLocation(note.getLocation());
            updatedNote.setHasPriority(note.getHasPriority());
            updatedNote.setDescription(note.getDescription());
            updatedNote.setHasUpdate(true);

            noteRepository.save(updatedNote);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
