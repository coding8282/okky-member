package org.okky.member.domain.model;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.okky.share.execption.BadArgument;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.okky.share.domain.AssertionConcern.assertArgLength;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NickNameRule {
    static List<String> BAD_NICK_NAMES = asList("운영자,ADMIN,관리자,OKKY,ROOT".split(","));
    static int MIN = 2;
    static int MAX = 10;

    public static void rejectIfBadNickName(String nickName) {
        String trimmed = nickName.trim();
        rejectIfBadLength(trimmed);
        rejectIfContainsWhiteSpace(trimmed);
        rejectIfIllegalNickName(trimmed);
    }

    static void rejectIfBadLength(String nickName) {
        assertArgLength(nickName, MIN, MAX,
                format("별명은 %d~%d자까지 가능합니다: 현재 %,d자", MIN, MAX, nickName.length()));
    }

    static void rejectIfContainsWhiteSpace(String nickName) {
        if (StringUtils.containsWhitespace(nickName))
            throw new BadArgument(format("별명에는 공백문자를 포함할 수 없습니다: '%s'", nickName));
    }

    static void rejectIfIllegalNickName(String nickName) {
        boolean isBad = BAD_NICK_NAMES
                .stream()
                .anyMatch(badNickName -> containsIgnoreCase(nickName, badNickName));
        if (isBad)
            throw new BadArgument(format("해당 닉네임에는 금지된 단어('%s')가 포함되어 있습니다.", nickName));
    }
}
