package net.trexis.software.development.department.service.implementation;

import static net.trexis.software.development.department.util.AppConstant.DEPARTMENT_COST_TEMPLATE;
import static net.trexis.software.development.department.util.AppConstant.MANAGER_COST_TEMPLATE;
import static net.trexis.software.development.department.util.AppConstant.MANAGER_UNDERSTAFFED_TEMPLATE;
import static net.trexis.software.development.department.util.AppConstant.LITTLE_RESPONSIBILITY_TEMPLATE;
import static net.trexis.software.development.department.util.AppConstant.ROLE_DEVELOPER;
import static net.trexis.software.development.department.util.AppConstant.NO_LITTLE_RESPONSIBILITY_TEMPLATE;
import static net.trexis.software.development.department.util.AppConstant.STRING_JOIN_DELIMITER;
import static net.trexis.software.development.department.util.RoleEnum.MANAGER;
import static net.trexis.software.development.department.util.RoleEnum.DEVELOPER_SENIOR;
import static net.trexis.software.development.department.util.RoleEnum.DEVELOPER_JUNIOR;
import static net.trexis.software.development.department.util.RoleEnum.QA_TESTER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.trexis.software.development.department.dto.DetailDto;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.dto.TrexisInfoDto;
import net.trexis.software.development.department.repository.EmployeeRepository;
import net.trexis.software.development.department.repository.entity.Employee;
import net.trexis.software.development.department.service.DevelopmentDepartmentService;
import net.trexis.software.development.department.service.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class DevelopmentDepartmentServiceImpl implements DevelopmentDepartmentService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper mapper;

    @Override
    public TrexisInfoDto getInfo() {
        final List<Employee> employees = employeeRepository.findAll();
        final List<Employee> topLevelEmployees = getTolLevelEmployees(employees);

        final List<EmployeeDto> employeeDtos =  mapper.toEmployeeDtoList(employees);
        final List<EmployeeDto> topLevelManagers =  mapper.toEmployeeDtoList(topLevelEmployees);

        final DetailDto detailDto = DetailDto.builder()
                .employeeDto(topLevelManagers)
                .build();

        return TrexisInfoDto.builder()
                .summary(buildSummaryInfo(employees, employeeDtos))
                .detail(detailDto)
                .build();
    }

    private List<Employee> getTolLevelEmployees(final List<Employee> employees) {
        return employees
                .stream()
                .filter(e -> Objects.isNull(e.getManager()))
                .collect(Collectors.toList());
    }

    private List<String> buildSummaryInfo(final List<Employee> employees,
                                          final List<EmployeeDto> employeeDtos) {
        final List<EmployeeDto> managers = getManagers(employeeDtos);
        final List<String> summaryInfo = buildManagerCost(managers);
        final List<String> managersWithLittleResponsibility =
                findManagersWithLittleResponsibility(managers);
        final String managerUnderstaffed =
                buildManagerUnderstaffed(managers, managersWithLittleResponsibility);

        summaryInfo.add(buildDepartmentCost(calcDepartmentCost(employees)));
        summaryInfo.add(buildManagerWithLittleResponsibility(managersWithLittleResponsibility));

        if (!managerUnderstaffed.startsWith(" ")) {
            summaryInfo.add(managerUnderstaffed);
        }

        return summaryInfo;
    }

    private List<EmployeeDto> getManagers(final List<EmployeeDto> employeeDtos) {
        return employeeDtos
                .stream()
                .filter(e -> MANAGER.getValue().equals(e.getRole()))
                .collect(Collectors.toList());
    }

    private List<String> buildManagerCost(final List<EmployeeDto> employeeDtos) {
        final List<String> managerCost = new ArrayList<>();

        employeeDtos.forEach(m -> {
            final String cost = String.format(MANAGER_COST_TEMPLATE, m.getName(), m.getCost());
            managerCost.add(cost);
        });

        return managerCost;
    }

    private String buildDepartmentCost(final int departmentCost) {
        return String.format(DEPARTMENT_COST_TEMPLATE, departmentCost);
    }

    private int calcDepartmentCost(final List<Employee> employees) {
        return employees.stream()
                .map(e -> e.getCost().getCost())
                .reduce(0, Integer::sum);
    }

    private String buildManagerWithLittleResponsibility(
            final List<String> managersWithLittleResponsibility) {

        if (CollectionUtils.isEmpty(managersWithLittleResponsibility)) {
            return NO_LITTLE_RESPONSIBILITY_TEMPLATE;
        }

        return String.format(LITTLE_RESPONSIBILITY_TEMPLATE, String.join(STRING_JOIN_DELIMITER,
                        managersWithLittleResponsibility));
    }
    private List<String> findManagersWithLittleResponsibility(
            final List<EmployeeDto> managers) {
        final List<String> managersWithLittleResponsibility = new ArrayList<>();

        for (final EmployeeDto manager : managers) {
            if (managerHasLittleResponsibility(manager.getSubordinates())) {
                managersWithLittleResponsibility.add(manager.getName());
            }
        }

        return managersWithLittleResponsibility;
    }

    private boolean managerHasLittleResponsibility(final List<EmployeeDto> subordinates) {
        return subordinates.size() == 1
                && MANAGER.getValue().equals(subordinates.get(0).getRole());

    }

    private String buildManagerUnderstaffed(final List<EmployeeDto> allManagers,
                                    final List<String> managersWithLittleResponsibility) {
        final List<EmployeeDto> managersWithStaff =
                excludeManagersWithLittleResponsibility(allManagers,
                        managersWithLittleResponsibility);
        final List<String> managersUnderstaffed = new ArrayList<>();

        for (final EmployeeDto managerWithStaff : managersWithStaff) {
            if (!isDevelopmentGroup(managerWithStaff.getSubordinates())) {
                managersUnderstaffed.add(managerWithStaff.getName());
            }
        }

        return String.format(MANAGER_UNDERSTAFFED_TEMPLATE,
                String.join(STRING_JOIN_DELIMITER, managersUnderstaffed));
    }

    private List<EmployeeDto> excludeManagersWithLittleResponsibility(
            final List<EmployeeDto> allManagers,
            final List<String> managersWithLittleResponsibility) {
        final List<EmployeeDto> managersWithStaff = new ArrayList<>();

        for (final EmployeeDto manager : allManagers) {
            for (final String managerLittleResponsibility : managersWithLittleResponsibility) {
                if (!manager.getName().equals(managerLittleResponsibility)) {
                    managersWithStaff.add(manager);
                }
            }
        }

        return managersWithStaff;
    }

    private boolean isDevelopmentGroup(final List<EmployeeDto> subordinates) {
        boolean hasSeniorDeveloper = false;
        boolean hasJuniorDeveloper = false;
        boolean hasQATester = false;
        int managersUnderManager = 0;

        for (final EmployeeDto subordinate : subordinates) {
            if (ROLE_DEVELOPER.equals(subordinate.getRole())) {
                if (DEVELOPER_SENIOR.getLevel().equals(subordinate.getLevel())) {
                    hasSeniorDeveloper = true;
                }
                if (DEVELOPER_JUNIOR.getLevel().equals(subordinate.getLevel())) {
                    hasJuniorDeveloper = true;
                }
            } else if (QA_TESTER.getValue().equals(subordinate.getRole())) {
                hasQATester = true;
            } else if (MANAGER.getValue().equals(subordinate.getRole())) {
                managersUnderManager += 1;
            }
        }

        return hasSeniorDeveloper && hasJuniorDeveloper && hasQATester || managersUnderManager > 1;
    }
}
