package com.yassine.notini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yassine.notini.dto.request.note.CreateNoteRequest;
import com.yassine.notini.dto.response.note.NoteDto;
import com.yassine.notini.service.NoteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes/{id}")
    public List<NoteDto> getUserNotes(@PathVariable String id) {
        return noteService.getUserNotes(id);
    }

    @PostMapping("/createnote")
    public NoteDto createNote(@Valid @RequestBody CreateNoteRequest request){
        return noteService.createNote(request);
    }


    @PutMapping("/{id}")
    public NoteDto updateNote(@PathVariable String id, @Valid @RequestBody CreateNoteRequest request){
        return noteService.updateNote(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable String id){
        return noteService.deleteNote(id);
    }
    @PatchMapping("/pinned/{id}")
    public NoteDto updatePinnedStatus(@PathVariable String id,@RequestParam boolean pinned){
        return noteService.updatePinnedStatus(id, pinned);
    }

    
}
