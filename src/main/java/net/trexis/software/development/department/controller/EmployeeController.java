package net.trexis.software.development.department.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.trexis.software.development.department.dto.AddEmployeeDto;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.service.EmployeeService;
import net.trexis.software.development.department.util.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/trexis/department/development/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> add(@RequestBody AddEmployeeDto employeeDto) {
        try {
            final EmployeeDto employeeDto1 = employeeService.add(employeeDto);
            return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(AppConstant.ERROR_SAVING_DATA_MSG, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
