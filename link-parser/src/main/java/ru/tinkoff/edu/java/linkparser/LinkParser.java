package ru.tinkoff.edu.java.linkparser;

public class LinkParser {
    private static final AbstractLinkHandler gitHubLinkHandler;
    private static final AbstractLinkHandler stackOverflowLinkHandler;

    static {
        gitHubLinkHandler = new GitHubLinkHandler(null);
        stackOverflowLinkHandler = new StackOverflowLinkHandler(gitHubLinkHandler);
    }

    public static LinkParserResponse parseLink(String link){
        return stackOverflowLinkHandler.parseLink(link);
    }
}
