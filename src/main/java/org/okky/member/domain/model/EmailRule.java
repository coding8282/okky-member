package org.okky.member.domain.model;

import lombok.NoArgsConstructor;
import org.okky.share.execption.BadArgument;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class EmailRule {
    private static final String PATTERN = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

    public static void rejectIfIllegalPattern(String email) {
        if (!email.matches(PATTERN))
            throw new BadArgument(
                    format("잘못된 이메일 형식입니다. 이메일은 'coding8282@gmail.com'처럼 @와 이메일서비스공급자명이 반드시 포함되어야 합니다: '%s'", email));
    }
}
