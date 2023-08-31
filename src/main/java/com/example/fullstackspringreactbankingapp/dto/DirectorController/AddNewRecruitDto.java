package com.example.fullstackspringreactbankingapp.dto.DirectorController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddNewRecruitDto {
    private String fullName;
    private String email;
    private String password;
    private Double netSalary;
    private long directorId;
}
