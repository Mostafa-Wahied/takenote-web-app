package com.myprojects.takenotewebapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Embeddable
public class MeetingId implements Serializable {
    private LocalDate date;
    private String subject;
    private String type;
}
