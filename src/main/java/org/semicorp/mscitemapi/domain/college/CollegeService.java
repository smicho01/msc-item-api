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

    public CollegeResponse insert(College newCollege) {
        newCollege.setId(UUID.randomUUID().toString());
        // Check if college already exits and return it if so
        College foundCollege = jdbi.onDemand(CollegeDao.class).findByName(newCollege.getName());
        if(foundCollege !=null) {
            log.info("College already exists");
            return new CollegeResponse(foundCollege, ResponseCodes.ALREADY_EXISTS);
        }
        try {
            log.info("Attempt to insert college {} with new id: {}", newCollege.getName(), newCollege.getId());
            boolean insert = jdbi.onDemand(CollegeDao.class).insert(new CollegeRow(newCollege));
        } catch (Exception e) {
            log.error("Error while inserting new college {}" , newCollege.getName());
            log.error(e.getMessage());
            return new CollegeResponse(newCollege, ResponseCodes.FAIL);
        }
        return new CollegeResponse(newCollege, ResponseCodes.SUCCESS);
    }
}
