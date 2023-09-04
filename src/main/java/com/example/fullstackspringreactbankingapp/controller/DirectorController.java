package com.example.fullstackspringreactbankingapp.controller;

import com.example.fullstackspringreactbankingapp.dto.DirectorController.AddNewRecruitDto;
import com.example.fullstackspringreactbankingapp.dto.DirectorController.DismissRecruitDto;
import com.example.fullstackspringreactbankingapp.entities.Director;
import com.example.fullstackspringreactbankingapp.entities.Employee;
import com.example.fullstackspringreactbankingapp.services.DirectorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/director")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DIRECTOR', 'ADMIN')")
public class DirectorController {

    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    @PostMapping("/add_new_recruit")
    public ResponseEntity<String> addNewRecruit(@RequestBody AddNewRecruitDto addNewRecruitDto){
        try{
            Director directorById = directorService.getDirectorById(addNewRecruitDto.getDirectorId());
            Employee map = modelMapper.map(addNewRecruitDto, Employee.class);
            map.setDirector(directorById); map.setId(null);

            directorService.addNewRecruit(map);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Hired new recruit with name " + addNewRecruitDto.getFullName());
    }

    @PostMapping("/dismiss_recruit")
    public ResponseEntity<String> dismissRecruit(@RequestBody DismissRecruitDto dismissRecruitDto){
        try{
            directorService.dismissRecruit(dismissRecruitDto.getEmployeeId());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Employee dismissed with ID = " + dismissRecruitDto.getEmployeeId());
    }

    @GetMapping("/monthly_operation")
    public ResponseEntity<String> monthlyOperation(){
        directorService.fulfillMonthlyOperations();
        return ResponseEntity.ok("Monthly operations has performed");
    }



}
