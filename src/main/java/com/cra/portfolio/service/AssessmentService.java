package com.cra.portfolio.service;

import com.cra.portfolio.model.Assessment;
import com.cra.portfolio.model.Category;
import com.cra.portfolio.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentService {
    private final AssessmentRepository assessmentRepository ;
    @Autowired
    private CategoryService categoryService ;



    public List<Assessment> getAllAssessments(){
        return assessmentRepository.findAll();
    }

    public void createAssessment(Assessment assessment){
        assessment.setCreatedAt(LocalDateTime.now());
        assessmentRepository.save(assessment);
    }







}
