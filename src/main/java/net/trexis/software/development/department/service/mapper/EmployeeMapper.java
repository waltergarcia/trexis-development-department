package net.trexis.software.development.department.service.mapper;

import static net.trexis.software.development.department.util.AppConstant.ROLE_DEVELOPER;

import java.util.List;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.repository.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "entity", target = "cost", qualifiedByName = "getCost")
    @Mapping(source = "entity", target = "role", qualifiedByName = "getRole")
    @Mapping(source = "entity", target = "level", qualifiedByName = "getLevel")
    EmployeeDto toEmployeeDto(final Employee entity);

    List<EmployeeDto> toEmployeeDtoList(final List<Employee> entities);

    @Named("getCost")
    default int getCost(final Employee employee) {
        if (!CollectionUtils.isEmpty(employee.getSubordinates())) {
            final int managerCost = employee.getCost().getCost();
            final int subordinatedCost = calculateSubordinatedCost(employee.getSubordinates());
            return managerCost + subordinatedCost;
        }

       return 0;
    }
    default int calculateSubordinatedCost(final List<Employee> subordinates) {
        int costSubordinated = 0;

        for (final Employee subordinate : subordinates) {
            final List<Employee> subordinateChildren = subordinate.getSubordinates();

            costSubordinated += subordinate.getCost().getCost();

            if (!CollectionUtils.isEmpty(subordinateChildren)) {
                costSubordinated += calculateSubordinatedCost(subordinateChildren);
            }
        }

       return costSubordinated ;
    }

    @Named("getRole")
    default String getRole(final Employee employee) {
        return employee.getRole().getRole();
    }

    @Named("getLevel")
    default String getLevel(final Employee employee) {
        if (ROLE_DEVELOPER.equals(employee.getRole().getRole())) {
            return employee.getLevel().getLevel();
        }

        return null;
    }

}
