package com.myprojects.takenotewebapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@IdClass(MeetingId.class)
@Table(name = "meetings")
public class Meeting {
    @Id
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @Temporal(TemporalType.DATE)
    private Date date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meeting meeting = (Meeting) o;
        return date != null && Objects.equals(date, meeting.date)
                && subject != null && Objects.equals(subject, meeting.subject)
                && type != null && Objects.equals(type, meeting.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, subject, type);
    }
}
