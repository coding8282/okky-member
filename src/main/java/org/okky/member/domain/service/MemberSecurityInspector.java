package org.okky.member.domain.service;

import lombok.AllArgsConstructor;
import org.okky.member.resource.ContextHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberSecurityInspector {
    public boolean isMe(String memberId) {
        String requesterId = ContextHelper.getId();
        return requesterId.equals(memberId);
    }
}
