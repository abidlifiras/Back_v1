package com.cra.portfolio.repository;

import com.cra.portfolio.model.Application;
import com.cra.portfolio.model.AssessmentResponse;
import com.cra.portfolio.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentResponseRepository extends JpaRepository<AssessmentResponse,Integer> {
    AssessmentResponse findByApplicationAndQuestion(Application application, Question question);

}
