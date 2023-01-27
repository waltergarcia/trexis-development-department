package net.trexis.software.development.department.service;

import net.trexis.software.development.department.dto.AddEmployeeDto;
import net.trexis.software.development.department.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto add(final AddEmployeeDto employeeDto);
}
