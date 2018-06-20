package org.okky.member.domain.service;

import lombok.AllArgsConstructor;
import org.okky.member.resource.ContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberSecurityInspector {
    ContextHolder holder;

    public boolean isMe(String memberId) {
        String requesterId = holder.getId();
        return requesterId.equals(memberId);
    }
}
