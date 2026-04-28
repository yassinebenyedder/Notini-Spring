package com.yassine.notini.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassine.notini.dto.request.note.CreateNoteRequest;
import com.yassine.notini.dto.response.note.NoteDto;
import com.yassine.notini.mapper.NoteMapper;
import com.yassine.notini.repository.NoteRepository;

@Service
public class NoteService{
    @Autowired
    private NoteRepository noteRepo;

    @Autowired
    private NoteMapper noteMapper;
    
    public List<NoteDto> getUserNotes(String userId){
        return noteRepo.findByUserId(userId)
            .stream()
            .map(noteMapper::toDto)
            .toList();
    }


    public NoteDto createNote(CreateNoteRequest request){
        var note = noteRepo.save(noteMapper.toEntity(request));
        return noteMapper.toDto(note);
    }



    public NoteDto updateNote(String id, CreateNoteRequest request){
        var existingNote = noteRepo.findById(id);
        if(existingNote.isPresent()) {
            var note = existingNote.get();
            note.setTitle(request.getTitle());
            note.setContent(request.getContent());
            note.setPinned(request.isPinned());
            // Update tags as JSON
            try {
                com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String tagsJson = objectMapper.writeValueAsString(request.getTags());
                note.setTags(tagsJson);
            } catch (Exception e) {
                throw new RuntimeException("Error converting tags to JSON", e);
            }
            var updatedNote = noteRepo.save(note);
            return noteMapper.toDto(updatedNote);
        }
        return null;
    }
    public String deleteNote(String id) {
        noteRepo.deleteById(id);
        return id;
    }
    public NoteDto updatePinnedStatus(String id, boolean pinned) {
        var note = noteRepo.findById(id);
        if(note.isPresent()) {
            var n = note.get();
            n.setPinned(pinned);
            return noteMapper.toDto(noteRepo.save(n));
        }
        return null;
    }

    
}
