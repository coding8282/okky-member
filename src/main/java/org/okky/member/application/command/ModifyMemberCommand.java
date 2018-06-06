package org.okky.member.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModifyMemberCommand {
    String memberId;
    String email;
    String name;
    String nickName;
    String sex;
    String motto;
    String description;
}
