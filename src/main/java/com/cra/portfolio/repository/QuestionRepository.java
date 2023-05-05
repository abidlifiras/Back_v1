package com.cra.portfolio.repository;

import com.cra.portfolio.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByCategoryId(Integer categoryId);
}
