package org.semicorp.mscitemapi.domain.college;

import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.college.dao.CollegeDao;
import org.semicorp.mscitemapi.domain.college.dao.CollegeRow;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.semicorp.mscitemapi.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollegeService {

    private final Jdbi jdbi;

    public CollegeService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public TextResponse insert(College newCollege) {
        newCollege.setId(UUID.randomUUID().toString());
        try {
            boolean insert = jdbi.onDemand(CollegeDao.class).insert(new CollegeRow(newCollege));
        } catch (Exception e) {
            if(e.toString().contains("duplicate key value violates unique constraint")) {
                return new TextResponse("Already exists", ResponseCodes.ALREADY_EXISTS);
            } else {
                return new TextResponse("Unknown error", ResponseCodes.FAIL);
            }
        }
        return new TextResponse("Created", ResponseCodes.SUCCESS);
    }
}
