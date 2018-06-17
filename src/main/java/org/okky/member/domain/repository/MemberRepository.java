package org.okky.member.domain.repository;

import org.okky.member.domain.model.Member;
import org.okky.member.domain.repository.dto.MemberDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = Member.class, idClass = String.class)
public interface MemberRepository extends RevisionRepository<Member, String, Long> {
    void save(Member member);
    boolean existsById(String id);
    boolean existsByNickName(String nickName);
    boolean existsByEmail(String email);
    Optional<Member> findById(String id);
    Optional<Member> findByEmail(String email);

    @Query("select " +
            "new org.okky.member.domain.repository.dto.MemberDto(m) " +
            "from Member m " +
            "where m.id=:id ")
    Optional<MemberDto> findDtoById(@Param("id") String id);
}
