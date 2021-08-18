package com.guard.restservice.notes;

import com.guard.restservice.GService;
import com.guard.restservice.operator.Operator;
import com.guard.restservice.operator.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final GService gService;

    private final NoteRepository noteRepository;
    private final OperatorService operatorService;

    @Autowired
    public NoteService(
            GService gService,
            NoteRepository noteRepository,
            OperatorService operatorService) {
        this.gService = gService;
        this.noteRepository = noteRepository;
        this.operatorService = operatorService;
    }

    public List<Note> getNotes(String token) {
        if(!operatorService.checkTokenValidity(token)) {
            throw new IllegalStateException("Access denied");
        }
        return noteRepository.findAll();
    }

    public void addNote(String token, Note note) {
        if(!operatorService.checkTokenValidity(token)) {
            throw new IllegalStateException("Access denied");
        }
        Optional<Operator> operator = operatorService.getOperatorByToken(token);
        operator.ifPresent(value -> note.setCreator(value.getName()));
        note.setTime(gService.calculateLocalTime());
        note.setDate(gService.calculateLocalDate());
        noteRepository.save(note);
    }

    public void deleteNoteById(String token, Long id) {
        if(!operatorService.checkTokenValidity(token)) {
            throw new IllegalStateException("Access denied");
        }
        noteRepository.deleteById(id);
    }

    // Frage Stephan: Drei Parameter problematisch?
    public void updateNoteById(String token, Long id, Note note) {
        if(!operatorService.checkTokenValidity(token)) {
            throw new IllegalStateException("Access denied");
        }
        Optional<Operator> operator = operatorService.getOperatorByToken(token);
        Note updatedNote = noteRepository.getOne(id);
        updatedNote.setTitle(note.getTitle());
        updatedNote.setLocation(note.getLocation());
        updatedNote.setHasPriority(note.getHasPriority());
        updatedNote.setDescription(note.getDescription());
        operator.ifPresent(value -> updatedNote.setCreator(value.getName()));
        updatedNote.setHasUpdate(true);
        noteRepository.save(updatedNote);
    }

}
