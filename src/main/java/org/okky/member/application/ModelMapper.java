package org.okky.member.application;

import org.okky.member.application.command.JoinMemberCommand;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.model.Sex;

public interface ModelMapper {
    static Member toMember(JoinMemberCommand cmd) {
        return new Member(
                cmd.getEmail(),
                cmd.getName(),
                cmd.getNickName(),
                Sex.parse(cmd.getSex()),
                cmd.getMotto(),
                cmd.getDescription());
    }
}
