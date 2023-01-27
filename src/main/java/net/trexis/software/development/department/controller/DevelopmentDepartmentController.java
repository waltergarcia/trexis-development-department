package net.trexis.software.development.department.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.trexis.software.development.department.dto.TrexisInfoDto;
import net.trexis.software.development.department.service.DevelopmentDepartmentService;
import net.trexis.software.development.department.util.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/trexis/department/development")
public class DevelopmentDepartmentController {

    private final DevelopmentDepartmentService service;

    @GetMapping(value = "/info")
    public ResponseEntity<TrexisInfoDto> getInfo() {

        try {
            return new ResponseEntity<>(service.getInfo(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(AppConstant.ERROR_GETTING_DATA_MSG, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
