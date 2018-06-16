package org.okky.member.domain.repository;

import org.okky.member.domain.model.Motto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Motto.class, idClass = Long.class)
public interface MottoRepository {
    void save(Motto motto);
    @Query(value = "SELECT " +
            "M.SENTENCE " +
            "FROM motto M " +
            "ORDER BY RAND() " +
            "LIMIT :limit", nativeQuery = true)
    List<String> findRandomly(@Param("limit") int limit);
}
