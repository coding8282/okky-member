package org.okky.member.resource;

import lombok.AllArgsConstructor;
import org.okky.member.application.MemberService;
import org.okky.member.application.command.DropMemberCommand;
import org.okky.member.application.command.JoinMemberCommand;
import org.okky.member.application.command.ModifyMemberCommand;
import org.okky.member.domain.model.EmailRule;
import org.okky.member.domain.model.NickNameRule;
import org.okky.member.domain.repository.MemberRepository;
import org.okky.member.domain.repository.dto.MemberDto;
import org.okky.share.execption.ResourceNotFound;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
class MemberResource {
    MemberService service;
    MemberRepository repository;
    ContextHolder holder;

    @GetMapping(value = "/members/exists", params = "email")
    boolean existsByEmail(@RequestParam String email) {
        EmailRule.rejectIfIllegalPattern(email);
        return repository.existsByEmail(email);
    }

    @GetMapping(value = "/members/exists", params = "nickName")
    boolean existsByNickName(@RequestParam String nickName) {
        NickNameRule.rejectIfBadNickName(nickName);
        return repository.existsByNickName(nickName);
    }

    @GetMapping(value = "/members/{memberId}/exists")
    boolean exists(@PathVariable String memberId) {
        return repository.existsById(memberId);
    }

    @GetMapping(value = "/members/{memberId}", produces = APPLICATION_JSON_VALUE)
    MemberDto get(@PathVariable String memberId) {
        return repository
                .findDtoById(memberId)
                .orElseThrow(ResourceNotFound::new);
    }

    @GetMapping(value = "/members/me", produces = APPLICATION_JSON_VALUE)
    MemberDto getMe() {
        return repository
                .findDtoById(holder.getId())
                .orElseThrow(ResourceNotFound::new);
    }

    @PostMapping(value = "/members", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    void join(@RequestBody JoinMemberCommand cmd) {
        service.join(cmd);
    }

    @PutMapping(value = "/members/me", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void modify(
            @RequestBody ModifyMemberCommand cmd) {
        cmd.setMemberId(holder.getId());
        service.modify(cmd);
    }

    @DeleteMapping(value = "/members/me/drop")
    @ResponseStatus(NO_CONTENT)
    void drop(
            @RequestBody DropMemberCommand cmd) {
        cmd.setMemberId(holder.getId());
        service.drop(cmd);
    }
}
