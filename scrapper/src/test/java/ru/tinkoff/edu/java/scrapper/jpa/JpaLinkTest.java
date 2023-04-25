package ru.tinkoff.edu.java.scrapper.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ScrapperApplication.class)
public class JpaLinkTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkRepository linkRepository;

    @Autowired
    private JpaChatRepository chatRepository;

    private final String TEST_LINK_1 = "https://edu.tinkoff.ru";
    private final String TEST_LINK_2 = "https://edu.tinkoff.ru";

    @Configuration
    static class MyIntegrationTests {
        @DynamicPropertySource
        static void registerProperties(DynamicPropertyRegistry registry) {
            registry.add("database-access-type", () -> "jpa");
        }
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrl() {
        Link link = new Link();
        link.setUrl(TEST_LINK_1);
        link.setLastCheckTime(OffsetDateTime.now());
        linkRepository.save(link);

        link = linkRepository.findByUrl(TEST_LINK_1).get();

        assertNotNull(link);
    }

    @Test
    @Transactional
    @Rollback
    public void findLinksByLastCheckTimeAfter() {
        Link link = new Link();
        link.setUrl(TEST_LINK_1);
        link.setLastCheckTime(OffsetDateTime.now().plusMinutes(1));
        linkRepository.save(link);

        link = new Link();
        link.setUrl(TEST_LINK_2);
        link.setLastCheckTime(OffsetDateTime.now().minusMinutes(1));
        linkRepository.save(link);

        List<Link> links = linkRepository.findLinksByLastCheckTimeAfter(OffsetDateTime.now());

        assertEquals(links.size(), 1);
        assertEquals(links.get(0).getUrl(), TEST_LINK_2);
    }

    @Test
    @Transactional
    @Rollback
    public void removeUnusedLinks() {
        Link link1 = new Link();
        link1.setUrl(TEST_LINK_1);
        link1.setLastCheckTime(OffsetDateTime.now().plusMinutes(1));
        linkRepository.save(link1);

        Link link2 = new Link();
        link2.setUrl(TEST_LINK_2);
        link2.setLastCheckTime(OffsetDateTime.now().minusMinutes(1));
        linkRepository.save(link2);

        Chat chat = chatRepository.save(new Chat(1L));
        chat.getLinks().add(link1);

        linkRepository.removeUnusedLinks(link2.getId());

        List<Link> links = linkRepository.findAll();

        assertEquals(links.size(), 1);
        assertEquals(links.get(0).getUrl(), TEST_LINK_1);
    }

}
