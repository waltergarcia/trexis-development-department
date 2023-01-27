package net.trexis.software.development.department.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
public class AddEmployeeDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    private String role;

    @JsonProperty
    private String level;

    @JsonProperty
    private String manager;
}
