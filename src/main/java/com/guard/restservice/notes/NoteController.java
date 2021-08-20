package com.guard.restservice.notes;

import com.guard.restservice.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NoteController {
    /** The response map after a request */
    private final Map<String, Boolean> response = new HashMap<>();

    private final NoteService noteService;

    /** Used to validate the unique operator token
     *  included in the api requests' header.
     *  If the token validation fails, the request is denied.
     */
    private final TokenService tokenService;

    @Autowired
    public NoteController(
            NoteService noteService,
            TokenService tokenService) {
        this.noteService = noteService;
        this.tokenService = tokenService;
    }

    @GetMapping(path = "notes")
    public List<Note> getNotes(
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        return noteService.getNotes();
     }

    @PostMapping(path = "notes/add")
    public Map<String, Boolean> addNote(
            @RequestHeader(name = "X-TOKEN") String token,
            @RequestBody Note note
    ) {
        tokenService.validateTokenAtRequest(token);

        noteService.addNote(token, note);

        response.put("added", Boolean.TRUE);
        return response;
    }

    @DeleteMapping(path = "notes/delete/{noteId}")
    public Map<String, Boolean> deleteNoteById(
            @PathVariable("noteId") Long id,
            @RequestHeader(name = "X-TOKEN") String token
    ) {
        tokenService.validateTokenAtRequest(token);

        noteService.deleteNoteById(id);

        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping(path = "notes/update/{noteId}")
    public Map<String, Boolean> updateNoteById(
            @PathVariable("noteId") Long id,
            @RequestHeader(name = "X-TOKEN") String token,
            @RequestBody Note note
    ) {
        tokenService.validateTokenAtRequest(token);

        noteService.updateNoteById(id, note);

        response.put("updated", Boolean.TRUE);
        return response;
    }
}
