package org.okky.member.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.member.TestMother;
import org.okky.member.application.command.DropMemberCommand;
import org.okky.member.application.command.JoinMemberCommand;
import org.okky.member.application.command.ModifyMemberCommand;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.repository.MemberRepository;
import org.okky.member.domain.service.MemberConstraint;
import org.okky.member.domain.service.MemberProxy;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.okky.member.domain.model.Sex.MALE;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class MemberServiceTest extends TestMother {
    @InjectMocks
    MemberService service;
    @Mock
    MemberRepository repository;
    @Mock
    MemberConstraint constraint;
    @Mock
    MemberProxy proxy;
    @Mock
    ModelMapper mapper;
    @Mock
    Member member;

    @Test
    public void join() {
        JoinMemberCommand cmd = new JoinMemberCommand("e", "p", "n", "nn", "s", "mt", "d");
        when(mapper.toModel(cmd)).thenReturn(member);
        when(proxy.signup("e", "p")).thenReturn("m-1");

        service.join(cmd);

        InOrder o = inOrder(constraint, mapper, proxy, repository, member);
        o.verify(constraint).rejectIfUnavailableEmail("e");
        o.verify(constraint).rejectIfUnavailableNickName("nn");
        o.verify(mapper).toModel(cmd);
        o.verify(proxy).signup(eq("e"), eq("p"));
        o.verify(member).assignId("m-1");
        o.verify(repository).save(member);
        o.verify(proxy).generateProfileImage("m-1");
    }

    @Test
    public void modify_같은_닉네임으로_바꾼_경우() {
        ModifyMemberCommand cmd = new ModifyMemberCommand("m-1", "e", "n", "nn", "MALE", "mt", "d");
        when(constraint.checkExistsAndGet("m-1")).thenReturn(member);
        when(member.isDifferentNickName("nn")).thenReturn(false);

        service.modify(cmd);

        InOrder o = inOrder(constraint, member);
        o.verify(constraint).checkExistsAndGet("m-1");
        o.verify(constraint, never()).rejectIfUnavailableNickName("nn");
        o.verify(member).modify("n", "nn", MALE, "mt", "d");
    }

    @Test
    public void modify_다른_닉네임으로_바꾼_경우() {
        ModifyMemberCommand cmd = new ModifyMemberCommand("m-1", "e", "n", "nn", "MALE", "mt", "d");
        when(constraint.checkExistsAndGet("m-1")).thenReturn(member);
        when(member.isDifferentNickName("nn")).thenReturn(true);

        service.modify(cmd);

        InOrder o = inOrder(constraint, member);
        o.verify(constraint).checkExistsAndGet("m-1");
        o.verify(constraint).rejectIfUnavailableNickName("nn");
        o.verify(member).modify("n", "nn", MALE, "mt", "d");
    }

    @Test
    public void drop() {
        DropMemberCommand cmd = new DropMemberCommand("m-1", "r");
        when(constraint.checkExistsAndGet("m-1")).thenReturn(member);
        when(member.getEmail()).thenReturn("e");

        service.drop(cmd);

        InOrder o = inOrder(constraint, proxy, member);
        o.verify(constraint).checkExistsAndGet("m-1");
        o.verify(member).drop("r");
        o.verify(proxy).disable("e");
        o.verify(proxy).deleteProfile("m-1");
    }
}