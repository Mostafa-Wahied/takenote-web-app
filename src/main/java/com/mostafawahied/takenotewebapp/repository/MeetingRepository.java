package com.mostafawahied.takenotewebapp.repository;

import com.mostafawahied.takenotewebapp.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Principal;
import java.util.List;

//        ------------------- experimental - trying to get unique latest meetings ---------------------------
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    //    Using custom query to get meetings
    @Query("SELECT m FROM Meeting m JOIN FETCH m.student")
    List<Meeting> findAll();

    //    getting the average subject level by date

}
