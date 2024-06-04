package org.semicorp.mscitemapi.domain.college;

import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.college.dao.CollegeDao;
import org.semicorp.mscitemapi.domain.college.dao.CollegeRow;
import org.semicorp.mscitemapi.response.BasicResponse;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.semicorp.mscitemapi.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CollegeService {

    private final Jdbi jdbi;

    public CollegeService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public CollegeResponse insert(College newCollege) {
        newCollege.setId(UUID.randomUUID().toString());
        try {
            boolean insert = jdbi.onDemand(CollegeDao.class).insert(new CollegeRow(newCollege));
        } catch (Exception e) {
            if(e.toString().contains("duplicate key value violates unique constraint")) {

                College duplocatedCollege  = new College(
                        getValuesFromErrorMessage(e.getMessage(), "id"),
                        getValuesFromErrorMessage(e.getMessage(), "name")
                        );
                return new CollegeResponse(duplocatedCollege, ResponseCodes.ALREADY_EXISTS);
            } else {
                return new CollegeResponse(null, ResponseCodes.FAIL);
            }
        }
        return new CollegeResponse(newCollege, ResponseCodes.SUCCESS);
    }


    /* Used to extract duplicated college id and name */
    public String getValuesFromErrorMessage(String message, String value){
        Pattern pattern = Pattern.compile("CollegeRow\\(id=(.*?), name=(.*?)\\)");
        Matcher matcher = pattern.matcher(message);
        String id = "";
        String name = "";
        if (matcher.find()) {
            id = matcher.group(1);
            name = matcher.group(2);
        }

        return switch (value) {
            case "id" -> id;
            case "name" -> name;
            default -> "";
        };
    }
}
