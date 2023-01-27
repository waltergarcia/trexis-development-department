package net.trexis.software.development.department.service;

import static net.trexis.software.development.department.util.AppConstant.ROLE_DEVELOPER;
import static net.trexis.software.development.department.util.RoleEnum.MANAGER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import net.trexis.software.development.department.dto.AddEmployeeDto;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.repository.CostRepository;
import net.trexis.software.development.department.repository.EmployeeRepository;
import net.trexis.software.development.department.repository.LevelRepository;
import net.trexis.software.development.department.repository.RoleRepository;
import net.trexis.software.development.department.repository.entity.Employee;
import net.trexis.software.development.department.repository.entity.Role;
import net.trexis.software.development.department.service.implementation.EmployeeServiceImpl;
import net.trexis.software.development.department.service.mapper.EmployeeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeService service;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CostRepository costRepository;

    @Mock
    private EmployeeMapper mapper;

    @BeforeEach
    void SetUp() {
        service = new EmployeeServiceImpl(employeeRepository, levelRepository, roleRepository, costRepository, mapper);
    }

    @Test
    void givenManagerEmployee_whenAdd_thenReturnSuccess() {
        when(roleRepository.findByRole(anyString()))
                .thenReturn(buildRoleManager());

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(Employee.builder().build());

        when(mapper.toEmployeeDto(any(Employee.class)))
                .thenReturn(new EmployeeDto());

        final EmployeeDto response = service.add(newAddEmployeeDto());

        assertNotNull(response);
    }

    @Test
    void givenDeveloperRole_whenAdd_thenReturnIllegalException() {
        final String errorMessage = "Missing manager to report";
        when(roleRepository.findByRole(anyString()))
                .thenReturn(buildDeveloperRole());

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.add(newAddEmployeeDto())
        );

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    private Role buildRoleManager() {
        return Role.builder()
                .role(MANAGER.getValue())
                .build();
    }

    private Role buildDeveloperRole() {
        return Role.builder()
                .role(ROLE_DEVELOPER)
                .build();
    }

    private AddEmployeeDto newAddEmployeeDto() {
        AddEmployeeDto newEmployee = new AddEmployeeDto();

        newEmployee.setRole(ROLE_DEVELOPER);

        return newEmployee;
    }
}
