package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.share.execption.BadArgument;

import static org.junit.Assert.assertEquals;
import static org.okky.member.domain.model.Sex.FEMALE;
import static org.okky.member.domain.model.Sex.MALE;

public class SexTest extends TestMother {
    @Test
    public void parse_성별이_null이면_예외() {
        expect(BadArgument.class, "성별은 필수입니다.");

        Sex.parse(null);
    }

    @Test
    public void parse_성별이_빈문자열이면_예외() {
        expect(BadArgument.class, "성별은 필수입니다.");

        Sex.parse("");
    }

    @Test
    public void parse_HOMO는_지원하지_않는_성별() {
        expect(BadArgument.class, "'HOMO'는 지원하지 않는 성별입니다. 'FEMALE,MALE'만 가능합니다.");

        Sex.parse("HOMO");
    }

    @Test
    public void parse_소문자를_줘도_분석_성공() {
        Sex sex = Sex.parse("male");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", sex, MALE);
    }

    @Test
    public void parse_대소문자를_섞어서_줘도_분석_성공() {
        Sex sex = Sex.parse("feMALE");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", sex, FEMALE);
    }
}