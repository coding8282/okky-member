package org.okky.member.resource;

import lombok.AllArgsConstructor;
import org.okky.member.domain.repository.MottoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
class MottoResource {
    MottoRepository repository;

    @GetMapping(value = "/members/mottos", produces = APPLICATION_JSON_VALUE)
    List<String> getMottos(@RequestParam(defaultValue = "7") int size) {
        return repository.findRandomly(size);
    }
}
