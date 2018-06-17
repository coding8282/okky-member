package org.okky.member.domain.service;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.member.TestMother;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.repository.MemberRepository;
import org.okky.share.execption.ModelConflicted;
import org.okky.share.execption.ModelNotExists;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class MemberConstraintTest extends TestMother {
    @InjectMocks
    MemberConstraint constraint;
    @Mock
    MemberRepository repository;
    @Mock
    Member member;

    @Test
    public void checkExistsAndGet_해당_id가_없는_경우() {
        expect(ModelNotExists.class, "해당 회원은 존재하지 않습니다: 'm-1'");

        constraint.checkExistsAndGet("m-1");
    }

    @Test
    public void checkExistsAndGet_해당_id가_있는_경우() {
        when(repository.findById("m-1")).thenReturn(Optional.of(member));

        Member found = constraint.checkExistsAndGet("m-1");

        assertEquals("아이디로 m-1으로 같은 사용자여야 한다.", member, found);
    }

    @Test
    public void rejectIfUnavailableEmail_해당_이메일을_이미_사용_중인_경우() {
        expect(ModelConflicted.class, "해당 이메일은 다른 사용자가 이미 사용 중이거나 탈퇴한 사용자입니다: 'coding8282@gmail.com'");

        when(repository.findByEmail("coding8282@gmail.com")).thenReturn(Optional.of(member));

        constraint.rejectIfUnavailableEmail("coding8282@gmail.com");
    }

    @Test
    public void rejectIfUnavailableEmail_해당_이메일을_사용_가능한_경우() {
        constraint.rejectIfUnavailableEmail("coding8282@gmail.com");

        InOrder o = inOrder(repository);
        o.verify(repository).findByEmail("coding8282@gmail.com");
    }

    @Test
    public void rejectIfUnavailableNickName_해당_닉네임이_이미_사용_중인_경우() {
        expect(ModelConflicted.class, "해당 닉네임은 다른 사용자가 이미 사용 중입니다: 'coding8282'");

        when(repository.existsByNickName("coding8282")).thenReturn(true);

        constraint.rejectIfUnavailableNickName("coding8282");
    }

    @Test
    public void rejectIfUnavailableNickName_해당_닉네임이_이미_사용_가능한_경우() {
        when(repository.existsByNickName("coding8282")).thenReturn(false);

        constraint.rejectIfUnavailableNickName("coding8282");

        InOrder o = inOrder(repository);
        o.verify(repository).existsByNickName("coding8282");
    }
}