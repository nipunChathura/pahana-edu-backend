package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Award;
import com.icbt.pahanaedu.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findByBook(Book book);
}
