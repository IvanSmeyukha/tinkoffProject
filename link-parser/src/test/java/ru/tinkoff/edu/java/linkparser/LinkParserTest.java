package ru.tinkoff.edu.java.linkparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkParserTest {
    @Test
    public void parseLink_GitHubValidLink_GitHubLinkResponse() {
        String link = "https://github.com/IvanSmeyukha/tinkoffProject";

        assertEquals(new GitHubLinkResponse("IvanSmeyukha", "tinkoffProject"),
                LinkParser.parseLink(link));

    }

    @Test
    public void parseLink_GitHubInvalidLink_Null() {
        String link = "https://github.ru/IvanSmeyukha/tinkoffProject";

        assertNull(LinkParser.parseLink(link));
    }

    @Test
    public void parseLink_StackOverflowValidLink_StackOverflowLinkResponse() {
        String link = "https://stackoverflow.com/questions/214741/what-is-a-stackoverflowerror";

        assertEquals(new StackOverflowLinkResponse(214741L),
                LinkParser.parseLink(link));
    }

    @Test
    public void parseLink_StackOverflowInvalidLink__Null() {
        String link = "https://stackoverflow.com/answers/214741/what-is-a-stackoverflowerror";

        assertNull(LinkParser.parseLink(link));
    }
}
