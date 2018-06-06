package org.okky.member.domain.model;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PaperingRule {
    public static final int ARTICLE_INTERVAL = 1000 * 5;
    public static final int REPLY_INTERVAL = 1000 * 5;
}
