package net.trexis.software.development.department.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public class TrexisInfoDto {

    @JsonProperty
    private List<String> summary;

    @JsonProperty
    private DetailDto detail;
}
