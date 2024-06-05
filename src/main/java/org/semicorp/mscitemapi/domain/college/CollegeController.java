package org.semicorp.mscitemapi.domain.college;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/college")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }


    @PostMapping
    public ResponseEntity addCollege(
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                            @RequestBody College college){
        log.info("Adding new college: " + college.getName());
        CollegeResponse insertResponse = collegeService.insert(college);
        switch (insertResponse.getCode()) {
            case (ResponseCodes.SUCCESS) :
                log.info("College added: " + college.getName());
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.CREATED);
            case (ResponseCodes.ALREADY_EXISTS):
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.CREATED);
            default:
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.BAD_REQUEST);
        }
    }
}
