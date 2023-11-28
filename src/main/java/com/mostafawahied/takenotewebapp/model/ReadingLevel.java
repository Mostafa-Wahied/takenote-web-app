package com.mostafawahied.takenotewebapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reading_level")
public class ReadingLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="level", nullable = false)
    private Character level;
    @Column(name="update_date", nullable = false)
    private Date updateDate;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public ReadingLevel(Character readingLevel, Date date) {
        this.level = readingLevel;
        this.updateDate = date;
    }
}