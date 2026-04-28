package com.yassine.notini.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yassine.notini.entity.Note;

public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findByUserId(String userId);
}
