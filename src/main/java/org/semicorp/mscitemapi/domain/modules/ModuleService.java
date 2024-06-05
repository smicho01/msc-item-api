package org.semicorp.mscitemapi.domain.modules;

import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.modules.dao.CollegeDao;
import org.semicorp.mscitemapi.domain.modules.dao.CollegeRow;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.semicorp.mscitemapi.response.ResponseCodes;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ModuleService {

    private final Jdbi jdbi;

    public ModuleService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public CollegeResponse insert(Module newCollege) {
       return  null;
    }

}
