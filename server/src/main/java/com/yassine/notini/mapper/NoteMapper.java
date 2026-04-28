package com.yassine.notini.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yassine.notini.dto.request.note.CreateNoteRequest;
import com.yassine.notini.dto.request.note.UpdateNoteRequest;
import com.yassine.notini.dto.response.note.NoteDto;
import com.yassine.notini.entity.Note;
import com.yassine.notini.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteMapper {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Note toEntity(CreateNoteRequest request) {
        try {
            String tagsJson = objectMapper.writeValueAsString(request.getTags());
            String noteId = java.util.UUID.randomUUID().toString();
            return Note.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .tags(tagsJson)
                    .pinned(request.isPinned())
                    .noteId(noteId)
                    .user(User.builder().id(request.getUserId()).build())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tags to JSON", e);
        }
    }

    public Note toEntity(UpdateNoteRequest request) {
        try {
            String tagsJson = objectMapper.writeValueAsString(request.getTags());
            return Note.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .tags(tagsJson)
                    .pinned(request.isPinned())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tags to JSON", e);
        }
    }

    public NoteDto toDto(Note note) {
        try {
            List<String> tagsList = objectMapper.readValue(note.getTags(), new TypeReference<List<String>>() {});
            return NoteDto.builder()
                    .id(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .tags(tagsList)
                    .pinned(note.isPinned())
                    .userId(note.getUser() != null ? note.getUser().getId() : null)
                    .createdAt(note.getCreatedAt())
                    .updatedAt(note.getUpdatedAt())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting tags from JSON", e);
        }
    }
}
