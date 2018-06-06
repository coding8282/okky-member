package org.okky.member.domain.service;

import lombok.AllArgsConstructor;
import org.okky.member.domain.model.Member;
import org.okky.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.okky.share.execption.ModelConflicted;
import org.okky.share.execption.ModelNotExists;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class MemberConstraint {
    private MemberRepository memberRepository;

    public Member checkExistsAndGet(String memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null)
            throw new ModelNotExists(format("해당 회원은 존재하지 않습니다: '%s'", memberId));
        return member;
    }

    public void checkAvailableEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member != null)
            throw new ModelConflicted(format("해당 이메일은 다른 사용자가 이미 사용 중이거나 탈퇴한 사용자입니다: '%s'", email));
    }

    public void checkAvailableNickName(String nickName) {
        if (memberRepository.existsByNickName(nickName))
            throw new ModelConflicted(format("해당 닉네임은 다른 사용자가 이미 사용 중입니다: '%s'", nickName));
    }
}
