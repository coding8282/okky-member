package org.okky.member.domain.repository;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.model.Sex;
import org.okky.member.domain.repository.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FieldDefaults(level = PRIVATE)
public class MemberRepositoryTest extends RepositoryTestMother {
    @Autowired
    MemberRepository repository;

    @Test
    public void findDtoById() {
        Member member = fixture();
        repository.save(member);
        MemberDto dto = repository.findDtoById(member.getId()).orElse(null);

        assertThat(dto.getId(), is(member.getId()));
    }

    // -----------------------------
    private Member fixture() {
        String email = "coding8282@gmail.com";
        String name = "현수";
        String nickName = "coding8282";
        Sex sex = Sex.FEMALE;
        String motto = "열심히 노력하자.";
        String description = "안녕하세요, 나의 자기소개입니다.";
        Member member = new Member(email, name, nickName, sex, motto, description);
        member.assignId("m-1234");
        return member;
    }
}