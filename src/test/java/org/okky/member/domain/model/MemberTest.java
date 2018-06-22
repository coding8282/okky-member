package org.okky.member.domain.model;

import org.junit.Test;
import org.okky.member.TestMother;
import org.okky.member.domain.exception.PaperingDetected;
import org.okky.share.execption.ModelConflicted;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.okky.member.domain.model.Sex.FEMALE;


public class MemberTest extends TestMother {
    @Test
    public void 일단_한_번_할당된_id를_수정하면_예외() {
        expect(ModelConflicted.class, "한번 할당된 id는 바꿀 수 없습니다.");

        Member member = fixture();
        member.assignId("m-9999");
    }

    @Test
    public void 처음_회원이_생성되었을_때는_blocked는_false_확인() {
        Member member = fixture();

        assertThat("처음 회원이 생성되었을 때는 block되지 않은 상태이다.", member.isBlocked(), is(false));
    }

    @Test
    public void toggleBlock_확인() {
        Member member = fixture();

        assertFalse("처음 회원이 생성되었을 때는 block되지 않은 상태이다.", member.isBlocked());

        member.toggleBlock();

        assertTrue("토글 후에는 block된 상태이다.", member.isBlocked());

        member.toggleBlock();

        assertFalse("다시 토글 후에는 block된 상태가 아니다.", member.isBlocked());
    }

    // --------------------- 아래는 다른 Bounded Context로 옮겨질 예정
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
        String email1 = "coding8282@gmail.com";
        String name1 = "현수";
        String nickName1 = "coding8282";
        Sex sex1 = FEMALE;
        String motto1 = "열심히 노력하자.";
        String description1 = "안녕하세요, 나의 자기소개입니다.";
        Member member = new Member(email1, name1, nickName1, sex1, motto1, description1);
        member.assignId("m-1234");
        return member;
    }
}