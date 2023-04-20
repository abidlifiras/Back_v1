package com.cra.portfolio.service;

import com.cra.portfolio.model.Assessment;
import com.cra.portfolio.model.Category;
import com.cra.portfolio.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentService {
    private final AssessmentRepository assessmentRepository ;
    @Autowired
    private CategoryService categoryService ;

    public Assessment createAssessment(Assessment assessment){
        assessment.setCreatedAt(LocalDateTime.now());
        for (Category category : assessment.getCategories()){
            category.setAssessment(assessment);
            Category category1 = categoryService.createCategory(category);
        }
        return assessmentRepository.save(assessment);
    }






}
