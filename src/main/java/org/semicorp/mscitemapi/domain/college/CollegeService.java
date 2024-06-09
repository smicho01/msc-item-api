package org.semicorp.mscitemapi.domain.college;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.college.dao.CollegeDao;
import org.semicorp.mscitemapi.domain.college.dao.CollegeRow;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CollegeService {

    private final Jdbi jdbi;

    public CollegeService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public CollegeResponse insert(College college) {
        college.setId(UUID.randomUUID().toString());
        // Check if college already exits and return it if so
        College foundCollege = jdbi.onDemand(CollegeDao.class).findByName(college.getName());
        if(foundCollege !=null) {
            log.info("College already exists");
            return new CollegeResponse(foundCollege, ResponseCodes.ALREADY_EXISTS);
        }
        try {
            log.info("Attempt add college {} with new id: {}", college.getName(), college.getId());
            boolean insert = jdbi.onDemand(CollegeDao.class).insert(new CollegeRow(college));
            if(!insert) {
                log.info("College not added. Id: {}", college.getId());
                return new CollegeResponse(college, ResponseCodes.FAIL);
            }
        } catch (Exception e) {
            log.error("Error while inserting new college {}" , college.getName());
            log.error(e.getMessage());
            return new CollegeResponse(college, ResponseCodes.FAIL);
        }
        log.info("College added. Id: {}", college.getId());
        return new CollegeResponse(college, ResponseCodes.SUCCESS);
    }
}
