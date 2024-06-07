package org.semicorp.mscitemapi.domain.modules;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService collegeService) {
        this.moduleService = collegeService;
    }



    @GetMapping
    public ResponseEntity<List<Module>> getAllModules(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("Get all modules");
        List<Module> allModules = moduleService.getAllModules();
        return new ResponseEntity<>(allModules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Module>> getModuleWithNameLike(
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                            @PathVariable(value = "id") String id) {
        log.info("Get module by id {}", id);
        List<Module> allModules = moduleService.findById(id);
        return new ResponseEntity<>(allModules, HttpStatus.OK);
    }

    @GetMapping("/college/{id}")
    public ResponseEntity<List<Module>> getModulesByCollege(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable(value = "id") String collegeId) {
        log.info("Get modules by college id {}", collegeId);
        List<Module> allModules = moduleService.findModulesByCollege(collegeId);
        return new ResponseEntity<>(allModules, HttpStatus.OK);
    }


}
