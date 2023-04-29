package com.cra.portfolio.service;

import com.cra.portfolio.model.Application;
import com.cra.portfolio.model.AssessmentResponse;
import com.cra.portfolio.model.Question;
import com.cra.portfolio.repository.AssessmentResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentResponseService {
    @Autowired
    private AssessmentResponseRepository assessmentResponseRepository;

    public AssessmentResponse saveAssessmentResponse(AssessmentResponse assessmentResponse) {
        LocalDateTime now = LocalDateTime.now();
        assessmentResponse.setCreatedAt(now);
        assessmentResponse.setModifiedAt(now);
        return assessmentResponseRepository.save(assessmentResponse);
    }

    public List<AssessmentResponse> getAllAssessmentResponses() {
        return assessmentResponseRepository.findAll();
    }

    public AssessmentResponse getAssessmentResponseById(Integer id) {
        return assessmentResponseRepository.findById(id).orElse(null);
    }

    public AssessmentResponse getAssessmentResponseByApplicationAndQuestion(Application application, Question question) {
        return assessmentResponseRepository.findByApplicationAndQuestion(application, question);
    }

    public void deleteAssessmentResponse(Integer id) {
        AssessmentResponse assessmentResponse = assessmentResponseRepository.findById(id).orElse(null);
        if (assessmentResponse != null) {
            assessmentResponse.setDeletedAt(LocalDateTime.now());
            assessmentResponseRepository.save(assessmentResponse);
        }
    }
}
