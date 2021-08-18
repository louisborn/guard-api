package com.guard.restservice.notes;

import com.guard.restservice.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
