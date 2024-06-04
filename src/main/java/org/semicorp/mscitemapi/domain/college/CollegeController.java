package org.semicorp.mscitemapi.domain.college;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.semicorp.mscitemapi.response.TextResponse;
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

        TextResponse insertResponse = collegeService.insert(college);
        switch (insertResponse.getCode()) {
            case (ResponseCodes.SUCCESS) :
                log.info("Added new college: " + college.getName());
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.CREATED);
            case (ResponseCodes.ALREADY_EXISTS):
                log.info("College exists: " + college.getName());
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.CONFLICT);
            default:
                return new ResponseEntity(insertResponse.getResponse(), HttpStatus.BAD_REQUEST);
        }

    }
}
