package com.mostafawahied.takenotewebapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "classroom_name")
    private String className;
    @Column(name = "classroom_year")
    private int classYear;
    @OneToMany(mappedBy = "classroom")
    private List<Student> students;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Classroom(String className, int classYear) {
        this.className = className;
        this.classYear = classYear;
    }
}
