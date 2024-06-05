package org.semicorp.mscitemapi.domain.modules;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService collegeService) {
        this.moduleService = collegeService;
    }

}
