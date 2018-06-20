package org.okky.member.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.Aggregate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static java.lang.String.format;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.domain.AssertionConcern.assertArgLength;
import static org.okky.share.domain.AssertionConcern.assertArgNotNull;
import static org.okky.share.util.JsonUtil.toPrettyJson;


/**
 * 좌우명 추천 기능을 위해 사용하는 모델.
 *
 * @author coding8282
 */
@NoArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
@Getter
@Entity
public class Motto implements Aggregate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    Long id;

    @Column(length = 200, nullable = false)
    String sentence;

    public Motto(String sentence) {
        assertArgNotNull(sentence, "좌우명은 필수입니다.");
        String trimed = sentence.trim();
        assertArgLength(trimed, 1, 200, format("좌우명은 %d~%d자까지 가능합니다.", 1, 200));
        this.sentence = trimed;
    }

    // ------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static Motto sample() {
        Motto motto = new Motto("일찍 일어나는 새가 피곤하당...");
        return motto;
    }
}
