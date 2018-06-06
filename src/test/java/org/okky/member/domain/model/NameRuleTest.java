package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

public class NameRuleTest extends TestMother {
    @Test
    public void 이름이_1글자인_경우_예외() {
        expect(BadArgument.class);

        NameRule.rejectIfBadLength("한");
    }

    @Test
    public void 이름이_11글자인_경우_예외() {
        expect(BadArgument.class);

        NameRule.rejectIfBadLength("이름 45678901");
    }
}