package org.okky.member.domain.repository;

import lombok.experimental.FieldDefaults;
import org.junit.Ignore;
import org.junit.Test;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.model.Sex;
import org.okky.member.domain.repository.dto.MemberDto;
import org.okky.member.domain.repository.dto.MemberInterenalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FieldDefaults(level = PRIVATE)
public class MemberRepositoryTest extends RepositoryTestMother {
    @Autowired
    MemberRepository repository;

    // TODO: 2018. 6. 20. saveAndFlush()를 해도 insert를 1번만 호출한다.
    @Ignore
    @Test
    public void email_유니크_제약조건_확인() {
        expect(DataIntegrityViolationException.class);

        repository.saveAndFlush(fixture());
        repository.saveAndFlush(fixture());
    }

    // TODO: 2018. 6. 20. saveAndFlush()를 해도 insert를 1번만 호출한다.
    @Ignore
    @Test
    public void nickName_유니크_제약조건_확인() {
        expect(DataIntegrityViolationException.class);

        repository.saveAndFlush(fixture());
        repository.saveAndFlush(fixture());
    }

    @Test
    public void findDtoById() {
        Member member = fixture();
        repository.save(member);
        MemberDto dto = repository.findDtoById(member.getId()).orElse(null);

        assertThat("id가 다르다.", dto.getId(), is(member.getId()));
        assertThat("type이 다르다.", dto.getType(), is(member.isAdmin() ? "admin" : "user"));
        assertThat("email이 다르다.", dto.getEmail(), is(member.getEmail()));
        assertThat("name이 다르다.", dto.getName(), is(member.getName()));
        assertThat("nickName이 다르다.", dto.getNickName(), is(member.getNickName()));
        assertThat("sex가 다르다.", dto.getSex(), is(member.getSex().name()));
        assertThat("motto가 다르다.", dto.getMotto(), is(member.getMotto()));
        assertThat("description가 다르다.", dto.getDescription(), is(member.getDescription()));
        assertThat("blocked가 다르다.", dto.isBlocked(), is(member.isBlocked()));
    }

    @Test
    public void findInternalDtoById() {
        Member member = fixture();
        repository.save(member);
        MemberInterenalDto dto = repository.findInternalDtoById(member.getId()).orElse(null);

        assertThat("id가 다르다.", dto.getId(), is(member.getId()));
        assertThat("type이 다르다.", dto.getType(), is(member.isAdmin() ? "admin" : "user"));
        assertThat("nickName이 다르다.", dto.getNickName(), is(member.getNickName()));
        assertThat("motto가 다르다.", dto.getMotto(), is(member.getMotto()));
        assertThat("description가 다르다.", dto.getDescription(), is(member.getDescription()));
        assertThat("blocked가 다르다.", dto.isBlocked(), is(member.isBlocked()));
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