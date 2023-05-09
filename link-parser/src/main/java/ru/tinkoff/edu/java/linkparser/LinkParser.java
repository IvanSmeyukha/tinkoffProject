package ru.tinkoff.edu.java.linkparser;

public class LinkParser {
    private static final LinkHandler GITHUBLINKHANDLER = new GitHubLinkHandler(null);
    private static final LinkHandler STACKOVERFLOWLINKHANDLER = new StackOverflowLinkHandler(GITHUBLINKHANDLER);

    private LinkParser() {
    }

    public static LinkParserResponse parseLink(String link) {
        return STACKOVERFLOWLINKHANDLER.parseLink(link);
    }
}
