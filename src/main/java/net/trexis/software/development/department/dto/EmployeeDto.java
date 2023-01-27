package net.trexis.software.development.department.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int cost;

    @JsonProperty
    private String role;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String level;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EmployeeDto> subordinates;
}
