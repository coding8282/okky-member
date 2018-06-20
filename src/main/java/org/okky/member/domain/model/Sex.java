package org.okky.member.domain.model;

import org.okky.share.execption.BadArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.okky.share.domain.AssertionConcern.assertArgNotEmpty;

public enum Sex {
    FEMALE,
    MALE,;

    public static Sex parse(String value) {
        try {
            assertArgNotEmpty(value, "성별은 필수입니다.");
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            String possibleValues = String.join(",", Arrays
                    .stream(values())
                    .map(Sex::name)
                    .collect(Collectors.toList()));
            throw new BadArgument(format("'%s'는 지원하지 않는 성별입니다. '%s'만 가능합니다.", value, possibleValues));
        }
    }
}
