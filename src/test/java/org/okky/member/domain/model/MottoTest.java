package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

public class MottoTest extends TestMother {
    @Test
    public void 좌우명이_101자이면_예외() {
        expect(BadArgument.class, "좌우명은 최대 100자까지 가능합니다.");

        new Motto("공부가 인생의 전부는 아니다. 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복1234567891");
    }

    @Test
    public void 좌우명이_100자이면_성공() {
        new Motto("공부가 인생의 전부는 아니다. 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복123456789");
    }
}