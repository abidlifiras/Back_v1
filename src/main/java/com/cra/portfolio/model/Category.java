package com.cra.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "Categories")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Category {
    private Integer step ;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String category ;

    private LocalDateTime createdAt = null ;

    private LocalDateTime deletedAt = null ;
    private LocalDateTime modifiedAt = null ;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @OneToMany(mappedBy="category",cascade = CascadeType.ALL)
    private List<Question> questions;

}
