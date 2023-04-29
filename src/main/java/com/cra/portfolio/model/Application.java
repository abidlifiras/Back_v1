package com.cra.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "applications")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String appName;
    private String appDescription;
    private LocalDateTime deletedAt = null ;
    private LocalDateTime modifiedAt = null ;
    private LocalDateTime createdAt = null ;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @OneToOne(mappedBy = "application")
    private AssessmentResponse assessmentResponse;
    @ManyToMany(mappedBy = "applications", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Server> servers = new ArrayList<>();

    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "application_contact",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        this.contacts.add(contact);
        contact.getApplications().add(this);
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.getApplications().remove(this);
    }

}

