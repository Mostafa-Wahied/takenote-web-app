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
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false
    )
//            , columnDefinition = "int default 0")
    private User user;

    public Classroom(String className) {
        this.className = className;
    }
}
