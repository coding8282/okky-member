package org.okky.member.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = PRIVATE)
@Builder
@Getter
@Embeddable
public class LastLog implements ValueObject {
    @Column(length = 50)
    String lastWroteArticleId;

    @Column(length = 50)
    String lastWroteReplyId;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    Long lastArticleWroteOn;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    Long lastReplyWroteOn;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    long nextArticleTime;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    long nextReplyTime;

    public LastLog() {
        this.lastWroteArticleId = null;
        this.lastWroteReplyId = null;
        this.lastArticleWroteOn = null;
        this.lastReplyWroteOn = null;
        this.nextArticleTime = 0L;
        this.nextReplyTime = 0L;
    }

    public static LastLog sample() {
        return new LastLog();
    }

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public LastLog withChangedArticle(String articleId, long wroteOn) {
        return LastLog.builder()
                .lastWroteArticleId(articleId)
                .lastWroteReplyId(lastWroteReplyId)
                .lastArticleWroteOn(wroteOn)
                .lastReplyWroteOn(lastReplyWroteOn)
                .nextArticleTime(wroteOn + PaperingRule.ARTICLE_INTERVAL)
                .nextReplyTime(nextReplyTime)
                .build();
    }

    public LastLog withChangedReply(String replyId, long repliedOn) {
        return LastLog.builder()
                .lastWroteArticleId(lastWroteArticleId)
                .lastWroteReplyId(replyId)
                .lastArticleWroteOn(lastArticleWroteOn)
                .lastReplyWroteOn(repliedOn)
                .nextArticleTime(nextArticleTime)
                .nextReplyTime(repliedOn + PaperingRule.REPLY_INTERVAL)
                .build();
    }
}
