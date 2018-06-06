package org.okky.member.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

@NoArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
public class Motto implements Aggregate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long id;

    @Column(length = 100, nullable = false)
    private String sentence;

    public Motto(String sentence) {
        assertArgNotNull(sentence, "좌우명은 필수입니다.");
        assertArgLength(sentence, 100, format("좌우명은 최대 %d자까지 가능합니다.", 100));
        this.sentence = sentence;
    }

    public static void main(String[] args) {
        String sentence1 = "일찍 일어나는 새가 피곤하당...";
        System.out.println(toPrettyJson(new Motto(sentence1)));
    }
}
