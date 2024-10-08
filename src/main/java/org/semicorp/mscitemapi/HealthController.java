package org.semicorp.mscitemapi;


import org.semicorp.mscitemapi.response.BasicResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.semicorp.mscitemapi.response.TextResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/healthcheck")
    public ResponseEntity<BasicResponse> healthcheck() {
        return new ResponseEntity<>(new TextResponse("OK", ResponseCodes.SUCCESS), HttpStatus.OK);
    }
}
