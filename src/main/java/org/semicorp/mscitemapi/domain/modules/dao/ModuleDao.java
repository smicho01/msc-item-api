package org.semicorp.mscitemapi.domain.modules.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.modules.Module;

import java.util.List;

public interface ModuleDao {

    @SqlUpdate(QueryModule.QUERY_INSERT_MODULE)
    boolean insert(@BindBean final ModuleRow collegeRow);

    @RegisterRowMapper(ModuleRowMapper.class)
    @SqlQuery(QueryModule.QUERY_FIND_ALL)
    List<Module> findAll();

    @RegisterRowMapper(ModuleRowMapper.class)
    @SqlQuery(QueryModule.QUERY_FIND_MODULE_BY_ID)
    List<Module> findById(@Bind("id") String id);
}
