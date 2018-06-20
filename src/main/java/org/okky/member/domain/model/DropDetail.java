package org.okky.member.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.ValueObject;
import org.okky.share.util.JsonUtil;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.okky.share.domain.AssertionConcern.assertArgLength;
import static org.okky.share.domain.AssertionConcern.assertArgNotEmpty;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
@Getter
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class DropDetail implements ValueObject {
    @Column(length = 200)
    String dropReason;

    @CreatedDate
    @Column(columnDefinition = "BIGINT UNSIGNED")
    long droppedOn;

    public DropDetail(String dropReason) {
        setDropReason(dropReason);
    }

    public static DropDetail sample() {
        String dropReason = "개인 정보 유출을 우려하여";
        DropDetail detail = new DropDetail(dropReason);
        return detail;
    }

    public static void main(String[] args) {
        System.out.println(JsonUtil.toPrettyJson(sample()));
    }

    // ---------------------------------
    private void setDropReason(String dropReason) {
        assertArgNotEmpty(dropReason, "탈퇴사유는 필수입니다.");
        String trimed = dropReason.trim();
        assertArgLength(trimed, 1, 200, format("탈퇴사유는 %d~%d자까지 가능합니다.", 1, 200));
        this.dropReason = trimed;
    }
}
