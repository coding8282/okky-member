package org.okky.member.domain.repository;

import org.junit.Test;
import org.okky.member.domain.model.Motto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class MottoRepositoryTest extends RepositoryTestMother {
    @Autowired
    private MottoRepository repository;

    @Test
    public void find() {
        Motto motto1 = new Motto("좌우명1");
        Motto motto2 = new Motto("좌우명2");
        Motto motto3 = new Motto("좌우명3");
        repository.save(motto1);
        repository.save(motto2);
        repository.save(motto3);
        List<String> mottos = repository.findRandomly(7);

        assertThat(mottos, hasItems(motto1.getSentence(), motto2.getSentence(), motto3.getSentence()));
    }
}