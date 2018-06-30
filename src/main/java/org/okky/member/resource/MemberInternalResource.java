package org.okky.member.resource;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.member.domain.repository.MemberRepository;
import org.okky.member.domain.repository.dto.MemberInterenalDto;
import org.okky.share.execption.ResourceNotFound;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/internal")
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class MemberInternalResource {
    MemberRepository repository;

    @GetMapping(value = "/members/{memberId}", produces = APPLICATION_JSON_VALUE)
    MemberInterenalDto get(@PathVariable String memberId) {
        return repository
                .findInternalDtoById(memberId)
                .orElseThrow(ResourceNotFound::new);
    }
}
