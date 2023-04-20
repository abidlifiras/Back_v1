package com.cra.portfolio.service;

import com.cra.portfolio.model.Option;
import com.cra.portfolio.model.Question;
import com.cra.portfolio.repository.OptionRepository;
import com.cra.portfolio.repository.QuestionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository ;
    @Autowired
    private  OptionService optionService ;
    public Question createQuestion(Question question) {
        question.setCreatedAt(LocalDateTime.now());
        for( Option option : question.getOptions()){
            option.setQuestion(question);
            optionService.createOption(option); }
        return  questionRepository.save(question);
    }
}
