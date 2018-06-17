package org.okky.member.domain.repository;

import org.junit.runner.RunWith;
import org.okky.member.TestMother;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class RepositoryTestMother extends TestMother {
}
