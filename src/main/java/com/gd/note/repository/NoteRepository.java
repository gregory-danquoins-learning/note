package com.gd.note.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gd.note.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwnerUsername(String ownerUsername);
}
