package net.trexis.software.development.department.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.trexis.software.development.department.dto.AddEmployeeDto;
import net.trexis.software.development.department.dto.EmployeeDto;
import net.trexis.software.development.department.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@ContextConfiguration(classes = EmployeeController.class)
class EmployeeControllerTest extends AbstractTestController {

    @MockBean
    private EmployeeService service;

    private static final String URL = "/trexis/department/development/employee";

    @Test
    void givenNewEmployee_whenAdd_thenReturnSuccess() throws Exception {
        when(service.add(new AddEmployeeDto()))
                .thenReturn(new EmployeeDto());

        doGetRequest()
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private ResultActions doGetRequest() throws Exception {
        return getMockMvc()
                .perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(new AddEmployeeDto())));
    }
}
