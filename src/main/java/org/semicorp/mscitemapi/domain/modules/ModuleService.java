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

    public CollegeResponse insert(Module newModule) {
        // TODO
       return  null;
    }

    public List<Module> getAllModules() {
        return jdbi.onDemand(ModuleDao.class).findAll();
    }

    public List<Module> findById(String id) {
        return jdbi.onDemand(ModuleDao.class).findById(id);
    }

    public List<Module> findModulesByCollege(String collegeId) {
        return jdbi.onDemand(ModuleDao.class).findModulesByCollegeId(collegeId);
    }
}
