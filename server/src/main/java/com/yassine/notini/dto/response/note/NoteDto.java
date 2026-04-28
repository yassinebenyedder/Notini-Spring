package com.yassine.notini.dto.response.note;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private String id;
    private String title;
    private String content;
    private List<String> tags;
    private boolean pinned;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
