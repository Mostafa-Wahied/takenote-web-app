package com.mostafawahied.takenotewebapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
//@ToString
//@RequiredArgsConstructor
@NoArgsConstructor
//@AllArgsConstructor
@Entity
//@IdClass(MeetingId.class)
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meetingNumber;

    private Date date;
    private String subject;
    private String type;
    private Character subjectLevel;
    private String strength;
    private String teachingPoint;
    private String nextStep;
    @ManyToOne
    private Student student;

    public Meeting(long meetingNumber
            , String subject
//            , Character subjectLevel
    ) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
//        this.subjectLevel = subjectLevel;
    }

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


        @Override
    public String toString() {
        return "Meeting{" +
                "meetingNumber=" + meetingNumber +
                ", date=" + date +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
//                ", subjectLevel=" + subjectLevel +
                ", strength='" + strength + '\'' +
                ", teachingPoint='" + teachingPoint + '\'' +
                ", nextStep='" + nextStep + '\'' +
                '}';
    }


}
