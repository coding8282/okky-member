package org.okky.member.domain.repository;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.okky.member.domain.model.Motto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@FieldDefaults(level = PRIVATE)
public class MottoRepositoryTest extends RepositoryTestMother {
    @Autowired
    MottoRepository repository;

    @Test
    public void find() {
        Motto m1 = new Motto("좌우명1");
        Motto m2 = new Motto("좌우명2");
        Motto m3 = new Motto("좌우명3");
        repository.save(m1);
        repository.save(m2);
        repository.save(m3);
        List<String> mottos = repository.findRandomly(7);

        assertThat(mottos, hasItems(m1.getSentence(), m2.getSentence(), m3.getSentence()));
    }
}