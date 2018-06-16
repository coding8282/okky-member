package org.okky.member.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.okky.member.domain.exception.PaperingDetected;
import org.okky.member.util.DateUtil;
import org.okky.share.domain.Aggregate;
import org.okky.share.execption.ModelConflicted;
import org.okky.share.util.JsonUtil;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.okky.share.domain.AssertionConcern.assertArgLength;
import static org.okky.share.domain.AssertionConcern.assertArgNotNull;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@FieldDefaults(level = PRIVATE)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MEMBER",
        indexes = {
                @Index(name = "I_EMAIL", columnList = "EMAIL", unique = true),
                @Index(name = "I_NICK_NAME", columnList = "NICK_NAME", unique = true),
        }
)
public class Member implements Aggregate {
    @Id
    @Column(length = 50)
    String id;

    @Column(name = "EMAIL", nullable = false, length = 30)
    @Audited
    String email;

    @Column(nullable = false, length = 50)
    @Audited
    String name;

    @Column(name = "NICK_NAME", nullable = false, length = 50)
    @Audited
    String nickName;

    @Column(nullable = false, length = 7)
    @Enumerated(STRING)
    Sex sex;

    @Column(length = 200)
    @Audited
    String motto;

    @Column(length = 200)
    String description;

    @Column(nullable = false, updatable = false, length = 1)
    @Type(type = "true_false")
    boolean admin;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    long joinedOn;

    @Embedded
    @Column(nullable = false)
    LastLog lastLog;

    @Embedded
    @Column
    DropDetail dropDetail;

    public Member(String email, String name, String nickName, Sex sex, String motto, String description) {
        setEmail(email);
        setName(name);
        setNickName(nickName);
        setSex(sex);
        setMotto(motto);
        setDescription(description);
        setAdmin(false);
        setLastLog(new LastLog());
    }

    public static void main(String[] args) {
        String email1 = "coding8282@gmail.com";
        String name1 = "현수";
        String nickName1 = "coding8282";
        Sex sex1 = Sex.FEMALE;
        String motto1 = "열심히 노력하자.";
        String description1 = "안녕하세요, 나의 자기소개입니다.";
        Member member = new Member(email1, name1, nickName1, sex1, motto1, description1);
        member.assignId("m-1234");
        System.out.println(JsonUtil.toPrettyJson(member));
    }

    public boolean isDifferentNickName(String nickName) {
        return !this.nickName.equals(nickName);
    }

    public void assignId(String id) {
        setId(id);
    }

    public void modify(String name, String nickName, Sex sex, String motto, String description) {
        rejectIfDropped();
        setName(name);
        setNickName(nickName);
        setSex(sex);
        setMotto(motto);
        setDescription(description);
    }

    public void renewArticleLog(String articleId, long wroteOn) {
        rejectIfDropped();
        LastLog newLastLog = lastLog.withChangedArticle(articleId, wroteOn);
        setLastLog(newLastLog);
    }

    public void renewReplyLog(String replyId, long repliedOn) {
        rejectIfDropped();
        LastLog newLastLog = lastLog.withChangedReply(replyId, repliedOn);
        setLastLog(newLastLog);
    }

    public void drop(String dropReason) {
        rejectIfDropped();
        setDropDetail(new DropDetail(dropReason));
    }

    public void checkArticlePapering() {
        if (!isPossibleWritingArticle()) {
            String time = DateUtil.toDateString(lastLog.getNextArticleTime());
            throw new PaperingDetected(format("게시글 도배 시도가 감지되었습니다. '%s' 이후에 다시 시도해주세요.", time));
        }
    }

    public void checkReplyPapering() {
        if (!isPossibleReplying()) {
            String time = DateUtil.toDateString(lastLog.getNextReplyTime());
            throw new PaperingDetected(format("답글 도배 시도가 감지되었습니다. '%s' 이후에 다시 시도해주세요.", time));
        }
    }

    public boolean dropped() {
        return dropDetail != null;
    }

    // -----------------------------------------------------------
    private void rejectIfDropped() {
        if (dropped())
            throw new ModelConflicted("탈퇴한 사용자이므로 더 이상 진행할 수 없습니다.");
    }

    private boolean isPossibleWritingArticle() {
        return lastLog.getNextArticleTime() <= currentTimeMillis();
    }

    private boolean isPossibleReplying() {
        return lastLog.getNextReplyTime() <= currentTimeMillis();
    }

    private void setId(String id) {
        assertArgNotNull(id, "id는 필수입니다.");
        this.id = id;
    }

    private void setEmail(String email) {
        assertArgNotNull(email, "이메일은 필수입니다.");
        String trimmed = email.trim();
        EmailRule.rejectIfIllegalPattern(trimmed);
        this.email = trimmed;
    }

    private void setName(String name) {
        assertArgNotNull(name, "이름은 필수입니다.");
        String trimmed = name.trim();
        NameRule.rejectIfBadLength(trimmed);
        this.name = trimmed;
    }

    private void setNickName(String nickName) {
        assertArgNotNull(nickName, "별명은 필수입니다.");
        NickNameRule.rejectIfBadNickName(nickName);
        this.nickName = nickName.trim();
    }

    private void setSex(Sex sex) {
        assertArgNotNull(sex, "성별은 필수입니다.");
        this.sex = sex;
    }

    private void setMotto(String motto) {
        if (motto == null) {
            this.motto = null;
            return;
        }
        String trimed = motto.trim();
        assertArgLength(trimed, 120, format("좌우명은 최대 %d자까지만 가능합니다: 현재 %d자", 120, trimed.length()));
        this.motto = trimed;
    }

    private void setDescription(String description) {
        if (description == null) {
            this.description = null;
            return;
        }
        String trimed = description.trim();
        assertArgLength(trimed, 200, format("자기 소개는 최대 %d자까지만 가능합니다: 현재 %d자", 200, trimed.length()));
        this.description = trimed;
    }

    private void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private void setLastLog(LastLog lastLog) {
        assertArgNotNull(lastLog, "게시글/답글 마지막 활동 내역은 필수입니다.");
        this.lastLog = lastLog;
    }

    private void setDropDetail(DropDetail dropDetail) {
        this.dropDetail = dropDetail;
    }
}
