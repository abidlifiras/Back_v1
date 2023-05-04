package com.cra.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ApplicationsKey implements Serializable {

    @Column(name = "applicationSrc_id")
    Integer applicationSrcId;
    @Column(name = "applicationTarget_id")
    Integer applicationTargetId;

}
