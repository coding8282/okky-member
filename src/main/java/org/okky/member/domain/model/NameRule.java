package org.okky.member.domain.model;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.domain.AssertionConcern.assertArgLength;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class NameRule {
    static int MIN = 2;
    static int MAX = 10;

    static void rejectIfBadLength(String name) {
        assertArgLength(name, MIN, MAX,
                format("이름은 %d~%d자까지 가능합니다: 현재 %,d자", MIN, MAX, name.length()));
    }
}
