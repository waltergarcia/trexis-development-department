package net.trexis.software.development.department.dto;

import static net.trexis.software.development.department.util.AppConstant.PROP_DETAIL_EMPLOYEES;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public class DetailDto {

    @JsonProperty(PROP_DETAIL_EMPLOYEES)
    private List<EmployeeDto> employeeDto;
}
