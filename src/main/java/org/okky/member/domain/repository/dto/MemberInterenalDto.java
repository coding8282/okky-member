package org.okky.member.domain.repository.dto;

import lombok.Getter;
import org.okky.member.domain.model.Member;

@Getter
public class MemberInterenalDto {
    String id;
    String type;
    String nickName;
    String motto;
    String description;
    boolean blocked;
    Long joinedOn;

    public MemberInterenalDto(Member m) {
        this.id = m.getId();
        this.type = m.isAdmin() ? "admin" : "user";
        this.nickName = m.getNickName();
        this.motto = m.getMotto();
        this.description = m.getDescription();
        this.blocked = m.isBlocked();
        this.joinedOn = m.getJoinedOn();
    }
}
