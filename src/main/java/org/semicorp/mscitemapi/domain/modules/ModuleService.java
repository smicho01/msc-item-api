package org.semicorp.mscitemapi.domain.modules;

import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.college.dao.CollegeDao;
import org.semicorp.mscitemapi.domain.modules.dao.ModuleDao;
import org.semicorp.mscitemapi.response.CollegeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final Jdbi jdbi;

    public ModuleService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public CollegeResponse insert(Module newCollege) {
        // TODO
       return  null;
    }

    public List<Module> getAllModules() {
        List<Module> allModules = jdbi.onDemand(ModuleDao.class).findAll();
        return allModules;
    }

    public List<Module> findById(String id) {
        List<Module> allModules = jdbi.onDemand(ModuleDao.class).findById(id);
        return allModules;
    }
}
