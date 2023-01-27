package net.trexis.software.development.department.service;

import static net.trexis.software.development.department.util.AppConstant.ROLE_DEVELOPER;
import static net.trexis.software.development.department.util.RoleEnum.DEVELOPER_JUNIOR;
import static net.trexis.software.development.department.util.RoleEnum.DEVELOPER_SENIOR;
import static net.trexis.software.development.department.util.RoleEnum.MANAGER;
import static net.trexis.software.development.department.util.RoleEnum.QA_TESTER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import net.trexis.software.development.department.dto.TrexisInfoDto;
import net.trexis.software.development.department.repository.EmployeeRepository;
import net.trexis.software.development.department.repository.entity.Cost;
import net.trexis.software.development.department.repository.entity.Employee;
import net.trexis.software.development.department.repository.entity.Level;
import net.trexis.software.development.department.repository.entity.Role;
import net.trexis.software.development.department.service.implementation.DevelopmentDepartmentServiceImpl;
import net.trexis.software.development.department.service.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DevelopmentDepartmentServiceTest {

    private DevelopmentDepartmentService service;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
        service = new DevelopmentDepartmentServiceImpl(employeeRepository, mapper);
    }

    @Test
    void whenRequestInfo_thenReturnSuccess() {
        when(employeeRepository.findAll())
                .thenReturn(buildFakeEmployees());

        final TrexisInfoDto response = service.getInfo();

        assertNotNull(response);
    }

    private List<Employee> buildFakeEmployees() {
        final List<Employee> employees = new ArrayList<>();
        final List<Employee> subordinates = new ArrayList<>();

        final Employee manager = buildFakeManager();
        final Employee seniorDev = buildFakeSeniorDev(manager);
        final Employee juniorDev = buildFakeJuniorDev(manager);
        final Employee qaTester = buildFakeQaTester(manager);

        subordinates.add(seniorDev);
        subordinates.add(juniorDev);
        subordinates.add(qaTester);
        manager.setSubordinates(subordinates);

        employees.add(manager);

        return employees;
    }

    private Employee buildFakeManager() {
        return Employee.builder()
                .role(Role.builder().role(MANAGER.getValue()).build())
                .cost(Cost.builder().cost(MANAGER.getCost()).build())
                .build();
    }

    private Employee buildFakeSeniorDev(final Employee manager) {
        return Employee.builder()
                .role(Role.builder().role(ROLE_DEVELOPER).build())
                .cost(Cost.builder().cost(DEVELOPER_SENIOR.getCost()).build())
                .level(Level.builder().level(DEVELOPER_SENIOR.getLevel()).build())
                .manager(manager)
                .build();
    }

    private Employee buildFakeJuniorDev(final Employee manager) {
        return Employee.builder()
                .role(Role.builder().role(ROLE_DEVELOPER).build())
                .cost(Cost.builder().cost(DEVELOPER_JUNIOR.getCost()).build())
                .level(Level.builder().level(DEVELOPER_JUNIOR.getLevel()).build())
                .manager(manager)
                .build();
    }

    private Employee buildFakeQaTester(final Employee manager) {
        return Employee.builder()
                .role(Role.builder().role(QA_TESTER.getValue()).build())
                .cost(Cost.builder().cost(QA_TESTER.getCost()).build())
                .manager(manager)
                .build();
    }

}
