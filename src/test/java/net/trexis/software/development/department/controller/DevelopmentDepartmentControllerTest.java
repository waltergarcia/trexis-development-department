package net.trexis.software.development.department.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.trexis.software.development.department.dto.TrexisInfoDto;
import net.trexis.software.development.department.service.DevelopmentDepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@ContextConfiguration(classes = DevelopmentDepartmentController.class)
class DevelopmentDepartmentControllerTest extends AbstractTestController {

    @MockBean
    private DevelopmentDepartmentService service;

    private static final String URL = "/trexis/department/development/info";

    @Test
    void whenRequestInfo_thenReturnSuccess() throws Exception {
        when(service.getInfo())
                .thenReturn(TrexisInfoDto.builder().build());

        doGetRequest()
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test()
    void whenRequestInfo_thenReturnError() throws Exception {
        doThrow(RuntimeException.class)
                .when(service).getInfo();

        doGetRequest()
                .andExpect(status().isInternalServerError());
    }

    private ResultActions doGetRequest() throws Exception {
        return getMockMvc()
                .perform(get(URL));
    }
}
