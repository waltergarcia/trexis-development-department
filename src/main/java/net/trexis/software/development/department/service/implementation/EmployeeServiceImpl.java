package net.trexis.software.development.department.service.implementation;

import static net.trexis.software.development.department.util.AppConstant.ROLE_DEVELOPER;
import static net.trexis.software.development.department.util.RoleEnum.MANAGER;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.trexis.software.development.department.dto.AddEmployeeDto;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.repository.CostRepository;
import net.trexis.software.development.department.repository.EmployeeRepository;
import net.trexis.software.development.department.repository.LevelRepository;
import net.trexis.software.development.department.repository.RoleRepository;
import net.trexis.software.development.department.repository.entity.Cost;
import net.trexis.software.development.department.repository.entity.Employee;
import net.trexis.software.development.department.repository.entity.Level;
import net.trexis.software.development.department.repository.entity.Role;
import net.trexis.software.development.department.service.EmployeeService;
import net.trexis.software.development.department.service.mapper.EmployeeMapper;
import net.trexis.software.development.department.util.RoleEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final LevelRepository levelRepository;

    private final RoleRepository roleRepository;

    private final CostRepository costRepository;

    private final EmployeeMapper mapper;

    @Override
    public EmployeeDto add(final AddEmployeeDto employeeDto) {
        final Role role = getRole(employeeDto.getRole());

        if (!MANAGER.getValue().equals(role.getRole())
                && Objects.isNull(employeeDto.getManager())) {
            throw new IllegalArgumentException("Missing manager to report");
        }

        final String requestLevel = employeeDto.getLevel();
        final Level level = getLevel(employeeDto.getLevel());
        final Cost cost = getCost(getCostValue(role, requestLevel));
        final Employee manager = getManager(employeeDto.getManager());

        final Employee employee = Employee.builder()
                .name(employeeDto.getName())
                .email(employeeDto.getEmail())
                .role(role)
                .level(level)
                .cost(cost)
                .manager(manager)
                .build();

        Employee sa = employeeRepository.save(employee);

        return mapper.toEmployeeDto(employeeRepository.save(employee));
    }

    private Role getRole(final String role) {
        return roleRepository.findByRole(role);
    }

    private Level getLevel(final String level) {
        return levelRepository.findByLevel(level);
    }

    private int getCostValue(final Role role, final String level) {
        final RoleEnum roleEnum ;

        if (ROLE_DEVELOPER.equals(role.getRole())) {
            roleEnum = RoleEnum.fromLevel(level);
        } else {
            roleEnum = RoleEnum.fromValue(role.getRole());
        }

        return roleEnum.getCost();
    }

    private Cost getCost(final int cost) {
        return costRepository.findByCost(cost);
    }

    private Employee getManager(final String name) {
        return employeeRepository.findByName(name);
    }
}
