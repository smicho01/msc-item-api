package org.semicorp.mscitemapi.domain.tag.dao.tagquestion;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TagQuestionDAO {

    @SqlUpdate(QueryTagQuestion.QUERY_INSERT)
    boolean insert(@BindBean final TagQuestionRow tagQuestionRow);
}
