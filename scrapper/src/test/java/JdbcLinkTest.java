import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment{
//    @Autowired
//    private final LinkRepository linkRepository;
//
//    @Autowired
//    private final JdbcTemplate jdbcTemplate;
//
//
//    @Test
//    @Transactional
//    @Rollback
//    public void add_AddingLink() {
//        try {
//            Link link = new Link(1111L, new URI("https://edu.tinkoff.ru"));
//            linkRepository.add(new URI("https://edu.tinkoff.ru"));
//
//            String query = "SELECT * FROM links WHERE id = 1111L";
//            Link addedLink = jdbcTemplate.queryForObject(query, Link.class);
//
//            assertNull(addedLink);
//            assertEquals(addedLink.getId(), link.getId());
//            assertEquals(addedLink.getUri(), link.getUri());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void removeTest() {
//    }
}
