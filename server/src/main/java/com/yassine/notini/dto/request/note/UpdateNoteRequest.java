package com.yassine.notini.dto.request.note;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNoteRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;
    @Size(max = 3000, message = "Content must be less than 3000 characters")
    private String content;
    @Size(max = 5, message = "A note can have at most 5 tags")
    private List<String> tags;
    private boolean pinned;
}
