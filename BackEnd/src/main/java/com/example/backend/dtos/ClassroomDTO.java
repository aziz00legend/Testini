package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {
    


    private Long id;
    private String title;
    private String titleColor;
    private Date creationDate;
    private Integer studentCount;
    private Integer assignmentCount;
    private Date nextTestDate;
    private String testTitle;





   


}
