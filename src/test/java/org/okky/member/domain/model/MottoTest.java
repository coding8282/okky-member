package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MottoTest extends TestMother {
    @Test
    public void 좌우명은_필수() {
        expect(BadArgument.class, "좌우명은 필수입니다.");

        new Motto(null);
    }

    @Test
    public void 좌우명이_0자이면_예외() {
        expect(BadArgument.class, "좌우명은 1~200자까지 가능합니다.");

        new Motto("");
    }

    @Test
    public void 좌우명이_201자이면_예외() {
        expect(BadArgument.class, "좌우명은 1~200자까지 가능합니다.");

        new Motto("공부가 인생의 전부는 아니다. 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복12");
    }

    @Test
    public void 좌우명이_200자이면_성공() {
        new Motto("공부가 인생의 전부는 아니다. 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복 그러나 인생의 전부도 아닌 공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복123456789공부 하나도 정복1");
    }

    @Test
    public void 좌우명은_trim_확인() {
        Motto motto = new Motto(" 나의 좌우명은 12345   \t");

        assertThat("양옆의 공백이 모두 제거되어야 한다.", motto.getSentence(), is("나의 좌우명은 12345"));
    }
}