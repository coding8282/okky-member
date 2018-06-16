package org.okky.member.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.member.application.command.DropMemberCommand;
import org.okky.member.application.command.JoinMemberCommand;
import org.okky.member.application.command.ModifyMemberCommand;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.model.Sex;
import org.okky.member.domain.repository.MemberRepository;
import org.okky.member.domain.service.MemberConstraint;
import org.okky.member.domain.service.MemberProxy;
import org.okky.member.util.ProfileImageUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MemberService {
    MemberRepository repository;
    MemberConstraint constraint;
    MemberProxy proxy;

    public void join(JoinMemberCommand cmd) {
        constraint.checkAvailableEmail(cmd.getEmail());
        constraint.checkAvailableNickName(cmd.getNickName());
        Member member = new Member(
                cmd.getEmail(),
                cmd.getName(),
                cmd.getNickName(),
                Sex.parse(cmd.getSex()),
                cmd.getMotto(),
                cmd.getDescription());
        String id = proxy.signup(cmd.getEmail(), cmd.getPassword());
        member.assignId(id);
        repository.save(member);
        File imageFile = ProfileImageUtil.generageRandomProfileImage();
        proxy.uploadProfileImage(id, imageFile);
    }

    @PreAuthorize("@memberSecurityInspector.isMe(#cmd.memberId)")
    public void modify(ModifyMemberCommand cmd) {
        String nickName = cmd.getNickName();
        Sex sex = Sex.parse(cmd.getSex());
        Member member = constraint.checkExistsAndGet(cmd.getMemberId());
        if (member.isDifferentNickName(nickName))
            constraint.checkAvailableNickName(nickName);
        member.modify(cmd.getName(), nickName, sex, cmd.getMotto(), cmd.getDescription());
    }

    @PreAuthorize("@memberSecurityInspector.isMe(#cmd.memberId)")
    public void drop(DropMemberCommand cmd) {
        String memberId = cmd.getMemberId();
        Member member = constraint.checkExistsAndGet(memberId);
        member.drop(cmd.getDropReason());
        proxy.disable(member.getEmail());
        proxy.deleteProfile(memberId);
    }
}
