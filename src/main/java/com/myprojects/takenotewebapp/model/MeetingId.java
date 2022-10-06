package com.myprojects.takenotewebapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Embeddable
public class MeetingId implements Serializable {
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String subject;
    private String type;
}
