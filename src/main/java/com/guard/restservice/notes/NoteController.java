package com.guard.restservice.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "{token}/notes")
    public List<Note> getNotes(
            @PathVariable("token") String token
    ) {
        return noteService.getNotes(token);
    }

    @PostMapping(path = "{token}/notes/add")
    public Map<String, Boolean> addNote(
            @PathVariable("token") String token,
            @RequestBody Note note
    ) {
        noteService.addNote(token, note);
        Map<String, Boolean> response = new HashMap<>();
        response.put("added", Boolean.TRUE);
        return response;
    }

    @DeleteMapping(path = "{token}/notes/delete/{noteId}")
    public Map<String, Boolean> deleteNoteById(
            @PathVariable("token") String token,
            @PathVariable("noteId") Long id
    ) {
        noteService.deleteNoteById(token, id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(path = "{token}/notes/update/{noteId}")
    public Map<String, Boolean> updateNoteById(
            @PathVariable("token") String token,
            @PathVariable("noteId") Long id,
            @RequestBody Note note
    ) {
        noteService.updateNoteById(token, id, note);
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return response;
    }
}
