package com.guard.restservice.notes;

import com.guard.restservice.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final OperatorService operatorService;

    @Autowired
    public NoteService(NoteRepository noteRepository, OperatorService operatorService) {
        this.noteRepository = noteRepository;
        this.operatorService = operatorService;
    }

    public List<Note> getNotes(String token) {
        if(!operatorService.checkTokenValidity(token)) {
            throw new IllegalStateException("Access denied");
        }
        return noteRepository.findAll();
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
