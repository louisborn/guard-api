package com.guard.restservice.notes;

import com.guard.restservice.GService;
import com.guard.restservice.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Frage Stephan: Zeit und Datum hier berechnen oder in der Applikation?
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
        Note note1 = noteRepository.getOne(id);
        note1.setTitle(note.getTitle());
        note1.setLocation(note.getLocation());
        note1.setHasPriority(note.getHasPriority());
        note1.setDescription(note.getDescription());
        note1.setCreator(note.getCreator());
        noteRepository.save(note1);
    }

}
