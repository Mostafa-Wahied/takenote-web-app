package com.myprojects.takenotewebapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MeetingId.class)
@Table(name = "meetings")
public class Meeting {
    @Id
    private LocalDate date;
    @Id
    private String subject;
    @Id
    private String type;
//    @EmbeddedId
    private MeetingId meetingId;
    private Character subjectLevel;
    private String strength;
    private String teachingPoint;
    private String nextStep;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;
}
