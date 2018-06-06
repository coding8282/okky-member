package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

public class EmailRuleTest extends TestMother {
    @Test
    public void 골뱅이가_없으면_예외() {
        expect(BadArgument.class);

        EmailRule.rejectIfIllegalPattern("coding8282_gmail.com");
    }

    @Test
    public void 이메일서비스공급자명에_마침표가_없으면_예외() {
        expect(BadArgument.class);

        EmailRule.rejectIfIllegalPattern("coding8282@gmail,com");
    }

    @Test
    public void 아이디가_없으면_예외() {
        expect(BadArgument.class);

        EmailRule.rejectIfIllegalPattern("@gmail.com");
    }

    @Test
    public void 이메일서비스공급자명이_없으면_예외() {
        expect(BadArgument.class);

        EmailRule.rejectIfIllegalPattern("coding8282@");
    }

    @Test
    public void 이메일서비스공급자명에_마침표가_여러개라도_성공() {
        EmailRule.rejectIfIllegalPattern("coding8282@gmail.api.co.kr");
    }
}
