package org.okky.member.domain.model;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PUBLIC, makeFinal = true)
public final class PaperingRule {
    static int ARTICLE_INTERVAL = 1000 * 5;
    static int REPLY_INTERVAL = 1000 * 5;
}
