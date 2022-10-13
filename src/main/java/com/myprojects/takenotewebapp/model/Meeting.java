package com.myprojects.takenotewebapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

//@SqlResultSetMapping(
//        name = "MeetingsResult",
//        classes = {
//                @ConstructorResult(
//                        targetClass = com.myprojects.takenotewebapp.model.Meeting.class,
//                        columns = {
//                                @ColumnResult(name = "date", type = Date.class),
//                                @ColumnResult(name = "subject"),
//                                @ColumnResult(name = "type"),
//                                @ColumnResult(name = "subject_level"),
//                                @ColumnResult(name = "strength"),
//                                @ColumnResult(name = "teaching_point"),
//                                @ColumnResult(name = "next_step"),
//                                @ColumnResult(name = "student", type = Student.class)
//                        }
//                )
//        }
//)

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
//    @Id
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @Temporal(TemporalType.DATE)
    private Date date;
//    @Id
    private String subject;
//    @Id
    private String type;
    //    @EmbeddedId
//    private MeetingId meetingId;
    private Character subjectLevel;
    private String strength;
    private String teachingPoint;
    private String nextStep;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;

//    public Meeting(Date date, String subject, String type, Character subjectLevel, String strength, String teachingPoint, String nextStep, Student student) {
//
//        this.date = date;
//        this.subject = subject;
//        this.type = type;
//        this.meetingId = new MeetingId();
//        this.subjectLevel = subjectLevel;
//        this.strength = strength;
//        this.teachingPoint = teachingPoint;
//        this.nextStep = nextStep;
//        this.student = student;
//    }

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
                ", subjectLevel=" + subjectLevel +
                ", strength='" + strength + '\'' +
                ", teachingPoint='" + teachingPoint + '\'' +
                ", nextStep='" + nextStep + '\'' +
                '}';
    }


}
