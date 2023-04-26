package com.cra.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "OptionResponse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String answer;
    private LocalDateTime deletedAt = null;
    private LocalDateTime modifiedAt = null;
    private LocalDateTime createdAt = null;
}
