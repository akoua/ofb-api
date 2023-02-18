package com.istic.ofbapi.repository;

import com.istic.ofbapi.model.Comment;
import com.istic.ofbapi.model.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllBySheet(Sheet sheet, Pageable pageable);
}
