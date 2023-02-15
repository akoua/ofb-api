package com.istic.ofbapi.repository;

import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Long> {
    Page<Sheet> findAllByUser(User user, Pageable pageable);

}
