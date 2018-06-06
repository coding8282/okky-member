package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.member.domain.exception.PaperingDetected;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.okky.member.domain.model.Sex.FEMALE;
import static org.okky.member.domain.model.Sex.MALE;


public class MemberTest extends TestMother {
    @Test
    public void 너무_빨리_게시글을_쓰면_도배_예외() {
        expect(PaperingDetected.class);

        Member member = fixture();
        member.renewArticleLog("a-dummy", currentTimeMillis());
        member.checkArticlePapering();
    }

    @Test
    public void 너무_빨리_답글을_쓰면_도배_예외() {
        expect(PaperingDetected.class);

        Member member = fixture();
        member.renewReplyLog("r-dummy", currentTimeMillis());
        member.checkReplyPapering();
    }

    @Test
    public void 최초에는_항상_게시글_작성_가능() {
        Member member = fixture();
        member.checkArticlePapering();
    }

    @Test
    public void 정보_수정_확인() {
        Member member = fixture();
        member.modify("현수2", "coding8181", FEMALE, "열심히 합시당222", "수정된 자기소개111");

        assertThat(member.getName(), is("현수2"));
        assertThat(member.getNickName(), is("coding8181"));
        assertThat(member.getSex(), is(FEMALE));
        assertThat(member.getMotto(), is("열심히 합시당222"));
        assertThat(member.getDescription(), is("수정된 자기소개111"));
    }

    // ------------------------------------------------------
    private Member fixture() {
        return new Member("coding8282@gmail.com", "현수", "coding8282", MALE, "열심히 노력하자.", "나의 자기 소개서입니다.");
    }
}