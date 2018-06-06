package org.okky.member.domain.repository.dto;

import lombok.Getter;
import org.okky.member.domain.model.Member;

@Getter
public class MemberDto {
    String id;
    String type;
    String email;
    String name;
    String nickName;
    String sex;
    String motto;
    String description;
    String lastWroteArticleId;
    String lastWroteReplyId;
    Long joinedOn;
    Long lastArticleWroteOn;
    Long lastReplyWroteOn;

    public MemberDto(Member m) {
        this.id = m.getId();
        this.type = m.isAdmin() ? "admin" : "user";
        this.email = m.getEmail();
        this.name = m.getName();
        this.nickName = m.getNickName();
        this.sex = m.getSex().name();
        this.motto = m.getMotto();
        this.description = m.getDescription();
        this.lastWroteArticleId = m.getLastLog().getLastWroteArticleId();
        this.lastWroteReplyId = m.getLastLog().getLastWroteReplyId();
        this.joinedOn = m.getJoinedOn();
        this.lastArticleWroteOn = m.getLastLog().getLastArticleWroteOn();
        this.lastReplyWroteOn = m.getLastLog().getLastReplyWroteOn();
    }
}
