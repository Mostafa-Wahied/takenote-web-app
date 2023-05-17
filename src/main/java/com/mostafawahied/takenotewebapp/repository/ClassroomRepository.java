package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Classroom;
import com.mostafawahied.takenotewebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findByUser(User user);
}
