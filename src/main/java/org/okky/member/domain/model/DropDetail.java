package org.okky.member.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.okky.share.domain.ValueObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;

import static java.lang.String.format;
import static lombok.AccessLevel.PROTECTED;
import static org.okky.share.domain.AssertionConcern.assertArgLength;
import static org.okky.share.domain.AssertionConcern.assertArgNotNull;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class DropDetail implements ValueObject {
    @Column(length = 200)
    private String dropReason;

    @CreatedDate
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private long droppedOn;

    public DropDetail(String dropReason) {
        setDropReason(dropReason);
    }

    public static DropDetail sample() {
        return new DropDetail("개인 정보 유출을 우려하여");
    }

    public static void main(String[] args) {
        System.out.println(sample());
    }

    // ---------------------------------
    private void setDropReason(String dropReason) {
        assertArgNotNull(dropReason, "탈퇴사유는 필수입니다.");
        String trimed = dropReason.trim();
        assertArgLength(trimed, 1, 100, format("탈퇴사유는 %d~%d자까지 가능합니다.", 1, 100));
        this.dropReason = trimed;
    }
}
