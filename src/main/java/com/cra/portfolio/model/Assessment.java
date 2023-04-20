package com.cra.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "assessments")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime deletedAt = null ;
    private LocalDateTime modifiedAt = null ;
    private LocalDateTime createdAt = null ;
    @OneToMany(mappedBy="assessment")
    private List<Application> applications ;
    @OneToMany(mappedBy="assessment")
    private List<Category> categories ;
    private String note ;

}
