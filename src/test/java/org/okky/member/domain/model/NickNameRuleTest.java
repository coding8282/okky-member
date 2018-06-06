package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

public class NickNameRuleTest extends TestMother {
    @Test
    public void 별명이_1글자인_경우_예외() {
        expect(BadArgument.class);

        NickNameRule.rejectIfBadLength("한");
    }

    @Test
    public void 별명이_13글자인_경우_예외() {
        expect(BadArgument.class);

        NickNameRule.rejectIfBadLength("한글_별명_7890123");
    }

    @Test
    public void 공백을_포함하면_예외() {
        expect(BadArgument.class);

        NickNameRule.rejectIfContainsWhiteSpace("공백\t닉네임");
    }

    @Test
    public void 관리자2는_금지된_단어를_포함했으므_예외() {
        expect(BadArgument.class);

        NickNameRule.rejectIfIllegalNickName("관리자2");
    }

    @Test
    public void oKkY는_대소문자에_상관없이_예외() {
        expect(BadArgument.class);

        NickNameRule.rejectIfIllegalNickName("oKkY");
    }
}