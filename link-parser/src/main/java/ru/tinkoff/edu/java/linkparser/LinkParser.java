package ru.tinkoff.edu.java.linkparser;

public class LinkParser {
    private static final LinkHandler gitHubLinkHandler = new GitHubLinkHandler(null);
    private static final LinkHandler stackOverflowLinkHandler = new StackOverflowLinkHandler(gitHubLinkHandler);

    public static LinkParserResponse parseLink(String link){
        return stackOverflowLinkHandler.parseLink(link);
    }
}
